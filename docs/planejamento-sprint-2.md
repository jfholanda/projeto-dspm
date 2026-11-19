# Planejamento — Sprint 2: estado e arquitetura

**Período:** 05/10 a 23/10/2026 · **Entrega:** 23/10/2026, 23h59

**Objetivo:** retirar estado e regras de negócio das funções de tela, preparando o Revysa para persistência e rede na Sprint 3.

## Retomada em 19/11/2026

As entregas de domínio, casos de uso, ViewModels, Koin, testes, documentação de arquitetura e Home foram integradas à `main` pelos PRs #37, #38, #40, #41, #42 e #44. O CI do PR #44 passou em `ktlintCheck`, Detekt, `allTests`, `assembleDebug` e `desktopJar`.

O workspace não possui plataformas Android no SDK, então os testes/builds não puderam ser executados localmente. O CI do GitHub validou os alvos Android e Desktop; a passagem manual do fluxo Android e o vídeo de cinco minutos continuam pendentes, mantendo aberta a Issue #27.

## Rastreamento no GitHub

- Issues: [S2-01 #21](https://github.com/jfholanda/projeto-dspm/issues/21), [S2-02 #22](https://github.com/jfholanda/projeto-dspm/issues/22), [S2-03 #23](https://github.com/jfholanda/projeto-dspm/issues/23), [S2-04 #24](https://github.com/jfholanda/projeto-dspm/issues/24), [S2-05 #25](https://github.com/jfholanda/projeto-dspm/issues/25), [S2-06 #26](https://github.com/jfholanda/projeto-dspm/issues/26), [S2-07 #27](https://github.com/jfholanda/projeto-dspm/issues/27), [S2-08 #35](https://github.com/jfholanda/projeto-dspm/issues/35).
- PRs integrados: [#37](https://github.com/jfholanda/projeto-dspm/pull/37), [#38](https://github.com/jfholanda/projeto-dspm/pull/38), [#40](https://github.com/jfholanda/projeto-dspm/pull/40), [#41](https://github.com/jfholanda/projeto-dspm/pull/41), [#42](https://github.com/jfholanda/projeto-dspm/pull/42) e [#44](https://github.com/jfholanda/projeto-dspm/pull/44).
- A Issue #27 permanece aberta para a validação manual no Android e a inclusão do vídeo de cinco minutos no README.

## Escopo

- Separar `data`, `domain` e `presentation` em `commonMain`.
- Modelar entidades e contratos de repositório no domínio, sem dependência de Compose ou infraestrutura.
- Introduzir ViewModels multiplataforma com `StateFlow`, injetados por Koin.
- Representar estados de tela como `Loading`, `Error`, `Empty` e `Success` usando tipos selados.
- Mover regras do fluxo de leitura, flashcards, quiz e revisão para casos de uso; definir e testar o algoritmo inicial de repetição espaçada descrito no PRD.
- Orientar a Home para a próxima ação: revisões vencidas, leitura pendente na ordem da biblioteca pessoal ou exploração do catálogo.
- Documentar a arquitetura e justificar escolhas.

## Tarefas e pronto quando

| ID | Trabalho | Pronto quando |
|---|---|---|
| S2-01 | Definir entidades e interfaces de repositório no domínio. | Domínio compila sem imports de Compose, Ktor, SQLDelight/Room ou Supabase. |
| S2-02 | Criar casos de uso para biblioteca, leitura/progresso, criação/revisão de cartões e quiz. | Regras principais podem ser chamadas sem UI e sem backend real. |
| S2-03 | Criar ViewModels com `StateFlow` e estado selado por tela. | Telas reagem a carregando, erro, vazio e sucesso; sem regra de negócio em `@Composable`. |
| S2-04 | Configurar módulos Koin em `commonMain`. | ViewModels e repositórios demonstrativos são resolvidos por injeção; não há estado global desnecessário. |
| S2-05 | Testar ViewModels com Turbine e dependências dublês. | Pelo menos oito testes relevantes passam no CI. |
| S2-06 | Produzir `docs/arquitetura.md`. | Diagrama mostra dependências entre camadas; documento justifica StateFlow/ViewModel e Koin e registra alternativas consideradas. |
| S2-07 | Preservar apresentação do produto. | Fluxo principal continua executável em Android e Desktop; vídeo de cinco minutos ligado no README. |
| S2-08 | Orientar a Home para a próxima ação de estudo. | Revisões vencidas são priorizadas; sem revisões, sugere o primeiro capítulo não lido na ordem da biblioteca; sem pendências, oferece o catálogo. A regra usa o progresso e cartões existentes e não exige novo estado persistido. |

## Critérios de aceite da disciplina

- [x] ViewModel multiplataforma expondo `StateFlow` e com escopo adequado.
- [x] `data/domain/presentation` separados; domínio independente de UI/infraestrutura; repositórios definidos por interface no domínio.
- [x] `Loading`, `Error`, `Empty` e `Success` modelados explicitamente por tipo selado e refletidos na UI.
- [x] Koin configurado em `commonMain`.
- [x] ≥8 testes de ViewModel com Turbine e dublês injetados, verdes no CI.
- [x] `docs/arquitetura.md` com diagrama e justificativas.
- [x] Home apresenta a próxima ação com base nas revisões vencidas e na leitura pendente; testes cobrem vencimentos, ordenação, conclusão e fallback.
- [ ] Vídeo de cinco minutos, PRs/issues rastreáveis e CI verde.

## Rastreamento S2-08

- Issue: [#35 — Orientar a Home para a próxima ação de estudo](https://github.com/jfholanda/projeto-dspm/issues/35).
- PR: [#44 — Orienta a Home para a próxima ação de estudo](https://github.com/jfholanda/projeto-dspm/pull/44), substitui o PR fechado #36.
- Dependências: S2-01 a S2-04 (Issues #21–#24).

## Fora do escopo

A Sprint 2 não implementa integração real, sincronização ou política offline; isso é Sprint 3. Evitar reescrever a UI inteira: migrar uma fatia vertical por vez e preservar o fluxo útil.

## Riscos

- **Escopo de arquitetura excessivo:** começar pelas telas já demonstradas e extrair somente contratos e casos de uso necessários.
- **Duplicação entre dados temporários e backend futuro:** definir contratos de domínio antes de implementar o adaptador remoto na Sprint 3.
- **Testes frágeis:** testar estado e comportamento do ViewModel com fakes, sem acoplar testes aos detalhes visuais.
