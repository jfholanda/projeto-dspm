package br.ufrn.revysa

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview(name = "HomePreview", widthDp = 390, heightDp = 740)
@Composable
internal fun HomePreview() {
    PreviewFrame { HomeScreen(HomeUiState(listOf(PreviewFixtures.item), 2), {}, {}, {}, {}) }
}

@Preview(name = "HomeEmptyPreview", widthDp = 390, heightDp = 740)
@Composable
internal fun HomeEmptyPreview() {
    PreviewFrame { HomeScreen(HomeUiState(emptyList(), 0), {}, {}, {}, {}) }
}

@Preview(name = "LibraryPreview", widthDp = 390, heightDp = 740)
@Composable
internal fun LibraryPreview() {
    PreviewFrame { LibraryScreen(LibraryUiState("", false, listOf(PreviewFixtures.item)), {}, {}, {}, {}) }
}

@Preview(name = "LibraryEmptyPreview", widthDp = 390, heightDp = 740)
@Composable
internal fun LibraryEmptyPreview() {
    PreviewFrame { LibraryScreen(LibraryUiState("inexistente", false, emptyList()), {}, {}, {}, {}) }
}

@Preview(name = "SubjectsPreview", widthDp = 390, heightDp = 740)
@Composable
internal fun SubjectsPreview() {
    PreviewFrame { SubjectsScreen(listOf(PreviewFixtures.material), {}) }
}

@Preview(name = "MaterialPreview", widthDp = 390, heightDp = 740)
@Composable
internal fun MaterialPreview() {
    PreviewFrame { MaterialScreen(MaterialUiState(PreviewFixtures.material, 0.5f, true), {}, {}, {}, {}, {}) }
}

@Preview(name = "Biblioteca larga escura", widthDp = 1000, heightDp = 740)
@Composable
internal fun LibraryWideDarkPreview() {
    PreviewFrame(dark = true) { LibraryScreen(LibraryUiState("", false, listOf(PreviewFixtures.item)), {}, {}, {}, {}) }
}
