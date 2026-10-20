package br.ufrn.revysa

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview(name = "ReaderPreview", widthDp = 390, heightDp = 740)
@Composable
internal fun ReaderPreview() {
    PreviewFrame { ReaderScreen(ReaderUiState("Algoritmos", PreviewFixtures.chapter, false, false), {}, {}, {}, {}) }
}

@Preview(name = "NotesPreview", widthDp = 390, heightDp = 740)
@Composable
internal fun NotesPreview() {
    PreviewFrame {
        NotesScreen(
            NotesUiState("Busca binária", PreviewFixtures.chapter.keyIdea, "", null, listOf("Revisar a divisão do intervalo.")),
            {},
            {},
        )
    }
}

@Preview(name = "NotesEmptyPreview", widthDp = 390, heightDp = 740)
@Composable
internal fun NotesEmptyPreview() {
    PreviewFrame { NotesScreen(NotesUiState("Busca binária", null, "", null, emptyList()), {}, {}) }
}

@Preview(name = "CardFormPreview", widthDp = 390, heightDp = 740)
@Composable
internal fun CardFormPreview() {
    PreviewFrame { CreateCardScreen(CardFormUiState("Busca binária", "", "", null to null), {}, {}, {}) }
}

@Preview(name = "CardFormErrorsPreview", widthDp = 390, heightDp = 740)
@Composable
internal fun CardFormErrorsPreview() {
    PreviewFrame { CreateCardScreen(CardFormUiState("Busca binária", "", "", cardErrors("", "")), {}, {}, {}) }
}
