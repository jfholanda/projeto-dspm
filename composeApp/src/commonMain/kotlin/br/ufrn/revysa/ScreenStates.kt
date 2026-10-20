package br.ufrn.revysa

internal data class MaterialItem(val material: StudyMaterial, val progress: Float)

internal data class HomeUiState(val materials: List<MaterialItem>, val reviewed: Int)

internal data class LibraryUiState(val query: String, val explore: Boolean, val materials: List<MaterialItem>)

internal data class MaterialUiState(val material: StudyMaterial?, val progress: Float = 0f, val saved: Boolean = false)

internal data class ReaderUiState(val materialTitle: String, val chapter: Chapter?, val highlighted: Boolean, val read: Boolean)

internal data class NotesUiState(
    val chapterTitle: String?,
    val highlight: String?,
    val note: String,
    val error: String?,
    val notes: List<String>,
)

internal data class CardFormUiState(
    val chapterTitle: String?,
    val question: String,
    val answer: String,
    val errors: Pair<String?, String?>,
)

internal data class ReviewUiState(
    val card: StudyCard?,
    val materialTitle: String,
    val index: Int,
    val total: Int,
    val revealed: Boolean,
)

internal data class QuizUiState(val material: StudyMaterial?, val selected: Int, val submitted: Boolean)

internal data class ProgressUiState(
    val readCount: Int,
    val reviewed: Int,
    val cardCount: Int,
    val quizCorrect: Int,
    val quizTotal: Int,
    val materials: List<MaterialItem>,
)

internal data class AuthUiState(
    val register: Boolean,
    val name: String,
    val email: String,
    val password: String,
    val errors: AuthErrors,
)
