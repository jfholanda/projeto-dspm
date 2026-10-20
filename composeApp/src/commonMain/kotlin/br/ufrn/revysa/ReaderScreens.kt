package br.ufrn.revysa

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
internal fun ReaderScreen(
    state: ReaderUiState,
    onHighlight: () -> Unit,
    onNotes: () -> Unit,
    onCreateCard: () -> Unit,
    onRead: () -> Unit,
) {
    val chapter = state.chapter
    if (chapter == null) {
        Screen("Capítulo indisponível") { item { EmptyMessage("Volte à biblioteca e escolha um capítulo disponível.") } }
        return
    }
    Screen(chapter.title) {
        item { Text(state.materialTitle, style = MaterialTheme.typography.labelLarge) }
        item { Text(chapter.text, style = MaterialTheme.typography.bodyLarge) }
        item {
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Ideia-chave", style = MaterialTheme.typography.titleMedium)
                    Text(chapter.keyIdea, color = MaterialTheme.colorScheme.onPrimaryContainer)
                }
            }
        }
        item {
            Action(if (state.highlighted) "Remover destaque" else "Destacar ideia-chave", "highlight") {
                onHighlight()
            }
        }
        item { Action("Destaques e notas", "open-notes") { onNotes() } }
        item { Action("Criar flashcard deste conceito", "create-card") { onCreateCard() } }
        item {
            Action(if (state.read) "Seção lida" else "Marcar seção como lida", "mark-read", enabled = !state.read) {
                onRead()
            }
        }
        if (state.read) item { Text("Leitura registrada.", modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite }) }
    }
}

@Composable
internal fun NotesScreen(
    state: NotesUiState,
    onNote: (String) -> Unit,
    onSave: () -> Unit,
) {
    Screen("Destaques e notas") {
        if (state.chapterTitle == null) {
            item { EmptyMessage("Capítulo não encontrado.") }
        } else {
            item { Text(state.chapterTitle.orEmpty()) }
            item {
                Text(state.highlight ?: "Nenhum destaque neste capítulo.")
            }
            item {
                OutlinedTextField(
                    value = state.note,
                    onValueChange = onNote,
                    label = { Text("Sua nota") },
                    isError = state.error != null,
                    supportingText = { state.error?.let { Text(it) } },
                    keyboardOptions =
                        KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                        ),
                    modifier = Modifier.fillMaxWidth().testTag("note"),
                )
            }
            item {
                Action("Salvar nota", "save-note", onClick = onSave)
            }
            if (state.notes.isEmpty()) item { EmptyMessage("Nenhuma nota ainda. Registre sua primeira ideia.") }
            items(state.notes) { Text(it, modifier = Modifier.testTag("saved-note")) }
        }
    }
}

@Composable
internal fun CreateCardScreen(
    state: CardFormUiState,
    onQuestion: (String) -> Unit,
    onAnswer: (String) -> Unit,
    onSave: () -> Unit,
) {
    Screen("Criar flashcard") {
        if (state.chapterTitle == null) {
            item { EmptyMessage("Capítulo não encontrado.") }
        } else {
            item { Text("Vinculado ao capítulo: ${state.chapterTitle}") }
            item {
                OutlinedTextField(
                    value = state.question,
                    onValueChange = onQuestion,
                    label = { Text("Pergunta") },
                    keyboardOptions =
                        KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                        ),
                    isError = state.errors.first != null,
                    supportingText = { state.errors.first?.let { Text(it) } },
                    modifier = Modifier.fillMaxWidth().testTag("question"),
                )
            }
            item {
                OutlinedTextField(
                    value = state.answer,
                    onValueChange = onAnswer,
                    label = { Text("Resposta") },
                    keyboardOptions =
                        KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                        ),
                    isError = state.errors.second != null,
                    supportingText = { state.errors.second?.let { Text(it) } },
                    modifier = Modifier.fillMaxWidth().testTag("answer"),
                )
            }
            item {
                Action("Salvar cartão", "save-card", onClick = onSave)
            }
        }
    }
}
