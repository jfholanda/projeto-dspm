# Revysa

Plataforma mobile de estudo ativo que conecta leitura, compreensão, flashcards, quizzes e revisão espaçada.

Projeto individual de **João Felipe de Holanda**.

## Sprint 0

Esta sprint prepara a estrutura técnica e documental do projeto. A primeira tela em Compose é opcional pela rúbrica e não será implementada nesta etapa.

## Stack

- Kotlin Multiplatform;
- Compose Multiplatform, planejado para as próximas sprints;
- Android como alvo prioritário;
- Desktop como alvo secundário;
- Supabase como backend futuro;
- Gradle, `ktlint`, `detekt` e GitHub Actions.

## Estrutura

```text
composeApp/
├── src/commonMain/
├── src/commonTest/
├── src/androidMain/
└── src/desktopMain/
docs/
gradle/libs.versions.toml
.github/workflows/ci.yml
```

## Execução e validação

Pré-requisitos: JDK 17+, Android SDK para o build Android e acesso à internet para baixar dependências Gradle.

```bash
./gradlew ktlintCheck
./gradlew detekt
./gradlew assembleDebug
./gradlew jvmJar
```

O APK de debug é gerado em `composeApp/build/outputs/apk/debug/`.

## Documentação

- [Planejamento da Sprint 0](docs/planejamento-sprint-0.md)
- [Proposta do produto](docs/proposta.md)
- [Registro de uso de IA](docs/uso-de-ia.md)
- [Backlog da Sprint 0](docs/backlog-sprint-0.md)
- [PRD](PRD.md)

## Checklist da Sprint 0

- [ ] Build Android concluído.
- [ ] Build Desktop concluído.
- [ ] `ktlintCheck` verde.
- [ ] `detekt` verde.
- [ ] CI verde na branch principal.
- [ ] Backlog criado no GitHub Projects.
- [ ] Vídeo preparado.
