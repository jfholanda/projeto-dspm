package br.ufrn.revysa

import app.cash.turbine.test
import br.ufrn.revysa.data.demo.InMemoryStudyRepository
import br.ufrn.revysa.domain.model.ChapterReference
import br.ufrn.revysa.domain.model.ReviewRating
import br.ufrn.revysa.domain.model.StudySnapshot
import br.ufrn.revysa.domain.repository.StudyRepository
import br.ufrn.revysa.domain.usecase.EpochClock
import br.ufrn.revysa.domain.usecase.StudyUseCases
import br.ufrn.revysa.domain.usecase.nextReviewIntervalDays
import br.ufrn.revysa.presentation.StudyViewModel
import br.ufrn.revysa.presentation.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
internal class StudyViewModelTest {
    private val dispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setMainDispatcher() {
        Dispatchers.setMain(dispatcher)
    }

    @AfterTest
    fun resetMainDispatcher() {
        Dispatchers.resetMain()
    }

    @Test
    fun emitsLoadingThenStudyData() =
        runTest(dispatcher) {
            val viewModel = viewModel()
            viewModel.state.test {
                assertIs<UiState.Loading>(awaitItem())
                advanceUntilIdle()
                assertIs<UiState.Success<StudySnapshot>>(awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun emitsEmptyWhenThereAreNoSavedMaterialsOrCards() =
        runTest(dispatcher) {
            val viewModel = viewModel(StudySnapshot(savedMaterialIds = emptyList(), cards = emptyList()))
            viewModel.state.test {
                assertIs<UiState.Loading>(awaitItem())
                advanceUntilIdle()
                assertIs<UiState.Empty<StudySnapshot>>(awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun convertsRepositoryFailureToErrorState() =
        runTest(dispatcher) {
            val repository = RecoverableStudyRepository()
            val viewModel = viewModel(repository = repository)
            viewModel.state.test {
                assertIs<UiState.Loading>(awaitItem())
                advanceUntilIdle()
                assertEquals("Falha de demonstração", assertIs<UiState.Error>(awaitItem()).message)
                repository.recover()
                viewModel.retry()
                advanceUntilIdle()
                assertIs<UiState.Loading>(awaitItem())
                assertIs<UiState.Success<StudySnapshot>>(awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun searchesTheCatalogAndRespectsExploreMode() =
        runTest(dispatcher) {
            val viewModel = viewModel()
            assertEquals(listOf("machine-learning"), viewModel.libraryMaterials(StudySnapshot.initial(), "máquina", false).map { it.id })
            assertEquals(3, viewModel.libraryMaterials(StudySnapshot.initial(), "", true).size)
        }

    @Test
    fun markingAChapterUpdatesReadingProgress() =
        runTest(dispatcher) {
            val viewModel = viewModel()
            advanceUntilIdle()
            viewModel.markChapterRead("machine-learning", 0)
            advanceUntilIdle()
            val snapshot = assertIs<UiState.Success<StudySnapshot>>(viewModel.state.value).value
            assertEquals(0.5f, viewModel.progress(snapshot, viewModel.materials().first()))
            assertEquals(listOf(ChapterReference("machine-learning", 0)), snapshot.readChapters)
        }

    @Test
    fun savesNotesAndCreatesValidatedCards() =
        runTest(dispatcher) {
            val viewModel = viewModel()
            advanceUntilIdle()
            viewModel.saveNote("machine-learning", 0, "  rever validação  ")
            assertTrue(viewModel.createCard("machine-learning", 0, "Pergunta?", "Resposta."))
            assertEquals(false, viewModel.createCard("machine-learning", 0, " ", "Resposta."))
            advanceUntilIdle()
            val snapshot = assertIs<UiState.Success<StudySnapshot>>(viewModel.state.value).value
            assertEquals("rever validação", snapshot.notes.single().text)
            assertEquals(2, snapshot.cards.size)
            assertEquals(0.0, snapshot.cards.last().intervalDays)
        }

    @Test
    fun recordingReviewSchedulesCardAndIncrementsReviewedCount() =
        runTest(dispatcher) {
            val now = 1_000_000L
            val repository = InMemoryStudyRepository(StudySnapshot.initial())
            val viewModel = viewModel(repository = repository, now = now)
            val card = repository.currentStudy().cards.single()
            viewModel.recordReview(card.id, ReviewRating.HARD)
            val updated = repository.currentStudy().cards.single()
            assertEquals(1.5, updated.intervalDays)
            assertEquals(now + (1.5 * DAY_MILLIS).toLong(), updated.nextReviewAtEpochMillis)
            assertEquals(1, repository.currentStudy().reviewed)
            assertTrue(viewModel.dueCards().isEmpty())
        }

    @Test
    fun quizSubmissionStoresTheCorrectResultAndRejectsInvalidAnswers() =
        runTest(dispatcher) {
            val viewModel = viewModel()
            advanceUntilIdle()
            assertNull(viewModel.submitQuiz("machine-learning", -1))
            assertEquals(true, viewModel.submitQuiz("machine-learning", 0))
            advanceUntilIdle()
            val snapshot = assertIs<UiState.Success<StudySnapshot>>(viewModel.state.value).value
            assertEquals(true, snapshot.quizResults["machine-learning"])
        }

    @Test
    fun spacedRepetitionSupportsEveryRatingAndFractionalIntervals() {
        assertEquals(1.0, nextReviewIntervalDays(0.0, ReviewRating.AGAIN))
        assertEquals(1.5, nextReviewIntervalDays(1.0, ReviewRating.HARD))
        assertEquals(3.0, nextReviewIntervalDays(1.5, ReviewRating.GOOD))
        assertEquals(4.5, nextReviewIntervalDays(1.5, ReviewRating.EASY))
    }

    private fun viewModel(
        snapshot: StudySnapshot = StudySnapshot.initial(),
        repository: StudyRepository = InMemoryStudyRepository(snapshot),
        now: Long = 0L,
    ) = StudyViewModel(StudyUseCases(repository, EpochClock { now }))

    private class RecoverableStudyRepository : StudyRepository {
        private val delegate = InMemoryStudyRepository()
        private val fail = MutableStateFlow(true)

        override fun observeStudy(): Flow<StudySnapshot> =
            fail.flatMapLatest {
                if (it) flow { error("Falha de demonstração") } else delegate.observeStudy()
            }

        fun recover() {
            fail.value = false
        }

        override fun currentStudy() = delegate.currentStudy()

        override fun findMaterial(id: String) = delegate.findMaterial(id)

        override fun allMaterials() = delegate.allMaterials()

        override fun addMaterial(id: String) = delegate.addMaterial(id)

        override fun markChapterRead(reference: ChapterReference) = delegate.markChapterRead(reference)

        override fun toggleHighlight(reference: ChapterReference) = delegate.toggleHighlight(reference)

        override fun saveNote(note: br.ufrn.revysa.domain.model.StudyNote) = delegate.saveNote(note)

        override fun createCard(card: br.ufrn.revysa.domain.model.StudyCard) = delegate.createCard(card)

        override fun submitQuiz(
            materialId: String,
            correct: Boolean,
        ) = delegate.submitQuiz(materialId, correct)

        override fun scheduleCardReview(
            cardId: String,
            intervalDays: Double,
            nextReviewAtEpochMillis: Long,
        ) = delegate.scheduleCardReview(cardId, intervalDays, nextReviewAtEpochMillis)
    }

    private companion object {
        const val DAY_MILLIS = 86_400_000.0
    }
}
