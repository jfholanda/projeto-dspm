# Revysa

Aplicativo de estudo ativo: leitura estruturada, destaques, notas, flashcards, quiz e acompanhamento de progresso.

Projeto individual de **João Felipe de Holanda**, Coorte B.

## Sprint 1 — fechamento em 20/10/2026

A interface compartilhada inclui Home, biblioteca pessoal/explorar com busca, disciplinas, materiais, capítulos, leitor, destaques/notas, criação e revisão de cartões, quiz, progresso, perfil e superfícies de login/cadastro.

As telas recebem estados e callbacks; os adaptadores de rota controlam os dados em memória e as ações. Há previews com dados fixos das 13 superfícies, incluindo estados vazios e erros. Os formulários configuram teclado e ações IME de acordo com o campo.

A navegação usa Navigation Compose com rotas tipadas, argumentos, grafo aninhado e deep links. O tema Material 3 oferece sistema/claro/escuro. A classe da janela é obtida por `currentWindowAdaptiveInfo().windowSizeClass`. Em janelas menores que 840 dp há navegação inferior; em janelas maiores, navegação lateral.

Os dados são demonstrativos, com textos próprios, e ficam em memória durante a sessão. Login/cadastro validam a interface sem criar contas. Backend, autenticação real, persistência e sincronização pertencem às etapas posteriores.

**Prazo:** 20/10/2026, conforme prorrogação no SIGAA informada pelo responsável. **Aceite final pendente:** evidências Android/TalkBack, vídeo, quadro Projects e integração dos PRs de fechamento. As evidências e limitações ficam em [docs/sprint-1.md](docs/sprint-1.md).

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

Em Linux sem servidor gráfico, usar `xvfb-run -a ./gradlew allTests`. Os testes de interface foram movidos para a Sprint 2 pela disciplina; mantemos a suíte como verificação adicional. Ela exercita a interface compartilhada no alvo Desktop; isso não substitui testes manuais do Android. O CI executa essas verificações e publica APK, JAR, relatórios e capturas como artefatos da execução.

Relatório local de testes: `composeApp/build/reports/tests/desktopTest/index.html`. Capturas dos dois temas e larguras: `composeApp/build/reports/sprint-1/`.

## Capturas da interface compartilhada

[Celular claro](docs/imagens/sprint-1/celular-claro.png) · [Celular escuro](docs/imagens/sprint-1/celular-escuro.png) · [Desktop claro](docs/imagens/sprint-1/desktop-claro.png) · [Desktop escuro](docs/imagens/sprint-1/desktop-escuro.png).

Validação local: `ktlintCheck`, `detekt`, `allTests`, APK e JAR concluídos. A suíte preserva os 13 testes de UI anteriores e acrescenta IME, retorno da busca, texto multilinha e limites de largura, além de renderização dos previews e testes comuns de validação/contraste.

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

[Guia do emulador Windows e previews](docs/emulador-windows-sprint-1.md). O Pixel 7/API 36 já está preparado, com TalkBack instalado. Isso não significa que a passagem humana com leitor de tela foi concluída.

**Vídeo de cinco minutos:** gravação e publicação pendentes. O [roteiro](docs/roteiro-sprint-1.md) prepara a sequência, os comandos e as justificativas técnicas. Por decisão do responsável em 20/10, o link será enviado separadamente ao professor. O envio ainda não foi confirmado.

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
- [Emulador Windows](docs/emulador-windows-sprint-1.md)
- [Proposta do produto](docs/proposta.md)
- [PRD](PRD.md)
- [Registro de uso de IA](docs/uso-de-ia.md)
- [Planejamento da Sprint 0](docs/planejamento-sprint-0.md)
- [Backlog da Sprint 0](docs/backlog-sprint-0.md)
