package br.ufrn.revysa.data.demo

import br.ufrn.revysa.domain.model.Chapter
import br.ufrn.revysa.domain.model.StudyMaterial

internal val demoCatalog =
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
