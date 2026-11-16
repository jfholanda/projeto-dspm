package br.ufrn.revysa

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import br.ufrn.revysa.presentation.StudyViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun ReviewRoute() {
    val viewModel = koinViewModel<StudyViewModel>()
    val state by viewModel.state.collectAsState()
    var queue by rememberSaveable(stateSaver = cardQueueSaver) { mutableStateOf(viewModel.dueCards()) }
    var index by rememberSaveable { mutableIntStateOf(0) }
    var revealed by rememberSaveable { mutableStateOf(false) }
    StudyStateContent(state, viewModel::retry) { _ ->
        val card = queue.getOrNull(index)
        val materialTitle = viewModel.material(card?.materialId.orEmpty())?.title.orEmpty()
        ReviewScreen(
            ReviewUiState(card, materialTitle, index, queue.size, revealed),
            onReveal = { revealed = true },
            onRate = { rating ->
                if (card != null && revealed) {
                    viewModel.recordReview(card.id, rating)
                    index++
                    revealed = false
                }
            },
            onRestart = {
                queue = viewModel.dueCards()
                index = 0
                revealed = false
            },
        )
    }
}

@Composable
internal fun QuizRoute(id: String) {
    val viewModel = koinViewModel<StudyViewModel>()
    val state by viewModel.state.collectAsState()
    var selected by rememberSaveable { mutableIntStateOf(-1) }
    var submitted by rememberSaveable { mutableStateOf(false) }
    StudyStateContent(state, viewModel::retry) {
        val material = viewModel.material(id)
        QuizScreen(
            QuizUiState(material, selected, submitted),
            onSelect = { selected = it },
            onSubmit = {
                if (!submitted && viewModel.submitQuiz(id, selected) != null) submitted = true
            },
            onRetry = {
                selected = -1
                submitted = false
            },
        )
    }
}

@Composable
internal fun ProgressRoute(navigate: (Any) -> Unit) {
    val viewModel = koinViewModel<StudyViewModel>()
    val state by viewModel.state.collectAsState()
    StudyStateContent(state, viewModel::retry) { snapshot ->
        val materials = viewModel.materials().filter { it.id in snapshot.savedMaterialIds }
        ProgressScreen(
            ProgressUiState(
                snapshot.readChapters.size,
                snapshot.reviewed,
                snapshot.cards.size,
                snapshot.quizResults.count { it.value },
                snapshot.quizResults.size,
                materialItems(snapshot, materials, viewModel),
            ),
            onMaterial = { navigate(Material(it)) },
        )
    }
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
        save = { cards ->
            cards.flatMap { listOf(it.materialId, it.chapter, it.question, it.answer, it.id, it.intervalDays, it.nextReviewAtEpochMillis) }
        },
        restore = { values ->
            values.chunked(CARD_FIELDS).map {
                StudyCard(it[0] as String, it[1] as Int, it[2] as String, it[3] as String, it[4] as String, it[5] as Double, it[6] as Long)
            }
        },
    )

private const val CARD_FIELDS = 7
