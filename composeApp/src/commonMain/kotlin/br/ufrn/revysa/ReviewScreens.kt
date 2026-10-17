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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
internal fun ReviewScreen(session: StudySession) {
    // Uma sessão tem uma fila finita; cartões criados depois entram na próxima revisão.
    var queue by remember { mutableStateOf(session.cards.toList()) }
    var index by rememberSaveable { mutableIntStateOf(0) }
    var revealed by rememberSaveable { mutableStateOf(false) }
    val card = queue.getOrNull(index)
    Screen("Revisão de hoje") {
        if (card == null) {
            item { Text("Revisão concluída", modifier = Modifier.testTag("review-complete")) }
            item { Text("${queue.size} cartões revisados nesta rodada.") }
            item {
                Action("Revisar novamente", "restart-review") {
                    queue = session.cards.toList()
                    index = 0
                    revealed = false
                }
            }
        } else {
            item { Text("Cartão ${index + 1} de ${queue.size}") }
            item { Text(catalog.find { it.id == card.materialId }?.title.orEmpty()) }
            item {
                Card(Modifier.fillMaxWidth()) {
                    Text(card.question, Modifier.padding(24.dp).testTag("review-question"), style = MaterialTheme.typography.titleLarge)
                }
            }
            if (revealed) {
                item {
                    Text(card.answer, modifier = Modifier.testTag("review-answer").semantics { liveRegion = LiveRegionMode.Polite })
                }
                items(listOf("Errei", "Difícil", "Acertei", "Fácil")) { rating ->
                    Action(rating, "rating-$rating") {
                        session.reviewed++
                        index++
                        revealed = false
                    }
                }
            } else {
                item { Action("Revelar resposta", "reveal") { revealed = true } }
            }
        }
    }
}

@Composable
internal fun QuizScreen(
    id: String,
    session: StudySession,
) {
    val material = catalog.find { it.id == id }
    var selected by rememberSaveable { mutableIntStateOf(-1) }
    var submitted by rememberSaveable { mutableStateOf(false) }
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
                                selected = selected == index,
                                enabled = !submitted,
                                role = Role.RadioButton,
                                onClick = { selected = index },
                            )
                            .padding(12.dp).testTag("option-$index"),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    RadioButton(selected = selected == index, onClick = null)
                    Text(material.answers[index], modifier = Modifier.padding(start = 12.dp))
                }
            }
            item {
                Action("Conferir resposta", "submit-quiz", enabled = selected >= 0 && !submitted) {
                    submitted = true
                    session.quizResults[id] = selected == 0
                }
            }
            if (submitted) {
                item {
                    Text(
                        if (selected == 0) "Correto! ${material.answers.first()}." else "Vamos revisar: ${material.answers.first()}.",
                        modifier = Modifier.testTag("quiz-feedback").semantics { liveRegion = LiveRegionMode.Polite },
                    )
                }
                item {
                    Action("Tentar novamente", "retry-quiz") {
                        selected = -1
                        submitted = false
                    }
                }
            }
        }
    }
}

@Composable
internal fun ProgressScreen(
    session: StudySession,
    navigate: (Any) -> Unit,
) {
    Screen("Seu progresso") {
        item { Text("Atividade desta sessão", style = MaterialTheme.typography.titleLarge) }
        item { Text("${session.readChapters.size} seções lidas", modifier = Modifier.testTag("read-count")) }
        item { Text("${session.reviewed} cartões revisados") }
        item { Text("${session.cards.size} cartões disponíveis") }
        item { Text("${session.quizResults.count { it.value }} quizzes com acerto de ${session.quizResults.size} respondidos") }
        items(catalog.filter { it.id in session.savedMaterials }, key = { it.id }) { material ->
            MaterialCard(material, session.progress(material)) { navigate(Material(material.id)) }
        }
    }
}
