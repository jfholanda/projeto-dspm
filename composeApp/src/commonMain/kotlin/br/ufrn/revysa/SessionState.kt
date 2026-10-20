package br.ufrn.revysa

import androidx.compose.runtime.saveable.Saver
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/** Snapshot de interface para recriações da Activity; não grava em disco ou backend. */
@Serializable
private data class SessionState(
    val saved: List<String>,
    val cards: List<StudyCard>,
    val notes: List<StudyNote>,
    val highlights: List<Pair<String, Int>>,
    val read: List<Pair<String, Int>>,
    val quiz: Map<String, Boolean>,
    val reviewed: Int,
)

internal val studySessionSaver =
    Saver<StudySession, String>(
        save = { session ->
            Json.encodeToString(
                SessionState(
                    session.savedMaterials.toList(),
                    session.cards.toList(),
                    session.notes.toList(),
                    session.highlights.toList(),
                    session.readChapters.toList(),
                    session.quizResults.toMap(),
                    session.reviewed,
                ),
            )
        },
        restore = { encoded ->
            val state = Json.decodeFromString<SessionState>(encoded)
            StudySession().apply {
                savedMaterials.clear()
                savedMaterials.addAll(state.saved)
                cards.clear()
                cards.addAll(state.cards)
                notes.addAll(state.notes)
                highlights.addAll(state.highlights)
                readChapters.addAll(state.read)
                quizResults.putAll(state.quiz)
                reviewed = state.reviewed
            }
        },
    )
