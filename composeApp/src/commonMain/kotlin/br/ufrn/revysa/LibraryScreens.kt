package br.ufrn.revysa

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
internal fun HomeScreen(
    state: HomeUiState,
    onReview: () -> Unit,
    onLibrary: () -> Unit,
    onSubjects: () -> Unit,
    onMaterial: (String) -> Unit,
) {
    Screen("Seu próximo passo de estudo") {
        item { Text("Leia, registre ideias e revise o que aprendeu.") }
        item { Action("Começar revisão", "start-review", onClick = onReview) }
        item { Action("Abrir biblioteca", "open-library", onClick = onLibrary) }
        item { Action("Ver disciplinas", "open-subjects", onClick = onSubjects) }
        item { Text("Continue estudando", style = MaterialTheme.typography.titleLarge) }
        if (state.materials.isEmpty()) item { EmptyMessage("Sua biblioteca está vazia. Explore o catálogo para começar.") }
        items(state.materials, key = { it.material.id }) { item ->
            MaterialCard(item.material, item.progress) { onMaterial(item.material.id) }
        }
        item { Text("${state.reviewed} revisões realizadas nesta sessão.") }
    }
}

@Composable
internal fun LibraryScreen(
    state: LibraryUiState,
    onQuery: (String) -> Unit,
    onExplore: (Boolean) -> Unit,
    onSubjects: () -> Unit,
    onMaterial: (String) -> Unit,
) {
    Screen("Sua biblioteca") {
        item {
            FilterChip(
                modifier = Modifier.heightIn(min = 48.dp),
                selected = !state.explore,
                onClick = { onExplore(false) },
                label = { Text("Meus materiais") },
            )
            FilterChip(
                selected = state.explore,
                onClick = { onExplore(true) },
                label = { Text("Explorar catálogo") },
                modifier = Modifier.heightIn(min = 48.dp).testTag("explore"),
            )
        }
        item {
            OutlinedTextField(
                value = state.query,
                onValueChange = onQuery,
                label = { Text("Buscar material ou disciplina") },
                keyboardOptions =
                    KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search,
                    ),
                keyboardActions =
                    KeyboardActions(onSearch = {
                        defaultKeyboardAction(ImeAction.Done)
                    }),
                modifier = Modifier.fillMaxWidth().testTag("search"),
                singleLine = true,
            )
        }
        item { Action("Ver disciplinas", "subjects", onClick = onSubjects) }
        if (state.materials.isEmpty()) item { EmptyMessage("Nenhum material encontrado. Tente outra busca ou explore o catálogo.") }
        items(state.materials, key = { it.material.id }) { item ->
            MaterialCard(item.material, item.progress) { onMaterial(item.material.id) }
        }
    }
}

@Composable
internal fun SubjectsScreen(
    materials: List<StudyMaterial>,
    onMaterial: (String) -> Unit,
) {
    Screen("Disciplinas") {
        if (materials.isEmpty()) item { EmptyMessage("Nenhuma disciplina disponível.") }
        items(materials, key = { it.id }) { material ->
            Card(onClick = { onMaterial(material.id) }, modifier = Modifier.fillMaxWidth()) {
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
    state: MaterialUiState,
    onLibrary: () -> Unit,
    onAdd: () -> Unit,
    onChapter: (Int) -> Unit,
    onCreateCard: () -> Unit,
    onQuiz: () -> Unit,
) {
    val material = state.material
    if (material == null) {
        Screen("Material indisponível") {
            item { EmptyMessage("Este material não foi encontrado no catálogo.") }
            item { Action("Abrir biblioteca", "open-library", onClick = onLibrary) }
        }
        return
    }
    Screen(material.title) {
        item { Text(material.subject, color = MaterialTheme.colorScheme.primary) }
        item { Text(material.description) }
        item { ReadingProgress(state.progress) }
        if (!state.saved) item { Action("Adicionar à biblioteca", "add-material", onClick = onAdd) }
        item { Text("Capítulos", style = MaterialTheme.typography.titleLarge) }
        items(material.chapters.indices.toList(), key = { it }) { index ->
            Action("${index + 1}. ${material.chapters[index].title}", "chapter-$index") { onChapter(index) }
        }
        item { Action("Criar flashcard", "create-card", onClick = onCreateCard) }
        item { Action("Fazer quiz", "open-quiz", onClick = onQuiz) }
    }
}
