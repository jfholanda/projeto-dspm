package br.ufrn.revysa.domain.model

internal data class Chapter(val title: String, val text: String, val keyIdea: String)

internal data class StudyMaterial(
    val id: String,
    val title: String,
    val subject: String,
    val description: String,
    val chapters: List<Chapter>,
    val question: String,
    val answers: List<String>,
    val correctAnswerIndex: Int = 0,
)

internal data class ChapterReference(val materialId: String, val chapter: Int)

internal data class StudyCard(
    val materialId: String,
    val chapter: Int,
    val question: String,
    val answer: String,
    val id: String = "$materialId-$chapter-${question.hashCode()}",
    val intervalDays: Double = 0.0,
    val nextReviewAtEpochMillis: Long = 0L,
)

internal data class StudyNote(val materialId: String, val chapter: Int, val text: String)

internal data class StudySnapshot(
    val savedMaterialIds: List<String>,
    val cards: List<StudyCard>,
    val notes: List<StudyNote> = emptyList(),
    val highlights: List<ChapterReference> = emptyList(),
    val readChapters: List<ChapterReference> = emptyList(),
    val quizResults: Map<String, Boolean> = emptyMap(),
    val reviewed: Int = 0,
) {
    companion object {
        fun initial() =
            StudySnapshot(
                savedMaterialIds = listOf("machine-learning", "design-software"),
                cards =
                    listOf(
                        StudyCard(
                            "machine-learning",
                            1,
                            "Qual é o papel da taxa de aprendizado?",
                            "A taxa de aprendizado controla o tamanho da atualização dos parâmetros.",
                        ),
                    ),
            )
    }
}

internal enum class HomePrimaryAction { REVIEW, CONTINUE_READING, EXPLORE_CATALOG }

internal data class StudyReadingSuggestion(
    val materialId: String,
    val chapterIndex: Int,
    val materialTitle: String,
    val chapterTitle: String,
    val progress: Float,
)

internal data class StudyHomeSummary(
    val dueReviewCount: Int,
    val nextReading: StudyReadingSuggestion?,
    val primaryAction: HomePrimaryAction,
)

internal enum class ReviewRating { AGAIN, HARD, GOOD, EASY }
