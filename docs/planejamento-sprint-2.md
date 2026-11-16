# Planejamento — Sprint 2: estado e arquitetura

**Período:** 05/10 a 23/10/2026 · **Entrega:** 23/10/2026, 23h59

**Objetivo:** retirar estado e regras de negócio das funções de tela, preparando o Revysa para persistência e rede na Sprint 3.

## Retomada em 16/11/2026

As camadas, contratos, casos de uso, ViewModel com `StateFlow`, estados selados, Koin e documentação de arquitetura foram implementados em `commonMain`. A implementação do intervalo de revisão segue o PRD e mantém o horário injetável. Nove testes novos de ViewModel/domínio com Turbine passam localmente no alvo Desktop, junto com a suíte existente; `ktlintCheck`, Detekt e `desktopJar` também passaram.

O workspace não possui plataformas Android no SDK, então `allTests` e `assembleDebug` não puderam ser executados localmente. A confirmação do CI e o vídeo de cinco minutos continuam pendentes; por isso os critérios que exigem CI e entrega formal permanecem abertos.

## Rastreamento no GitHub

- [S2-01 — Domínio e contratos](https://github.com/jfholanda/projeto-dspm/issues/21)
- [S2-02 — Casos de uso e repositório demonstrativo](https://github.com/jfholanda/projeto-dspm/issues/22)
- [S2-03 — ViewModels e estados observáveis](https://github.com/jfholanda/projeto-dspm/issues/23)
- [S2-04 — Injeção com Koin](https://github.com/jfholanda/projeto-dspm/issues/24)
- [S2-05 — Testes de ViewModel e revisão](https://github.com/jfholanda/projeto-dspm/issues/25)
- [S2-06 — Documentação da arquitetura](https://github.com/jfholanda/projeto-dspm/issues/26)
- [S2-07 — Validação e evidências](https://github.com/jfholanda/projeto-dspm/issues/27) — CI Android e vídeo continuam pendentes.

## Escopo

- Separar `data`, `domain` e `presentation` em `commonMain`.
- Modelar entidades e contratos de repositório no domínio, sem dependência de Compose ou infraestrutura.
- Introduzir ViewModels multiplataforma com `StateFlow`, injetados por Koin.
- Representar estados de tela como `Loading`, `Error`, `Empty` e `Success` usando tipos selados.
- Mover regras do fluxo de leitura, flashcards, quiz e revisão para casos de uso; definir e testar o algoritmo inicial de repetição espaçada descrito no PRD.
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

## Critérios de aceite da disciplina

- [x] ViewModel multiplataforma expondo `StateFlow` e com escopo adequado.
- [x] `data/domain/presentation` separados; domínio independente de UI/infraestrutura; repositórios definidos por interface no domínio.
- [x] `Loading`, `Error`, `Empty` e `Success` modelados explicitamente por tipo selado e refletidos na UI.
- [x] Koin configurado em `commonMain`.
- [ ] ≥8 testes de ViewModel com Turbine e dublês injetados, verdes no CI.
- [x] `docs/arquitetura.md` com diagrama e justificativas.
- [ ] Vídeo de cinco minutos, PRs/issues rastreáveis e CI verde.

## Fora do escopo

A Sprint 2 não implementa integração real, sincronização ou política offline; isso é Sprint 3. Evitar reescrever a UI inteira: migrar uma fatia vertical por vez e preservar o fluxo útil.

## Riscos

- **Escopo de arquitetura excessivo:** começar pelas telas já demonstradas e extrair somente contratos e casos de uso necessários.
- **Duplicação entre dados temporários e backend futuro:** definir contratos de domínio antes de implementar o adaptador remoto na Sprint 3.
- **Testes frágeis:** testar estado e comportamento do ViewModel com fakes, sem acoplar testes aos detalhes visuais.
