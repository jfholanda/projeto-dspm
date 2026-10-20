package br.ufrn.revysa

import androidx.window.core.layout.WindowSizeClass
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class LayoutAndValidationTest {
    @Test
    fun navigationChangesOnlyAtExpandedWidth() {
        listOf(390, 599, 600, 839).forEach { width ->
            assertFalse(useNavigationRail(WindowSizeClass.compute(width.toFloat(), 740f)), "width=$width")
        }
        listOf(840, 1000, 1200).forEach { width ->
            assertTrue(useNavigationRail(WindowSizeClass.compute(width.toFloat(), 740f)), "width=$width")
        }
    }

    @Test
    fun credentialsRejectInvalidAndWhitespaceOnlyValues() {
        assertFalse(authErrors(true, " ", "joao@example.com", "abcdef").valid)
        assertFalse(authErrors(false, "", "invalido", "abcdef").valid)
        assertFalse(authErrors(false, "", "joao@example.com", "      ").valid)
        assertTrue(authErrors(false, "", " joao@example.com ", "abcdef").valid)
        assertTrue(authErrors(true, "João", "joao@example.com", "abcdef").valid)
    }

    @Test
    fun cardValidationAcceptsMultilineAndRejectsBlank() {
        assertEquals("Informe a pergunta.", cardErrors(" \n ", "Resposta").first)
        assertEquals("Informe a resposta.", cardErrors("Pergunta", "\t").second)
        val errors = cardErrors("Primeira linha\nSegunda linha", "Uma resposta\ncom duas linhas")
        assertNull(errors.first)
        assertNull(errors.second)
    }
}
