package br.ufrn.revysa

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
internal fun ProfileScreen(
    theme: ThemeMode,
    onTheme: (ThemeMode) -> Unit,
    navigate: (Any) -> Unit,
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
        item { Action("Entrar", "open-login") { navigate(Login) } }
        item { Action("Criar conta", "open-register") { navigate(Register) } }
    }
}

private const val MINIMUM_PASSWORD_LENGTH = 6

internal data class AuthErrors(val name: String?, val email: String?, val password: String?) {
    val valid: Boolean get() = listOf(name, email, password).all { it == null }
}

internal fun authErrors(
    register: Boolean,
    name: String,
    email: String,
    password: String,
): AuthErrors =
    AuthErrors(
        name = if (register && name.isBlank()) "Informe seu nome." else null,
        email = if (email.trim().matches(Regex("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$"))) null else "Informe um e-mail válido.",
        password = if (password.length < MINIMUM_PASSWORD_LENGTH) "Use pelo menos 6 caracteres." else null,
    )

@Composable
internal fun AuthScreen(
    register: Boolean,
    navigate: (Any) -> Unit,
) {
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    // A senha não participa do estado salvo da Activity.
    var password by androidx.compose.runtime.remember { mutableStateOf("") }
    var attempted by rememberSaveable { mutableStateOf(false) }
    val errors = authErrors(register, name, email, password)
    Screen(if (register) "Criar conta" else "Entrar") {
        item { Text("Experimente o fluxo com dados fictícios. Nesta demonstração, nenhuma conta será criada.") }
        if (register) {
            item { AuthField(name, { name = it }, "Nome", if (attempted) errors.name else null, "name") }
        }
        item { AuthField(email, { email = it }, "E-mail", if (attempted) errors.email else null, "email") }
        item { AuthField(password, { password = it }, "Senha", if (attempted) errors.password else null, "password", secret = true) }
        item {
            Action("Continuar demonstração", "submit-auth") {
                attempted = true
                if (errors.valid) {
                    password = ""
                    navigate(Home)
                }
            }
        }
        item {
            Action(if (register) "Já tenho conta" else "Criar conta", "switch-auth") {
                navigate(if (register) Login else Register)
            }
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
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(label) },
        isError = error != null,
        supportingText = { error?.let { Text(it) } },
        visualTransformation = if (secret) PasswordVisualTransformation() else androidx.compose.ui.text.input.VisualTransformation.None,
        modifier = Modifier.fillMaxWidth().testTag(tag),
        singleLine = true,
    )
}
