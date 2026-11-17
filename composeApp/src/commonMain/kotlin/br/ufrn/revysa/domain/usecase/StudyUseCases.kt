package br.ufrn.revysa.domain.usecase

import br.ufrn.revysa.domain.model.ChapterReference
import br.ufrn.revysa.domain.model.ReviewRating
import br.ufrn.revysa.domain.model.StudyCard
import br.ufrn.revysa.domain.model.StudyMaterial
import br.ufrn.revysa.domain.model.StudyNote
import br.ufrn.revysa.domain.model.StudySnapshot
import br.ufrn.revysa.domain.repository.StudyRepository
import kotlinx.coroutines.flow.Flow

internal fun interface EpochClock {
    fun nowMillis(): Long
}

@Suppress("TooManyFunctions")
internal class StudyUseCases(
    private val repository: StudyRepository,
    private val clock: EpochClock,
) {
    fun observeStudy(): Flow<StudySnapshot> = repository.observeStudy()

    fun allMaterials(): List<StudyMaterial> = repository.allMaterials()

    fun libraryMaterials(
        snapshot: StudySnapshot,
        query: String,
        explore: Boolean,
    ): List<StudyMaterial> =
        repository.allMaterials().filter { material ->
            (explore || material.id in snapshot.savedMaterialIds) &&
                (material.title.contains(query, ignoreCase = true) || material.subject.contains(query, ignoreCase = true))
        }

    fun progress(
        snapshot: StudySnapshot,
        material: StudyMaterial,
    ): Float {
        val readCount = snapshot.readChapters.count { it.materialId == material.id }
        return if (material.chapters.isEmpty()) 0f else readCount.toFloat() / material.chapters.size
    }

    fun material(id: String): StudyMaterial? = repository.findMaterial(id)

    fun addMaterial(id: String) = repository.addMaterial(id)

    fun markChapterRead(
        materialId: String,
        chapter: Int,
    ) = repository.markChapterRead(ChapterReference(materialId, chapter))

    fun toggleHighlight(
        materialId: String,
        chapter: Int,
    ) = repository.toggleHighlight(ChapterReference(materialId, chapter))

    fun saveNote(
        materialId: String,
        chapter: Int,
        text: String,
    ) {
        if (text.isNotBlank() && material(materialId)?.chapters?.getOrNull(chapter) != null) {
            repository.saveNote(StudyNote(materialId, chapter, text.trim()))
        }
    }

    fun createCard(
        materialId: String,
        chapter: Int,
        question: String,
        answer: String,
    ): Boolean {
        if (question.isBlank() || answer.isBlank() || material(materialId)?.chapters?.getOrNull(chapter) == null) return false
        repository.createCard(StudyCard(materialId, chapter, question.trim(), answer.trim()))
        return true
    }

    fun submitQuiz(
        materialId: String,
        selectedAnswer: Int,
    ): Boolean? {
        val material = material(materialId)
        return when {
            material == null || selectedAnswer !in material.answers.indices -> null
            else -> (selectedAnswer == material.correctAnswerIndex).also { repository.submitQuiz(materialId, it) }
        }
    }

    fun reviewDueCards(snapshot: StudySnapshot = repository.currentStudy()): List<StudyCard> {
        val now = clock.nowMillis()
        return snapshot.cards.filter { it.nextReviewAtEpochMillis <= now }
    }

    fun recordReview(
        cardId: String,
        rating: ReviewRating,
    ) {
        val previousInterval = repository.currentStudy().cards.firstOrNull { it.id == cardId }?.intervalDays ?: return
        val reviewedAt = clock.nowMillis()
        val interval = nextReviewIntervalDays(previousInterval, rating)
        val dueAt = reviewedAt + (interval * MILLIS_PER_DAY).toLong()
        repository.scheduleCardReview(cardId, interval, dueAt)
    }
}

internal fun nextReviewIntervalDays(
    previousIntervalDays: Double,
    rating: ReviewRating,
): Double =
    when (rating) {
        ReviewRating.AGAIN -> INITIAL_INTERVAL_DAYS
        ReviewRating.HARD -> (previousIntervalDays.takeIf { it > 0.0 } ?: INITIAL_INTERVAL_DAYS) * HARD_MULTIPLIER
        ReviewRating.GOOD -> (previousIntervalDays.takeIf { it > 0.0 } ?: INITIAL_INTERVAL_DAYS) * GOOD_MULTIPLIER
        ReviewRating.EASY -> (previousIntervalDays.takeIf { it > 0.0 } ?: INITIAL_INTERVAL_DAYS) * EASY_MULTIPLIER
    }

private const val INITIAL_INTERVAL_DAYS = 1.0
private const val HARD_MULTIPLIER = 1.5
private const val GOOD_MULTIPLIER = 2.0
private const val EASY_MULTIPLIER = 3.0
private const val MILLIS_PER_DAY = 86_400_000.0
