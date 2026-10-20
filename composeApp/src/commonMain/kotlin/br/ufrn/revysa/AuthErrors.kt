package br.ufrn.revysa

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
        password = if (password.isBlank() || password.length < MINIMUM_PASSWORD_LENGTH) "Use pelo menos 6 caracteres." else null,
    )
