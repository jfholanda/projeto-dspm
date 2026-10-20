package br.ufrn.revysa

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

internal fun materialItems(
    session: StudySession,
    materials: List<StudyMaterial>,
): List<MaterialItem> = materials.map { MaterialItem(it, session.progress(it)) }

@Composable
internal fun HomeRoute(
    session: StudySession,
    navigate: (Any) -> Unit,
) {
    HomeScreen(
        HomeUiState(materialItems(session, catalog.filter { it.id in session.savedMaterials }), session.reviewed),
        onReview = { navigate(Review) },
        onLibrary = { navigate(LibraryGraph) },
        onSubjects = { navigate(Subjects) },
        onMaterial = { navigate(Material(it)) },
    )
}

@Composable
internal fun LibraryRoute(
    session: StudySession,
    navigate: (Any) -> Unit,
) {
    var query by rememberSaveable { mutableStateOf("") }
    var explore by rememberSaveable { mutableStateOf(false) }
    val materials =
        catalog.filter {
            (explore || it.id in session.savedMaterials) && (it.title.contains(query, true) || it.subject.contains(query, true))
        }
    LibraryScreen(
        LibraryUiState(query, explore, materialItems(session, materials)),
        onQuery = { query = it },
        onExplore = { explore = it },
        onSubjects = { navigate(Subjects) },
        onMaterial = { navigate(Material(it)) },
    )
}

@Composable
internal fun MaterialRoute(
    id: String,
    session: StudySession,
    navigate: (Any) -> Unit,
) {
    val material = catalog.find { it.id == id }
    MaterialScreen(
        MaterialUiState(material, material?.let(session::progress) ?: 0f, id in session.savedMaterials),
        onLibrary = { navigate(LibraryGraph) },
        onAdd = { if (material != null && id !in session.savedMaterials) session.savedMaterials.add(id) },
        onChapter = { navigate(Reader(id, it)) },
        onCreateCard = { navigate(CreateCard(id, 0)) },
        onQuiz = { navigate(Quiz(id)) },
    )
}
