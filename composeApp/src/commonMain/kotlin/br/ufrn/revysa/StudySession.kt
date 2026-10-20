package br.ufrn.revysa

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

internal data class Chapter(val title: String, val text: String, val keyIdea: String)

internal data class StudyMaterial(
    val id: String,
    val title: String,
    val subject: String,
    val description: String,
    val chapters: List<Chapter>,
    val question: String,
    val answers: List<String>,
)

internal val catalog =
    listOf(
        StudyMaterial(
            "machine-learning",
            "Aprendizado de Máquina",
            "Inteligência Artificial",
            "Modelos, treinamento e otimização, em textos introdutórios próprios.",
            listOf(
                Chapter(
                    "Introdução",
                    "Um modelo aprende padrões em exemplos de treinamento. A avaliação usa outros exemplos para " +
                        "verificar se o aprendizado se aplica a dados novos.",
                    "Treinar e avaliar são etapas distintas.",
                ),
                Chapter(
                    "Gradiente descendente",
                    "O gradiente indica a direção de maior crescimento do erro. O algoritmo atualiza os parâmetros " +
                        "na direção oposta. A taxa de aprendizado controla o tamanho de cada passo: valores altos podem " +
                        "ultrapassar a solução, e valores baixos exigem mais passos.",
                    "A taxa de aprendizado controla o tamanho da atualização dos parâmetros.",
                ),
            ),
            "O que a taxa de aprendizado controla?",
            listOf("O tamanho da atualização dos parâmetros", "A quantidade de exemplos", "O número de camadas"),
        ),
        StudyMaterial(
            "design-software",
            "Design de Software",
            "Engenharia de Software",
            "Organização de código e princípios de projeto, com exemplos próprios.",
            listOf(
                Chapter(
                    "Responsabilidades",
                    "Uma responsabilidade representa um motivo para mudar. Separar responsabilidades permite alterar " +
                        "uma regra sem modificar partes que tratam de outras decisões.",
                    "Uma unidade deve reunir comportamentos que mudam pelo mesmo motivo.",
                ),
                Chapter(
                    "Inversão de dependência",
                    "Regras de alto nível podem depender de contratos em vez de implementações concretas. Um " +
                        "adaptador implementa o contrato e pode ser substituído sem alterar a regra.",
                    "Depender de abstrações permite substituir detalhes de infraestrutura.",
                ),
            ),
            "O que facilita substituir uma implementação?",
            listOf("Depender de um contrato", "Usar estado global", "Acoplar a regra à infraestrutura"),
        ),
        StudyMaterial(
            "redes",
            "Redes de Computadores",
            "Sistemas Distribuídos",
            "Comunicação entre sistemas e transporte, em conteúdo demonstrativo próprio.",
            listOf(
                Chapter(
                    "Comunicação em camadas",
                    "As camadas dividem responsabilidades da comunicação. Cada camada oferece serviços à camada " +
                        "superior e utiliza serviços da inferior.",
                    "Camadas organizam responsabilidades e contratos de comunicação.",
                ),
                Chapter(
                    "Camada de transporte",
                    "O transporte conecta processos por meio de portas. TCP oferece um fluxo ordenado e confiável. " +
                        "UDP envia datagramas sem garantir entrega ou ordem.",
                    "TCP oferece entrega confiável e ordenada de um fluxo de bytes.",
                ),
            ),
            "Qual característica é oferecida pelo TCP?",
            listOf("Fluxo confiável e ordenado", "Entrega sem ordem garantida", "Ausência de portas"),
        ),
    )

internal data class StudyCard(val materialId: String, val chapter: Int, val question: String, val answer: String)

internal data class StudyNote(val materialId: String, val chapter: Int, val text: String)

/** Estado demonstrativo de uma sessão. Persistência e autenticação ficam para a Sprint 2, conforme o cronograma atualizado. */
internal class StudySession {
    val savedMaterials = mutableStateListOf("machine-learning", "design-software")
    val cards =
        mutableStateListOf(
            StudyCard("machine-learning", 1, "Qual é o papel da taxa de aprendizado?", catalog.first().chapters[1].keyIdea),
        )
    val notes = mutableStateListOf<StudyNote>()
    val highlights = mutableStateListOf<Pair<String, Int>>()
    val readChapters = mutableStateListOf<Pair<String, Int>>()
    val quizResults = mutableStateMapOf<String, Boolean>()
    var reviewed by mutableStateOf(0)

    fun progress(material: StudyMaterial): Float = readChapters.count { it.first == material.id }.toFloat() / material.chapters.size

    fun markRead(
        id: String,
        chapter: Int,
    ) {
        if (id to chapter !in readChapters) readChapters.add(id to chapter)
    }

    fun toggleHighlight(
        id: String,
        chapter: Int,
    ) {
        val key = id to chapter
        if (!highlights.remove(key)) highlights.add(key)
    }
}

internal fun cardErrors(
    question: String,
    answer: String,
): Pair<String?, String?> =
    (if (question.isBlank()) "Informe a pergunta." else null) to
        (if (answer.isBlank()) "Informe a resposta." else null)
