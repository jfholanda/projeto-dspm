package br.ufrn.revysa

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
internal fun ReviewScreen(
    state: ReviewUiState,
    onReveal: () -> Unit,
    onRate: () -> Unit,
    onRestart: () -> Unit,
) {
    val card = state.card
    Screen("Revisão de hoje") {
        if (card == null) {
            item { Text("Revisão concluída", modifier = Modifier.testTag("review-complete")) }
            item { Text("${state.total} cartões revisados nesta rodada.") }
            item {
                Action("Revisar novamente", "restart-review", onClick = onRestart)
            }
        } else {
            item { Text("Cartão ${state.index + 1} de ${state.total}") }
            item { Text(state.materialTitle) }
            item {
                Card(Modifier.fillMaxWidth()) {
                    Text(card.question, Modifier.padding(24.dp).testTag("review-question"), style = MaterialTheme.typography.titleLarge)
                }
            }
            if (state.revealed) {
                item {
                    Text(card.answer, modifier = Modifier.testTag("review-answer").semantics { liveRegion = LiveRegionMode.Polite })
                }
                items(listOf("Errei", "Difícil", "Acertei", "Fácil")) { rating ->
                    Action(rating, "rating-$rating", onClick = onRate)
                }
            } else {
                item { Action("Revelar resposta", "reveal", onClick = onReveal) }
            }
        }
    }
}

@Composable
internal fun QuizScreen(
    state: QuizUiState,
    onSelect: (Int) -> Unit,
    onSubmit: () -> Unit,
    onRetry: () -> Unit,
) {
    val material = state.material
    Screen("Quiz") {
        if (material == null) {
            item { EmptyMessage("Material não encontrado.") }
        } else {
            item { Text(material.title) }
            item { Text(material.question, style = MaterialTheme.typography.titleLarge) }
            items(material.answers.indices.toList()) { index ->
                Row(
                    modifier =
                        Modifier.fillMaxWidth().heightIn(min = 48.dp)
                            .selectable(
                                selected = state.selected == index,
                                enabled = !state.submitted,
                                role = Role.RadioButton,
                                onClick = { onSelect(index) },
                            )
                            .padding(12.dp).testTag("option-$index"),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    RadioButton(selected = state.selected == index, onClick = null)
                    Text(material.answers[index], modifier = Modifier.padding(start = 12.dp))
                }
            }
            item {
                Action("Conferir resposta", "submit-quiz", enabled = state.selected >= 0 && !state.submitted, onClick = onSubmit)
            }
            if (state.submitted) {
                item {
                    Text(
                        if (state.selected == 0) "Correto! ${material.answers.first()}." else "Vamos revisar: ${material.answers.first()}.",
                        modifier = Modifier.testTag("quiz-feedback").semantics { liveRegion = LiveRegionMode.Polite },
                    )
                }
                item {
                    Action("Tentar novamente", "retry-quiz", onClick = onRetry)
                }
            }
        }
    }
}

@Composable
internal fun ProgressScreen(
    state: ProgressUiState,
    onMaterial: (String) -> Unit,
) {
    Screen("Seu progresso") {
        item { Text("Atividade desta sessão", style = MaterialTheme.typography.titleLarge) }
        item { Text("${state.readCount} seções lidas", modifier = Modifier.testTag("read-count")) }
        item { Text("${state.reviewed} cartões revisados") }
        item { Text("${state.cardCount} cartões disponíveis") }
        item { Text("${state.quizCorrect} quizzes com acerto de ${state.quizTotal} respondidos") }
        items(state.materials, key = { it.material.id }) { item ->
            MaterialCard(item.material, item.progress) { onMaterial(item.material.id) }
        }
    }
}
