package br.ufrn.revysa

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview(name = "ProfilePreview", widthDp = 390, heightDp = 740)
@Composable
internal fun ProfilePreview() {
    PreviewFrame { ProfileScreen(ThemeMode.Light, {}, {}, {}) }
}

@Preview(name = "LoginPreview", widthDp = 390, heightDp = 740)
@Composable
internal fun LoginPreview() {
    PreviewFrame { AuthScreen(AuthUiState(false, "", "", "", AuthErrors(null, null, null)), {}, {}, {}, {}, {}) }
}

@Preview(name = "RegisterPreview", widthDp = 390, heightDp = 740)
@Composable
internal fun RegisterPreview() {
    PreviewFrame { AuthScreen(AuthUiState(true, "", "", "", AuthErrors(null, null, null)), {}, {}, {}, {}, {}) }
}

@Preview(name = "RegisterErrorsPreview", widthDp = 390, heightDp = 740)
@Composable
internal fun RegisterErrorsPreview() {
    PreviewFrame { AuthScreen(AuthUiState(true, "", "invalido", "", authErrors(true, "", "invalido", "")), {}, {}, {}, {}, {}) }
}
