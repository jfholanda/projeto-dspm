package br.ufrn.revysa

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.unit.dp
import org.junit.Rule
import org.junit.Test

class ScreenPreviewTest {
    @get:Rule val compose = createComposeRule()

    @Test
    fun allScreenPreviewsRenderWithFixedDataAndNoNavigation() {
        val previews: List<@Composable () -> Unit> =
            listOf(
                { HomePreview() }, { HomeEmptyPreview() }, { LibraryPreview() }, { LibraryEmptyPreview() },
                { SubjectsPreview() }, { MaterialPreview() }, { ReaderPreview() }, { NotesPreview() },
                { NotesEmptyPreview() }, { CardFormPreview() }, { CardFormErrorsPreview() }, { ReviewPreview() },
                { ReviewEmptyPreview() }, { QuizPreview() }, { ProgressPreview() }, { ProgressEmptyPreview() },
                { ProfilePreview() }, { LoginPreview() }, { RegisterPreview() }, { RegisterErrorsPreview() },
            )
        val active = mutableIntStateOf(0)
        compose.setContent { Box(Modifier.requiredSize(390.dp, 740.dp)) { previews[active.intValue]() } }
        previews.indices.forEach { index ->
            compose.runOnIdle { active.intValue = index }
            compose.onNodeWithTag("screen").assertIsDisplayed()
        }
    }
}
