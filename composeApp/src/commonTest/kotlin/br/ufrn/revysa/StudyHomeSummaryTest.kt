package br.ufrn.revysa

import br.ufrn.revysa.data.demo.InMemoryStudyRepository
import br.ufrn.revysa.domain.model.ChapterReference
import br.ufrn.revysa.domain.model.HomePrimaryAction
import br.ufrn.revysa.domain.model.StudySnapshot
import br.ufrn.revysa.domain.usecase.EpochClock
import br.ufrn.revysa.domain.usecase.StudyUseCases
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class StudyHomeSummaryTest {
    @Test
    fun prioritizesDueReviewsAndKeepsReadingSuggestionVisible() {
        val snapshot = StudySnapshot.initial()

        val summary = useCases(snapshot, now = 1_000L).homeSummary(snapshot)

        assertEquals(1, summary.dueReviewCount)
        assertEquals(HomePrimaryAction.REVIEW, summary.primaryAction)
        assertEquals("machine-learning", summary.nextReading?.materialId)
        assertEquals(0, summary.nextReading?.chapterIndex)
        assertEquals("Introdução", summary.nextReading?.chapterTitle)
    }

    @Test
    fun excludesFutureReviewsAndChoosesFirstUnreadChapter() {
        val snapshot =
            StudySnapshot.initial().copy(
                cards = StudySnapshot.initial().cards.map { it.copy(nextReviewAtEpochMillis = 2_000L) },
                readChapters = listOf(ChapterReference("machine-learning", 0)),
            )

        val summary = useCases(snapshot, now = 1_000L).homeSummary(snapshot)

        assertEquals(0, summary.dueReviewCount)
        assertEquals(HomePrimaryAction.CONTINUE_READING, summary.primaryAction)
        assertEquals("machine-learning", summary.nextReading?.materialId)
        assertEquals(1, summary.nextReading?.chapterIndex)
    }

    @Test
    fun followsPersonalLibraryOrderAndSkipsCompletedMaterials() {
        val snapshot =
            StudySnapshot.initial().copy(
                cards = StudySnapshot.initial().cards.map { it.copy(nextReviewAtEpochMillis = 2_000L) },
                readChapters = listOf(ChapterReference("machine-learning", 0), ChapterReference("machine-learning", 1)),
            )

        val summary = useCases(snapshot, now = 1_000L).homeSummary(snapshot)

        assertEquals("design-software", summary.nextReading?.materialId)
        assertEquals(0, summary.nextReading?.chapterIndex)
    }

    @Test
    fun offersCatalogWhenLibraryIsEmptyOrAllSavedChaptersAreRead() {
        val empty = StudySnapshot.initial().copy(savedMaterialIds = emptyList(), cards = emptyList())
        val emptySummary = useCases(empty, now = 1_000L).homeSummary(empty)
        assertEquals(HomePrimaryAction.EXPLORE_CATALOG, emptySummary.primaryAction)
        assertNull(emptySummary.nextReading)

        val complete =
            StudySnapshot.initial().copy(
                cards = emptyList(),
                readChapters =
                    listOf(
                        ChapterReference("machine-learning", 0),
                        ChapterReference("machine-learning", 1),
                        ChapterReference("design-software", 0),
                        ChapterReference("design-software", 1),
                    ),
            )
        val completeSummary = useCases(complete, now = 1_000L).homeSummary(complete)
        assertEquals(HomePrimaryAction.EXPLORE_CATALOG, completeSummary.primaryAction)
        assertNull(completeSummary.nextReading)
    }

    private fun useCases(
        snapshot: StudySnapshot,
        now: Long,
    ) = StudyUseCases(InMemoryStudyRepository(snapshot), EpochClock { now })
}
