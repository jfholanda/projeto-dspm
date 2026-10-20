package br.ufrn.revysa

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

@Composable
internal fun ReaderRoute(
    route: Reader,
    session: StudySession,
    navigate: (Any) -> Unit,
) {
    val material = catalog.find { it.id == route.id }
    ReaderScreen(
        ReaderUiState(
            material?.title.orEmpty(),
            material?.chapters?.getOrNull(route.chapter),
            route.id to route.chapter in session.highlights,
            route.id to route.chapter in session.readChapters,
        ),
        onHighlight = { session.toggleHighlight(route.id, route.chapter) },
        onNotes = { navigate(Notes(route.id, route.chapter)) },
        onCreateCard = { navigate(CreateCard(route.id, route.chapter)) },
        onRead = { session.markRead(route.id, route.chapter) },
    )
}

@Composable
internal fun NotesRoute(
    route: Notes,
    session: StudySession,
) {
    var note by rememberSaveable { mutableStateOf("") }
    var attempted by rememberSaveable { mutableStateOf(false) }
    val chapter = catalog.find { it.id == route.id }?.chapters?.getOrNull(route.chapter)
    NotesScreen(
        NotesUiState(
            chapter?.title,
            if (route.id to route.chapter in session.highlights) chapter?.keyIdea else null,
            note,
            if (attempted && note.isBlank()) "Escreva uma nota antes de salvar." else null,
            session.notes.filter { it.materialId == route.id && it.chapter == route.chapter }.map { it.text },
        ),
        onNote = { note = it },
        onSave = {
            attempted = true
            if (chapter != null && note.isNotBlank()) {
                session.notes.add(StudyNote(route.id, route.chapter, note.trim()))
                note = ""
                attempted = false
            }
        },
    )
}

@Composable
internal fun CreateCardRoute(
    route: CreateCard,
    session: StudySession,
    onSaved: () -> Unit,
) {
    var question by rememberSaveable { mutableStateOf("") }
    var answer by rememberSaveable { mutableStateOf("") }
    var attempted by rememberSaveable { mutableStateOf(false) }
    val chapter = catalog.find { it.id == route.id }?.chapters?.getOrNull(route.chapter)
    val errors = cardErrors(question, answer)
    CreateCardScreen(
        CardFormUiState(chapter?.title, question, answer, if (attempted) errors else null to null),
        onQuestion = { question = it },
        onAnswer = { answer = it },
        onSave = {
            attempted = true
            if (chapter != null && errors.first == null && errors.second == null) {
                session.cards.add(StudyCard(route.id, route.chapter, question.trim(), answer.trim()))
                onSaved()
            }
        },
    )
}
