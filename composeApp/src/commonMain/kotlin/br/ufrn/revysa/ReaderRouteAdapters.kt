package br.ufrn.revysa

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import br.ufrn.revysa.domain.model.ChapterReference
import br.ufrn.revysa.presentation.StudyViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun ReaderRoute(
    route: Reader,
    navigate: (Any) -> Unit,
) {
    val viewModel = koinViewModel<StudyViewModel>()
    val state by viewModel.state.collectAsState()
    StudyStateContent(state, viewModel::retry) { snapshot ->
        val material = viewModel.material(route.id)
        val reference = ChapterReference(route.id, route.chapter)
        ReaderScreen(
            ReaderUiState(
                material?.title.orEmpty(),
                material?.chapters?.getOrNull(route.chapter),
                reference in snapshot.highlights,
                reference in snapshot.readChapters,
            ),
            onHighlight = { viewModel.toggleHighlight(route.id, route.chapter) },
            onNotes = { navigate(Notes(route.id, route.chapter)) },
            onCreateCard = { navigate(CreateCard(route.id, route.chapter)) },
            onRead = { viewModel.markChapterRead(route.id, route.chapter) },
        )
    }
}

@Composable
internal fun NotesRoute(route: Notes) {
    val viewModel = koinViewModel<StudyViewModel>()
    val state by viewModel.state.collectAsState()
    var note by rememberSaveable { mutableStateOf("") }
    var attempted by rememberSaveable { mutableStateOf(false) }
    StudyStateContent(state, viewModel::retry) { snapshot ->
        val chapter = viewModel.material(route.id)?.chapters?.getOrNull(route.chapter)
        val reference = ChapterReference(route.id, route.chapter)
        NotesScreen(
            NotesUiState(
                chapter?.title,
                if (reference in snapshot.highlights) chapter?.keyIdea else null,
                note,
                if (attempted && note.isBlank()) "Escreva uma nota antes de salvar." else null,
                snapshot.notes.filter { it.materialId == route.id && it.chapter == route.chapter }.map { it.text },
            ),
            onNote = { note = it },
            onSave = {
                attempted = true
                if (chapter != null && note.isNotBlank()) {
                    viewModel.saveNote(route.id, route.chapter, note)
                    note = ""
                    attempted = false
                }
            },
        )
    }
}

@Composable
internal fun CreateCardRoute(
    route: CreateCard,
    onSaved: () -> Unit,
) {
    val viewModel = koinViewModel<StudyViewModel>()
    val state by viewModel.state.collectAsState()
    var question by rememberSaveable { mutableStateOf("") }
    var answer by rememberSaveable { mutableStateOf("") }
    var attempted by rememberSaveable { mutableStateOf(false) }
    StudyStateContent(state, viewModel::retry) {
        val chapter = viewModel.material(route.id)?.chapters?.getOrNull(route.chapter)
        val errors = cardErrors(question, answer)
        CreateCardScreen(
            CardFormUiState(chapter?.title, question, answer, if (attempted) errors else null to null),
            onQuestion = { question = it },
            onAnswer = { answer = it },
            onSave = {
                attempted = true
                if (viewModel.createCard(route.id, route.chapter, question, answer)) onSaved()
            },
        )
    }
}
