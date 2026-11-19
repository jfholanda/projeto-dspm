package br.ufrn.revysa

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import br.ufrn.revysa.domain.model.HomePrimaryAction
import br.ufrn.revysa.domain.model.StudyHomeSummary
import br.ufrn.revysa.domain.model.StudyReadingSuggestion

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
    val homeSummary =
        StudyHomeSummary(
            2,
            StudyReadingSuggestion(material.id, 0, material.title, chapter.title, 0.5f),
            HomePrimaryAction.REVIEW,
        )
}

@Composable
internal fun PreviewFrame(
    dark: Boolean = false,
    content: @Composable () -> Unit,
) {
    RevysaTheme(if (dark) ThemeMode.Dark else ThemeMode.Light) { Surface { content() } }
}
