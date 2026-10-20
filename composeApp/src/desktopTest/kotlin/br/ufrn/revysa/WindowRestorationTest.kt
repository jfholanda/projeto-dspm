package br.ufrn.revysa

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.LocalSaveableStateRegistry
import androidx.compose.runtime.saveable.SaveableStateRegistry
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import org.junit.Test

@OptIn(ExperimentalTestApi::class)
@Suppress("MagicNumber") // Dimensões de janela e identificador de evento usados como dados de teste.
class WindowRestorationTest {
    @Test
    fun restoredNavigationDoesNotReplayTheLastDeepLink() =
        runComposeUiTest {
            var registry = SaveableStateRegistry(null) { true }
            val visible = mutableStateOf(true)
            setContent {
                if (visible.value) {
                    CompositionLocalProvider(LocalSaveableStateRegistry provides registry) {
                        Box(Modifier.requiredSize(390.dp, 740.dp)) {
                            App(
                                deepLink = "revysa://material/design-software",
                                deepLinkRequestId = 3,
                                windowSizeClass = WindowSizeClass.compute(390f, 740f),
                            )
                        }
                    }
                }
            }
            onNodeWithText("Capítulos").assertIsDisplayed()
            onNodeWithTag("chapter-0").performClick()
            onNodeWithTag("open-notes").performClick()
            onNodeWithTag("note").performTextInput("Nota antes de girar")
            onNodeWithTag("save-note").performClick()
            onNodeWithTag("nav-4").performClick()
            onNodeWithText("Aparência").assertIsDisplayed()
            var saved: Map<String, List<Any?>> = emptyMap()
            runOnIdle {
                saved = registry.performSave()
                visible.value = false
            }
            waitForIdle()
            runOnIdle {
                registry = SaveableStateRegistry(saved) { true }
                visible.value = true
            }
            onNodeWithText("Aparência").assertIsDisplayed()
            onNodeWithTag("nav-1").performClick()
            onNodeWithTag("saved-note").assertIsDisplayed()
        }

    @Test
    fun allRailActionsRemainReachableInShortLandscapeWindow() =
        runComposeUiTest {
            setContent {
                Box(Modifier.requiredSize(1000.dp, 360.dp)) {
                    App(windowSizeClass = WindowSizeClass.compute(1000f, 360f))
                }
            }
            onNodeWithTag("nav-4").performScrollTo().assertIsDisplayed().performClick()
            onNodeWithText("Aparência").assertIsDisplayed()
        }
}
