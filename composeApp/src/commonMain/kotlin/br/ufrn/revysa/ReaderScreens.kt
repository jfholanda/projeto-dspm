package br.ufrn.revysa

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
internal fun ReaderScreen(
    route: Reader,
    session: StudySession,
    navigate: (Any) -> Unit,
) {
    val material = catalog.find { it.id == route.id }
    val chapter = material?.chapters?.getOrNull(route.chapter)
    if (chapter == null) {
        Screen("Capítulo indisponível") { item { EmptyMessage("Volte à biblioteca e escolha um capítulo disponível.") } }
        return
    }
    val highlighted = route.id to route.chapter in session.highlights
    val read = route.id to route.chapter in session.readChapters
    Screen(chapter.title) {
        item { Text(material.title, style = MaterialTheme.typography.labelLarge) }
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
            Action(if (highlighted) "Remover destaque" else "Destacar ideia-chave", "highlight") {
                session.toggleHighlight(route.id, route.chapter)
            }
        }
        item { Action("Destaques e notas", "open-notes") { navigate(Notes(route.id, route.chapter)) } }
        item { Action("Criar flashcard deste conceito", "create-card") { navigate(CreateCard(route.id, route.chapter)) } }
        item {
            Action(if (read) "Seção lida" else "Marcar seção como lida", "mark-read", enabled = !read) {
                session.markRead(route.id, route.chapter)
            }
        }
        if (read) item { Text("Leitura registrada.", modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite }) }
    }
}

@Composable
internal fun NotesScreen(
    route: Notes,
    session: StudySession,
) {
    val chapter = catalog.find { it.id == route.id }?.chapters?.getOrNull(route.chapter)
    var note by rememberSaveable { mutableStateOf("") }
    var attempted by rememberSaveable { mutableStateOf(false) }
    val error = attempted && note.isBlank()
    val notes = session.notes.filter { it.materialId == route.id && it.chapter == route.chapter }
    Screen("Destaques e notas") {
        if (chapter == null) {
            item { EmptyMessage("Capítulo não encontrado.") }
        } else {
            item { Text(chapter.title) }
            item {
                Text(if (route.id to route.chapter in session.highlights) chapter.keyIdea else "Nenhum destaque neste capítulo.")
            }
            item {
                OutlinedTextField(
                    value = note,
                    onValueChange = { note = it },
                    label = { Text("Sua nota") },
                    isError = error,
                    supportingText = { if (error) Text("Escreva uma nota antes de salvar.") },
                    modifier = Modifier.fillMaxWidth().testTag("note"),
                )
            }
            item {
                Action("Salvar nota", "save-note") {
                    attempted = true
                    if (note.isNotBlank()) {
                        session.notes.add(StudyNote(route.id, route.chapter, note.trim()))
                        note = ""
                        attempted = false
                    }
                }
            }
            if (notes.isEmpty()) item { EmptyMessage("Nenhuma nota ainda. Registre sua primeira ideia.") }
            items(notes) { Text(it.text, modifier = Modifier.testTag("saved-note")) }
        }
    }
}

@Composable
internal fun CreateCardScreen(
    route: CreateCard,
    session: StudySession,
    onSaved: () -> Unit,
) {
    val chapter = catalog.find { it.id == route.id }?.chapters?.getOrNull(route.chapter)
    var question by rememberSaveable { mutableStateOf("") }
    var answer by rememberSaveable { mutableStateOf("") }
    var attempted by rememberSaveable { mutableStateOf(false) }
    val errors = cardErrors(question, answer)
    Screen("Criar flashcard") {
        if (chapter == null) {
            item { EmptyMessage("Capítulo não encontrado.") }
        } else {
            item { Text("Vinculado ao capítulo: ${chapter.title}") }
            item {
                OutlinedTextField(
                    value = question,
                    onValueChange = { question = it },
                    label = { Text("Pergunta") },
                    isError = attempted && errors.first != null,
                    supportingText = { if (attempted) errors.first?.let { Text(it) } },
                    modifier = Modifier.fillMaxWidth().testTag("question"),
                )
            }
            item {
                OutlinedTextField(
                    value = answer,
                    onValueChange = { answer = it },
                    label = { Text("Resposta") },
                    isError = attempted && errors.second != null,
                    supportingText = { if (attempted) errors.second?.let { Text(it) } },
                    modifier = Modifier.fillMaxWidth().testTag("answer"),
                )
            }
            item {
                Action("Salvar cartão", "save-card") {
                    attempted = true
                    if (errors.first == null && errors.second == null) {
                        session.cards.add(StudyCard(route.id, route.chapter, question.trim(), answer.trim()))
                        onSaved()
                    }
                }
            }
        }
    }
}
