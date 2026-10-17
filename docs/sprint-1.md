# Sprint 1 — Fluxo de estudo e interface

## Comportamento entregue

O estudante encontra materiais na Home, biblioteca ou disciplinas, abre um capítulo, destaca a ideia-chave, salva notas e registra a leitura. Pode criar cartões vinculados ao capítulo, revisá-los em uma fila finita, responder ao quiz do material e acompanhar a atividade da sessão.

A biblioteca permite buscar, explorar todo o catálogo e adicionar material aos itens pessoais. Busca sem resultados, capítulo inválido e material inexistente têm estados de recuperação. Cada material possui textos e quiz próprios, sem reutilizar conteúdo de aprendizado de máquina em outras disciplinas.

Perfil oferece os temas sistema/claro/escuro e acesso às superfícies de login/cadastro. Esses formulários validam campos e deixam explícito o caráter demonstrativo; não há autenticação real nem armazenamento de senha.

## Organização

- `App.kt` e `Routes.kt`: Navigation Compose com rotas tipadas serializadas, grafo da biblioteca, retorno e adaptação da navegação.
- `LibraryScreens.kt`, `ReaderScreens.kt`, `ReviewScreens.kt`, `ProfileScreens.kt`: telas e ações.
- `Components.kt`: lista de tela, cartões de material, ações e progresso reutilizáveis.
- `ThemeMode.kt` e `PlatformTheme.kt`: paletas, modo de aparência e barras de sistema Android.
- `StudySession.kt`: catálogo próprio e estado demonstrativo compartilhado pela sessão.

A largura máxima é de 760 dp. A navegação inferior troca para lateral quando a janela chega a 840 dp. Listas usam rolagem e chaves estáveis para os materiais e capítulos.

## Deep links

O grafo resolve `revysa://material/machine-learning`, `revysa://material/design-software` e `revysa://material/redes`. IDs inexistentes apresentam recuperação para a biblioteca. O Android encaminha a URI completa, inclusive em `onNewIntent`; a biblioteca de navegação interpreta os argumentos. Desktop aceita a URI como argumento de execução.

Comandos e roteiro: [roteiro-sprint-1.md](roteiro-sprint-1.md).

## Testes e qualidade

- `SprintOneUiTest`: telas principais, formulário vazio e com espaços, criação/revisão de cartão, notas/destaques, progresso, quiz, busca vazia, deep links válidos/inexistentes/repetidos e layouts de 390×740 e 1000×740 dp nos dois temas.
- `ThemeContrastTest`: pares de texto/fundo das duas paletas com contraste de pelo menos 4,5:1.
- `ktlintCheck` e Detekt, incluindo explicitamente os arquivos Kotlin dos source sets multiplataforma. As exceções de regras de nomenclatura/tamanho são específicas de funções `@Composable`; as outras regras continuam ativas.
- CI: `ktlintCheck detekt`, `allTests` com Xvfb, `assembleDebug desktopJar`, relatórios/capturas e artefatos de build.

### Resultado desta execução

Em 17/10/2026, no ambiente Linux com JDK 17 e SDK Android temporário:

```bash
ANDROID_HOME=/tmp/revysa-android-sdk xvfb-run -a ./gradlew ktlintCheck detekt allTests assembleDebug desktopJar
```

**Resultado: BUILD SUCCESSFUL.** Formatação e análise estática verdes; 13 testes de UI e dois de contraste no Desktop, além dos mesmos dois testes comuns de contraste em Android/JVM: 17 execuções, nenhuma falha. Os testes Android/JVM não são testes instrumentados em dispositivo.

APK: `composeApp/build/outputs/apk/debug/composeApp-debug.apk`. JAR: `composeApp/build/libs/`. Relatórios de teste: `composeApp/build/reports/tests/`. A pasta temporária do SDK não faz parte do projeto; configurar o SDK local conforme o README.

Capturas reais geradas pela suíte (não por emulador Android):

| Janela | Claro | Escuro |
|---|---|---|
| 390×740 dp | [Celular](imagens/sprint-1/celular-claro.png) | [Celular](imagens/sprint-1/celular-escuro.png) |
| 1000×740 dp | [Desktop](imagens/sprint-1/desktop-claro.png) | [Desktop](imagens/sprint-1/desktop-escuro.png) |

Rastreabilidade: [implementação #9](https://github.com/jfholanda/projeto-dspm/issues/9) e [evidências finais #10](https://github.com/jfholanda/projeto-dspm/issues/10).

## Ambiente de build

JDK 17, Gradle 9.7.1, Kotlin 2.4.10, Compose Multiplatform 1.12.0, Navigation Compose 2.9.2 e AGP 9.3.3. O AGP foi atualizado para suportar a API 37 exigida pelas dependências Android do Compose já adotado. Referência: [compatibilidade do AGP 9.3](https://developer.android.com/build/releases/agp-9-3-0-release-notes).

A estrutura atual ainda usa a compatibilidade legada do KMP com o plugin Android Application, declarada em `gradle.properties`. A migração de módulos pode ser tratada separadamente; os avisos de descontinuação não são suprimidos no build.

## Limitações e aceite manual

Os dados sobrevivem à navegação, mas não ao encerramento da sessão/processo. Revisão registra avaliações e conclui a fila; não calcula agenda real de repetição. Quiz demonstrativo contém uma pergunta por material. Login/cadastro não criam contas.

Ainda é necessário verificar TalkBack, escala de fontes e deep link em Android, gravar o vídeo com participação do responsável e confirmar a execução remota verde. Consulte [acessibilidade](acessibilidade-sprint-1.md) e [planejamento](planejamento-sprint-1.md). Testes Desktop não comprovam comportamento do sistema Android.
