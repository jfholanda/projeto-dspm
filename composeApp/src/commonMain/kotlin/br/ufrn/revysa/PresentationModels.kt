package br.ufrn.revysa

// Compatibility aliases keep the existing stateless screen models concise.
internal typealias Chapter = br.ufrn.revysa.domain.model.Chapter
internal typealias StudyMaterial = br.ufrn.revysa.domain.model.StudyMaterial
internal typealias StudyCard = br.ufrn.revysa.domain.model.StudyCard
internal typealias StudyNote = br.ufrn.revysa.domain.model.StudyNote
internal typealias StudySnapshot = br.ufrn.revysa.domain.model.StudySnapshot
internal typealias ReviewRating = br.ufrn.revysa.domain.model.ReviewRating

internal fun cardErrors(
    question: String,
    answer: String,
): Pair<String?, String?> =
    (if (question.isBlank()) "Informe a pergunta." else null) to
        (if (answer.isBlank()) "Informe a resposta." else null)
