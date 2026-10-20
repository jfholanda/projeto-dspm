package br.ufrn.revysa

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
internal fun ProfileScreen(
    theme: ThemeMode,
    onTheme: (ThemeMode) -> Unit,
    onLogin: () -> Unit,
    onRegister: () -> Unit,
) {
    Screen("Perfil") {
        item { Text("Sessão de demonstração. Suas atividades ficam disponíveis enquanto o app estiver aberto.") }
        item { Text("Aparência") }
        items(ThemeMode.entries.size) { index ->
            val mode = ThemeMode.entries[index]
            FilterChip(
                selected = theme == mode,
                onClick = { onTheme(mode) },
                label = { Text(mode.label) },
                modifier = Modifier.heightIn(min = 48.dp).testTag("theme-${mode.name}"),
            )
        }
        item { Action("Entrar", "open-login") { onLogin() } }
        item { Action("Criar conta", "open-register") { onRegister() } }
    }
}

@Composable
internal fun AuthScreen(
    state: AuthUiState,
    onName: (String) -> Unit,
    onEmail: (String) -> Unit,
    onPassword: (String) -> Unit,
    onSubmit: () -> Unit,
    onSwitch: () -> Unit,
) {
    Screen(if (state.register) "Criar conta" else "Entrar") {
        item { Text("Experimente o fluxo com dados fictícios. Nesta demonstração, nenhuma conta será criada.") }
        if (state.register) {
            item { AuthField(state.name, onName, "Nome", state.errors.name, "name") }
        }
        item {
            AuthField(
                state.email,
                onEmail,
                "E-mail",
                state.errors.email,
                "email",
                keyboardType = KeyboardType.Email,
            )
        }
        item { AuthField(state.password, onPassword, "Senha", state.errors.password, "password", secret = true, onSubmit = onSubmit) }
        item {
            Action("Continuar demonstração", "submit-auth", onClick = onSubmit)
        }
        item {
            Action(if (state.register) "Já tenho conta" else "Criar conta", "switch-auth", onClick = onSwitch)
        }
    }
}

@Composable
private fun AuthField(
    value: String,
    onChange: (String) -> Unit,
    label: String,
    error: String?,
    tag: String,
    secret: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    onSubmit: () -> Unit = {},
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(label) },
        isError = error != null,
        supportingText = { error?.let { Text(it) } },
        visualTransformation = if (secret) PasswordVisualTransformation() else VisualTransformation.None,
        modifier = Modifier.fillMaxWidth().testTag(tag),
        singleLine = true,
        keyboardOptions =
            KeyboardOptions(
                keyboardType = if (secret) KeyboardType.Password else keyboardType,
                autoCorrectEnabled = !secret && keyboardType != KeyboardType.Email,
                imeAction = if (secret) ImeAction.Done else ImeAction.Next,
            ),
        keyboardActions =
            KeyboardActions(
                onNext = { defaultKeyboardAction(ImeAction.Next) },
                onDone = {
                    onSubmit()
                    defaultKeyboardAction(ImeAction.Done)
                },
            ),
    )
}
