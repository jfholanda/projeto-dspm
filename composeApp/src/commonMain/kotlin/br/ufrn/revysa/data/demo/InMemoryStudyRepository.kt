package br.ufrn.revysa.data.demo

import br.ufrn.revysa.domain.model.ChapterReference
import br.ufrn.revysa.domain.model.StudyCard
import br.ufrn.revysa.domain.model.StudyMaterial
import br.ufrn.revysa.domain.model.StudyNote
import br.ufrn.revysa.domain.model.StudySnapshot
import br.ufrn.revysa.domain.repository.StudyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@Suppress("TooManyFunctions")
internal class InMemoryStudyRepository(
    initialSnapshot: StudySnapshot = StudySnapshot.initial(),
    private val onSnapshotChanged: (StudySnapshot) -> Unit = {},
    private val catalog: List<StudyMaterial> = demoCatalog,
) : StudyRepository {
    private val mutableState = MutableStateFlow(initialSnapshot)
    private val state = mutableState.asStateFlow()

    override fun observeStudy(): Flow<StudySnapshot> = state

    override fun currentStudy(): StudySnapshot = mutableState.value

    override fun findMaterial(id: String): StudyMaterial? = catalog.firstOrNull { it.id == id }

    override fun allMaterials(): List<StudyMaterial> = catalog

    override fun addMaterial(id: String) {
        if (findMaterial(id) == null || id in currentStudy().savedMaterialIds) return
        update { copy(savedMaterialIds = savedMaterialIds + id) }
    }

    override fun markChapterRead(reference: ChapterReference) {
        if (findMaterial(reference.materialId)?.chapters?.getOrNull(reference.chapter) == null) return
        if (reference in currentStudy().readChapters) return
        update { copy(readChapters = readChapters + reference) }
    }

    override fun toggleHighlight(reference: ChapterReference) {
        if (findMaterial(reference.materialId)?.chapters?.getOrNull(reference.chapter) == null) return
        update {
            copy(highlights = if (reference in highlights) highlights - reference else highlights + reference)
        }
    }

    override fun saveNote(note: StudyNote) {
        if (findMaterial(note.materialId)?.chapters?.getOrNull(note.chapter) == null) return
        update { copy(notes = notes + note) }
    }

    override fun createCard(card: StudyCard) {
        if (findMaterial(card.materialId)?.chapters?.getOrNull(card.chapter) == null) return
        if (currentStudy().cards.any { it.id == card.id }) return
        update { copy(cards = cards + card) }
    }

    override fun submitQuiz(
        materialId: String,
        correct: Boolean,
    ) {
        if (findMaterial(materialId) == null) return
        update { copy(quizResults = quizResults + (materialId to correct)) }
    }

    override fun scheduleCardReview(
        cardId: String,
        intervalDays: Double,
        nextReviewAtEpochMillis: Long,
    ) {
        if (currentStudy().cards.none { it.id == cardId }) return
        update {
            copy(
                cards =
                    cards.map {
                        if (it.id == cardId) it.copy(intervalDays = intervalDays, nextReviewAtEpochMillis = nextReviewAtEpochMillis) else it
                    },
                reviewed = reviewed + 1,
            )
        }
    }

    private fun update(transform: StudySnapshot.() -> StudySnapshot) {
        val updated = currentStudy().transform()
        mutableState.value = updated
        onSnapshotChanged(updated)
    }
}
