package br.ufrn.revysa

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toAwtImage
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertHeightIsAtLeast
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.unit.dp
import org.junit.Rule
import org.junit.Test
import java.io.File
import javax.imageio.ImageIO

@Suppress("TooManyFunctions") // Casos independentes de aceite da sprint.
class SprintOneUiTest {
    @get:Rule
    val compose = createComposeRule()

    private var expectedWidth = 390

    private fun launch(
        width: Int = 390,
        link: String? = null,
    ) {
        expectedWidth = width
        compose.setContent {
            Box(Modifier.requiredSize(width.dp, 740.dp).testTag("app")) { App(deepLink = link) }
        }
    }

    private fun click(tag: String) {
        val node = compose.onNodeWithTag(tag)
        if (!node.isDisplayed()) compose.onNodeWithTag("screen").performScrollToNode(hasTestTag(tag))
        node.performClick()
    }

    private fun capture(name: String) {
        val directory = File("build/reports/sprint-1")
        directory.mkdirs()
        val frame = compose.onNodeWithTag("app").captureToImage().toAwtImage()
        kotlin.test.assertEquals(expectedWidth, frame.width, "A captura deve mostrar toda a largura testada.")
        ImageIO.write(frame, "png", File(directory, "$name.png"))
    }

    @Test
    fun libraryOpensMaterialAndSelectedChapter() {
        launch()
        click("open-library")
        click("material-design-software")
        click("chapter-1")
        compose.onNodeWithText("Inversão de dependência").assertIsDisplayed()
        click("back")
        compose.onNodeWithText("Capítulos").assertIsDisplayed()
    }

    @Test
    fun emptyFlashcardShowsBothErrors() {
        launch(link = "revysa://material/machine-learning")
        click("create-card")
        click("save-card")
        compose.onNodeWithText("Informe a pergunta.").assertIsDisplayed()
        compose.onNodeWithText("Informe a resposta.").assertIsDisplayed()
    }

    @Test
    fun whitespaceFlashcardCannotBeSaved() {
        launch(link = "revysa://material/redes")
        click("create-card")
        compose.onNodeWithTag("question").performTextInput("   ")
        compose.onNodeWithTag("answer").performTextInput("   ")
        click("save-card")
        compose.onNodeWithText("Informe a pergunta.").assertIsDisplayed()
    }

    @Test
    fun newFlashcardCanBeReviewed() {
        launch(link = "revysa://material/design-software")
        click("create-card")
        compose.onNodeWithTag("question").performTextInput("Por que usar contratos?")
        compose.onNodeWithTag("answer").performTextInput("Para substituir implementações.")
        click("save-card")
        click("nav-2")
        click("reveal")
        click("rating-Acertei")
        compose.onNodeWithTag("review-question").assertTextContains("Por que usar contratos?")
        click("reveal")
        compose.onNodeWithTag("review-answer").assertTextContains("Para substituir implementações.")
        click("rating-Fácil")
        compose.onNodeWithTag("review-complete").assertIsDisplayed()
    }

    @Test
    fun quizRequiresAnAnswerAndShowsFeedback() {
        launch(link = "revysa://material/redes")
        click("open-quiz")
        compose.onNodeWithTag("submit-quiz").assertIsNotEnabled()
        click("option-0")
        compose.onNodeWithTag("submit-quiz").assertIsEnabled()
        click("submit-quiz")
        compose.onNodeWithTag("quiz-feedback").performScrollTo().assertTextContains("Correto!", substring = true)
    }

    @Test
    fun notesAndHighlightsRemainAfterReturning() {
        launch(link = "revysa://material/machine-learning")
        click("chapter-1")
        click("highlight")
        click("open-notes")
        compose.onNodeWithTag("note").performScrollTo().performTextInput("Rever taxa de aprendizado.")
        click("save-note")
        compose.onNodeWithTag("saved-note").performScrollTo().assertTextContains("Rever taxa de aprendizado.")
        click("back")
        click("open-notes")
        compose.onNodeWithTag("saved-note").performScrollTo().assertIsDisplayed()
    }

    @Test
    fun readSectionUpdatesProgressOnlyOnce() {
        launch(link = "revysa://material/redes")
        click("chapter-0")
        click("mark-read")
        compose.onNodeWithTag("mark-read").assertIsNotEnabled()
        click("nav-3")
        compose.onNodeWithTag("read-count").assertTextContains("1 seções lidas")
    }

    @Test
    fun librarySearchHasAnEmptyState() {
        launch()
        click("open-library")
        compose.onNodeWithTag("search").performTextInput("inexistente")
        compose.onNodeWithTag("empty").performScrollTo().assertIsDisplayed()
    }

    @Test
    fun unknownMaterialDeepLinkShowsRecovery() {
        launch(link = "revysa://material/inexistente")
        compose.onNodeWithText("Material indisponível").assertIsDisplayed()
        click("open-library")
        compose.onNodeWithText("Sua biblioteca").assertIsDisplayed()
    }

    @Test
    fun narrowLayoutSupportsDarkAndLightThemes() {
        launch()
        compose.onNodeWithTag("navigation-bar").assertIsDisplayed()
        compose.onNodeWithTag("start-review").assertHeightIsAtLeast(48.dp)
        capture("celular-claro")
        click("nav-4")
        click("theme-Dark")
        capture("celular-escuro")
        click("theme-Light")
        compose.onNodeWithTag("theme-Light").assertIsSelected()
    }

    @Test
    fun wideLayoutUsesRailAndCanOpenLibrary() {
        launch(width = 1000)
        compose.onNodeWithTag("navigation-rail").assertIsDisplayed()
        capture("desktop-claro")
        click("nav-4")
        click("theme-Dark")
        capture("desktop-escuro")
        click("nav-1")
        compose.onNodeWithText("Sua biblioteca").assertIsDisplayed()
    }

    @Test
    fun repeatedDeepLinkReopensTheMaterial() {
        val request = androidx.compose.runtime.mutableIntStateOf(0)
        compose.setContent {
            Box(Modifier.requiredSize(390.dp, 740.dp)) {
                App(deepLink = "revysa://material/redes", deepLinkRequestId = request.intValue)
            }
        }
        compose.onNodeWithText("Redes de Computadores").assertIsDisplayed()
        compose.runOnIdle { request.intValue++ }
        click("back")
        compose.onNodeWithText("Seu próximo passo de estudo").assertIsDisplayed()
        click("nav-4")
        compose.runOnIdle { request.intValue++ }
        compose.onNodeWithText("Redes de Computadores").assertIsDisplayed()
    }

    @Test
    fun loginFormRejectsInvalidCredentials() {
        launch()
        click("nav-4")
        click("open-login")
        click("submit-auth")
        compose.onNodeWithText("Informe um e-mail válido.").assertIsDisplayed()
        compose.onNodeWithText("Use pelo menos 6 caracteres.").assertIsDisplayed()
    }
}
