package br.ufrn.revysa

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

@Composable
internal fun ReviewRoute(session: StudySession) {
    // A fila e a posição são salvas juntas, evitando inconsistência ao retornar à rota.
    var queue by rememberSaveable(stateSaver = cardQueueSaver) { mutableStateOf(session.cards.toList()) }
    var index by rememberSaveable { mutableIntStateOf(0) }
    var revealed by rememberSaveable { mutableStateOf(false) }
    val card = queue.getOrNull(index)
    ReviewScreen(
        ReviewUiState(card, catalog.find { it.id == card?.materialId }?.title.orEmpty(), index, queue.size, revealed),
        onReveal = { revealed = true },
        onRate = {
            if (card != null && revealed) {
                session.reviewed++
                index++
                revealed = false
            }
        },
        onRestart = {
            queue = session.cards.toList()
            index = 0
            revealed = false
        },
    )
}

@Composable
internal fun QuizRoute(
    id: String,
    session: StudySession,
) {
    var selected by rememberSaveable { mutableIntStateOf(-1) }
    var submitted by rememberSaveable { mutableStateOf(false) }
    val material = catalog.find { it.id == id }
    QuizScreen(
        QuizUiState(material, selected, submitted),
        onSelect = { selected = it },
        onSubmit = {
            if (material != null && selected in material.answers.indices && !submitted) {
                submitted = true
                session.quizResults[id] = selected == 0
            }
        },
        onRetry = {
            selected = -1
            submitted = false
        },
    )
}

@Composable
internal fun ProgressRoute(
    session: StudySession,
    navigate: (Any) -> Unit,
) {
    ProgressScreen(
        ProgressUiState(
            session.readChapters.size,
            session.reviewed,
            session.cards.size,
            session.quizResults.count { it.value },
            session.quizResults.size,
            materialItems(session, catalog.filter { it.id in session.savedMaterials }),
        ),
        onMaterial = { navigate(Material(it)) },
    )
}

@Composable
internal fun AuthRoute(
    register: Boolean,
    navigate: (Any) -> Unit,
) {
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var attempted by rememberSaveable { mutableStateOf(false) }
    val errors = authErrors(register, name, email, password)
    AuthScreen(
        AuthUiState(register, name, email, password, if (attempted) errors else AuthErrors(null, null, null)),
        onName = { name = it },
        onEmail = { email = it },
        onPassword = { password = it },
        onSubmit = {
            attempted = true
            if (errors.valid) {
                password = ""
                navigate(Home)
            }
        },
        onSwitch = {
            password = ""
            navigate(if (register) Login else Register)
        },
    )
}

private val cardQueueSaver =
    androidx.compose.runtime.saveable.listSaver<List<StudyCard>, Any>(
        save = { cards -> cards.flatMap { listOf(it.materialId, it.chapter, it.question, it.answer) } },
        restore = {
                values ->
            values.chunked(CARD_FIELDS).map { StudyCard(it[0] as String, it[1] as Int, it[2] as String, it[3] as String) }
        },
    )
private const val CARD_FIELDS = 4
