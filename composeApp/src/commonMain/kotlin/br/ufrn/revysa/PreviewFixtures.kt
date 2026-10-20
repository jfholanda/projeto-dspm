package br.ufrn.revysa

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable

internal object PreviewFixtures {
    val chapter =
        Chapter(
            "Busca binária",
            "Divida o intervalo ordenado ao meio e continue na metade que pode conter o valor.",
            "Cada passo reduz o intervalo pela metade.",
        )
    val material =
        StudyMaterial(
            "preview",
            "Algoritmos",
            "Computação",
            "Conteúdo fixo para conferir a interface.",
            listOf(chapter),
            "O que acontece a cada passo?",
            listOf("O intervalo é dividido ao meio", "O intervalo aumenta"),
        )
    val item = MaterialItem(material, 0.5f)
    val card = StudyCard(material.id, 0, "Como a busca reduz o intervalo?", chapter.keyIdea)
}

@Composable
internal fun PreviewFrame(
    dark: Boolean = false,
    content: @Composable () -> Unit,
) {
    RevysaTheme(if (dark) ThemeMode.Dark else ThemeMode.Light) { Surface { content() } }
}
