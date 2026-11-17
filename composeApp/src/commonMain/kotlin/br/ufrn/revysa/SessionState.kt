package br.ufrn.revysa

import androidx.compose.runtime.saveable.Saver
import br.ufrn.revysa.domain.model.ChapterReference
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/** Presentation-only serialization used to restore the in-memory demo session after Activity recreation. */
internal val studySnapshotSaver =
    Saver<StudySnapshot, String>(
        save = { snapshot -> Json.encodeToString(StudySnapshotDto.from(snapshot)) },
        restore = { encoded -> Json.decodeFromString<StudySnapshotDto>(encoded).toModel() },
    )

@Serializable
private data class StudySnapshotDto(
    val savedMaterialIds: List<String>,
    val cards: List<CardDto>,
    val notes: List<NoteDto>,
    val highlights: List<ReferenceDto>,
    val readChapters: List<ReferenceDto>,
    val quizResults: Map<String, Boolean>,
    val reviewed: Int,
) {
    fun toModel() =
        StudySnapshot(
            savedMaterialIds,
            cards.map { it.toModel() },
            notes.map { it.toModel() },
            highlights.map { it.toModel() },
            readChapters.map { it.toModel() },
            quizResults,
            reviewed,
        )

    companion object {
        fun from(snapshot: StudySnapshot) =
            StudySnapshotDto(
                snapshot.savedMaterialIds,
                snapshot.cards.map {
                    CardDto(it.materialId, it.chapter, it.question, it.answer, it.id, it.intervalDays, it.nextReviewAtEpochMillis)
                },
                snapshot.notes.map { NoteDto(it.materialId, it.chapter, it.text) },
                snapshot.highlights.map { ReferenceDto(it.materialId, it.chapter) },
                snapshot.readChapters.map { ReferenceDto(it.materialId, it.chapter) },
                snapshot.quizResults,
                snapshot.reviewed,
            )
    }
}

@Serializable
private data class CardDto(
    val materialId: String,
    val chapter: Int,
    val question: String,
    val answer: String,
    val id: String,
    val intervalDays: Double,
    val nextReviewAtEpochMillis: Long,
) {
    fun toModel() = StudyCard(materialId, chapter, question, answer, id, intervalDays, nextReviewAtEpochMillis)
}

@Serializable
private data class NoteDto(val materialId: String, val chapter: Int, val text: String) {
    fun toModel() = StudyNote(materialId, chapter, text)
}

@Serializable
private data class ReferenceDto(val materialId: String, val chapter: Int) {
    fun toModel() = ChapterReference(materialId, chapter)
}
