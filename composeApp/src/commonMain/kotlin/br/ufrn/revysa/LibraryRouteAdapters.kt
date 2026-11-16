package br.ufrn.revysa

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import br.ufrn.revysa.domain.model.StudySnapshot
import br.ufrn.revysa.presentation.StudyViewModel
import br.ufrn.revysa.presentation.UiState
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun HomeRoute(navigate: (Any) -> Unit) {
    val viewModel = koinViewModel<StudyViewModel>()
    val state by viewModel.state.collectAsState()
    StudyStateContent(state, viewModel::retry) { snapshot ->
        val materials = viewModel.materials().filter { it.id in snapshot.savedMaterialIds }
        HomeScreen(
            HomeUiState(materialItems(snapshot, materials, viewModel), snapshot.reviewed, viewModel.homeSummary(snapshot)),
            onReview = { navigate(Review) },
            onLibrary = { navigate(LibraryGraph) },
            onSubjects = { navigate(Subjects) },
            onExplore = { navigate(Subjects) },
            onContinueReading = { target -> navigate(Reader(target.materialId, target.chapterIndex)) },
            onMaterial = { navigate(Material(it)) },
        )
    }
}

@Composable
internal fun LibraryRoute(navigate: (Any) -> Unit) {
    val viewModel = koinViewModel<StudyViewModel>()
    val state by viewModel.state.collectAsState()
    var query by rememberSaveable { mutableStateOf("") }
    var explore by rememberSaveable { mutableStateOf(false) }
    StudyStateContent(state, viewModel::retry) { snapshot ->
        val materials = viewModel.libraryMaterials(snapshot, query, explore)
        LibraryScreen(
            LibraryUiState(query, explore, materialItems(snapshot, materials, viewModel)),
            onQuery = { query = it },
            onExplore = { explore = it },
            onSubjects = { navigate(Subjects) },
            onMaterial = { navigate(Material(it)) },
        )
    }
}

@Composable
internal fun MaterialRoute(
    id: String,
    navigate: (Any) -> Unit,
) {
    val viewModel = koinViewModel<StudyViewModel>()
    val state by viewModel.state.collectAsState()
    StudyStateContent(state, viewModel::retry) { snapshot ->
        val material = viewModel.material(id)
        MaterialScreen(
            MaterialUiState(material, material?.let { viewModel.progress(snapshot, it) } ?: 0f, id in snapshot.savedMaterialIds),
            onLibrary = { navigate(LibraryGraph) },
            onAdd = { viewModel.addMaterial(id) },
            onChapter = { navigate(Reader(id, it)) },
            onCreateCard = { navigate(CreateCard(id, 0)) },
            onQuiz = { navigate(Quiz(id)) },
        )
    }
}

internal fun materialItems(
    snapshot: StudySnapshot,
    materials: List<StudyMaterial>,
    viewModel: StudyViewModel,
): List<MaterialItem> = materials.map { MaterialItem(it, viewModel.progress(snapshot, it)) }

@Composable
internal fun StudyStateContent(
    state: UiState<StudySnapshot>,
    onRetry: () -> Unit,
    content: @Composable (StudySnapshot) -> Unit,
) {
    when (state) {
        UiState.Loading -> Screen("Carregando") { item { CircularProgressIndicator() } }
        is UiState.Error ->
            Screen("Não foi possível carregar") {
                item { Text(state.message) }
                item { Action("Tentar novamente", "retry-load", onClick = onRetry) }
            }
        is UiState.Empty -> content(state.value)
        is UiState.Success -> content(state.value)
    }
}
