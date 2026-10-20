package br.ufrn.revysa

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview(name = "ReviewPreview", widthDp = 390, heightDp = 740)
@Composable
internal fun ReviewPreview() {
    PreviewFrame { ReviewScreen(ReviewUiState(PreviewFixtures.card, "Algoritmos", 0, 1, true), {}, {}, {}) }
}

@Preview(name = "ReviewEmptyPreview", widthDp = 390, heightDp = 740)
@Composable
internal fun ReviewEmptyPreview() {
    PreviewFrame { ReviewScreen(ReviewUiState(null, "", 0, 0, false), {}, {}, {}) }
}

@Preview(name = "QuizPreview", widthDp = 390, heightDp = 740)
@Composable
internal fun QuizPreview() {
    PreviewFrame { QuizScreen(QuizUiState(PreviewFixtures.material, 0, true), {}, {}, {}) }
}

@Preview(name = "ProgressPreview", widthDp = 390, heightDp = 740)
@Composable
internal fun ProgressPreview() {
    PreviewFrame { ProgressScreen(ProgressUiState(1, 2, 3, 1, 1, listOf(PreviewFixtures.item)), {}) }
}

@Preview(name = "ProgressEmptyPreview", widthDp = 390, heightDp = 740)
@Composable
internal fun ProgressEmptyPreview() {
    PreviewFrame { ProgressScreen(ProgressUiState(0, 0, 0, 0, 0, emptyList()), {}) }
}
