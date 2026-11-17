package br.ufrn.revysa.domain.repository

import br.ufrn.revysa.domain.model.ChapterReference
import br.ufrn.revysa.domain.model.StudyCard
import br.ufrn.revysa.domain.model.StudyMaterial
import br.ufrn.revysa.domain.model.StudyNote
import br.ufrn.revysa.domain.model.StudySnapshot
import kotlinx.coroutines.flow.Flow

@Suppress("TooManyFunctions")
internal interface StudyRepository {
    fun observeStudy(): Flow<StudySnapshot>

    fun currentStudy(): StudySnapshot

    fun findMaterial(id: String): StudyMaterial?

    fun allMaterials(): List<StudyMaterial>

    fun addMaterial(id: String)

    fun markChapterRead(reference: ChapterReference)

    fun toggleHighlight(reference: ChapterReference)

    fun saveNote(note: StudyNote)

    fun createCard(card: StudyCard)

    fun submitQuiz(
        materialId: String,
        correct: Boolean,
    )

    fun scheduleCardReview(
        cardId: String,
        intervalDays: Double,
        nextReviewAtEpochMillis: Long,
    )
}
