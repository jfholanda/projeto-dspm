# Revysa

Aplicativo de estudo ativo: leitura estruturada, destaques, notas, flashcards, quiz e acompanhamento de progresso.

Projeto individual de **João Felipe de Holanda**, Coorte B.

## Sprint 1

A interface compartilhada inclui Home, biblioteca pessoal/explorar com busca, disciplinas, materiais, capítulos, leitor, destaques/notas, criação e revisão de cartões, quiz, progresso, perfil e superfícies de login/cadastro.

A navegação usa Navigation Compose com rotas tipadas, argumentos, grafo aninhado e deep links. O tema Material 3 oferece sistema/claro/escuro. Em janelas menores que 840 dp há navegação inferior; em janelas maiores, navegação lateral.

Os dados são demonstrativos, com textos próprios, e ficam em memória durante a sessão. Login/cadastro validam a interface sem criar contas. Backend, autenticação real, persistência e sincronização pertencem às etapas posteriores.

**Aceite final pendente:** passagem Android com TalkBack/deep link, gravação do vídeo e confirmação do CI no commit integrado. As evidências e limitações ficam em [docs/sprint-1.md](docs/sprint-1.md).

## Executar

Pré-requisitos: **JDK 17**, Android SDK e acesso à internet para as dependências. O wrapper fornece Gradle 9.7.1. O projeto usa Kotlin 2.4.10, Compose Multiplatform 1.12.0 e AGP 9.3.3.

Instalar os pacotes Android e apontar `ANDROID_HOME` para o SDK (ou definir `sdk.dir` em `local.properties`, não versionado):

```bash
sdkmanager 'platforms;android-37.0' 'build-tools;36.0.0'
```

Executar Desktop:

```bash
./gradlew :composeApp:run
```

Gerar APK Android e JAR Desktop:

```bash
./gradlew assembleDebug desktopJar
```

O APK está em `composeApp/build/outputs/apk/debug/composeApp-debug.apk`. Para instalar em aparelho/emulador conectado:

```bash
adb install -r composeApp/build/outputs/apk/debug/composeApp-debug.apk
```

## Testar

```bash
./gradlew ktlintCheck detekt
./gradlew allTests
```

Em Linux sem servidor gráfico, usar `xvfb-run -a ./gradlew allTests`. A suíte exercita a interface compartilhada no alvo Desktop; isso não substitui testes manuais do Android. O CI executa essas verificações e publica APK, JAR, relatórios e capturas como artefatos da execução.

Relatório local de testes: `composeApp/build/reports/tests/desktopTest/index.html`. Capturas dos dois temas e larguras: `composeApp/build/reports/sprint-1/`.

## Capturas da interface compartilhada

[Celular claro](docs/imagens/sprint-1/celular-claro.png) · [Celular escuro](docs/imagens/sprint-1/celular-escuro.png) · [Desktop claro](docs/imagens/sprint-1/desktop-claro.png) · [Desktop escuro](docs/imagens/sprint-1/desktop-escuro.png).

Validação local: `ktlintCheck`, `detekt`, `allTests`, APK e JAR concluídos. São 13 testes de UI e dois testes comuns de contraste, estes executados em Desktop e Android/JVM.

## Demonstrar deep link

Android, com o aplicativo instalado:

```bash
adb shell am start -W -a android.intent.action.VIEW \
  -d 'revysa://material/design-software' br.ufrn.revysa
```

Desktop:

```bash
./gradlew :composeApp:run --args='revysa://material/design-software'
```

Materiais: `machine-learning`, `design-software` e `redes`. Um ID inexistente mostra uma tela de recuperação.

## Vídeo e acessibilidade

**Vídeo de cinco minutos:** gravação e publicação pendentes. O [roteiro](docs/roteiro-sprint-1.md) prepara a sequência, os comandos e as justificativas técnicas. Inserir aqui a URL real depois de publicar.

A implementação inclui rótulos, papéis, estados selecionados, áreas mínimas de 48 dp e testes de contraste. A passagem com leitor de tela e escala de fontes está em [acessibilidade-sprint-1.md](docs/acessibilidade-sprint-1.md), com os itens ainda não verificados explicitamente indicados.

## Estrutura

```text
composeApp/src/commonMain/   telas, navegação, componentes, tema e estado da sessão
composeApp/src/commonTest/   testes comuns de contraste
composeApp/src/desktopTest/  testes de interface compartilhada
composeApp/src/androidMain/  Activity, manifest e barras de sistema
composeApp/src/desktopMain/  janela e entrada Desktop
config/detekt.yml            regras de análise Kotlin/Compose
.github/workflows/ci.yml     qualidade, testes e builds
```

## Documentação

- [Planejamento e checklist da Sprint 1](docs/planejamento-sprint-1.md)
- [Implementação, testes e limites da Sprint 1](docs/sprint-1.md)
- [Roteiro de demonstração](docs/roteiro-sprint-1.md)
- [Acessibilidade](docs/acessibilidade-sprint-1.md)
- [Proposta do produto](docs/proposta.md)
- [PRD](PRD.md)
- [Registro de uso de IA](docs/uso-de-ia.md)
- [Planejamento da Sprint 0](docs/planejamento-sprint-0.md)
- [Backlog da Sprint 0](docs/backlog-sprint-0.md)
