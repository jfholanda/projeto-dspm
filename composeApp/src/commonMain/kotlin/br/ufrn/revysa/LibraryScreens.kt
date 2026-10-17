package br.ufrn.revysa

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
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
import androidx.compose.ui.unit.dp

@Composable
internal fun HomeScreen(
    session: StudySession,
    navigate: (Any) -> Unit,
) {
    Screen("Seu próximo passo de estudo") {
        item { Text("Leia, registre ideias e revise o que aprendeu.") }
        item { Action("Começar revisão", "start-review") { navigate(Review) } }
        item { Action("Abrir biblioteca", "open-library") { navigate(LibraryGraph) } }
        item { Action("Ver disciplinas", "open-subjects") { navigate(Subjects) } }
        item { Text("Continue estudando", style = MaterialTheme.typography.titleLarge) }
        items(catalog.filter { it.id in session.savedMaterials }, key = { it.id }) { material ->
            MaterialCard(material, session.progress(material)) { navigate(Material(material.id)) }
        }
        item { Text("${session.reviewed} revisões realizadas nesta sessão.") }
    }
}

@Composable
internal fun LibraryScreen(
    session: StudySession,
    navigate: (Any) -> Unit,
) {
    var explore by rememberSaveable { mutableStateOf(false) }
    var query by rememberSaveable { mutableStateOf("") }
    val shown =
        catalog.filter {
            (explore || it.id in session.savedMaterials) &&
                (it.title.contains(query, true) || it.subject.contains(query, true))
        }
    Screen("Sua biblioteca") {
        item {
            FilterChip(modifier = Modifier.heightIn(min = 48.dp), selected = !explore, onClick = {
                explore = false
            }, label = { Text("Meus materiais") })
            FilterChip(
                selected = explore,
                onClick = { explore = true },
                label = { Text("Explorar catálogo") },
                modifier = Modifier.heightIn(min = 48.dp).testTag("explore"),
            )
        }
        item {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                label = { Text("Buscar material ou disciplina") },
                modifier = Modifier.fillMaxWidth().testTag("search"),
                singleLine = true,
            )
        }
        item { Action("Ver disciplinas", "subjects") { navigate(Subjects) } }
        if (shown.isEmpty()) item { EmptyMessage("Nenhum material encontrado. Tente outra busca ou explore o catálogo.") }
        items(shown, key = { it.id }) { material ->
            MaterialCard(material, session.progress(material)) { navigate(Material(material.id)) }
        }
    }
}

@Composable
internal fun SubjectsScreen(navigate: (Any) -> Unit) {
    Screen("Disciplinas") {
        items(catalog, key = { it.id }) { material ->
            Card(onClick = { navigate(Material(material.id)) }, modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(material.subject, style = MaterialTheme.typography.titleLarge)
                    Text(material.title)
                }
            }
        }
    }
}

@Composable
internal fun MaterialScreen(
    id: String,
    session: StudySession,
    navigate: (Any) -> Unit,
) {
    val material = catalog.find { it.id == id }
    if (material == null) {
        Screen("Material indisponível") {
            item { EmptyMessage("Este material não foi encontrado no catálogo.") }
            item { Action("Abrir biblioteca", "open-library") { navigate(LibraryGraph) } }
        }
        return
    }
    Screen(material.title) {
        item { Text(material.subject, color = MaterialTheme.colorScheme.primary) }
        item { Text(material.description) }
        item { ReadingProgress(session.progress(material)) }
        if (id !in session.savedMaterials) {
            item { Action("Adicionar à biblioteca", "add-material") { session.savedMaterials.add(id) } }
        }
        item { Text("Capítulos", style = MaterialTheme.typography.titleLarge) }
        items(material.chapters.indices.toList(), key = { it }) { index ->
            Action("${index + 1}. ${material.chapters[index].title}", "chapter-$index") { navigate(Reader(id, index)) }
        }
        item { Action("Criar flashcard", "create-card") { navigate(CreateCard(id, 0)) } }
        item { Action("Fazer quiz", "open-quiz") { navigate(Quiz(id)) } }
    }
}
