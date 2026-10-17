# Planejamento — Sprint 1: interface e navegação

**Período previsto:** 14/09 a 02/10/2026 · **Entrega prevista:** 02/10/2026, 23h59.

**Situação:** implementação de fechamento preparada; aceite final depende das evidências de execução descritas abaixo. Este documento registra uma conferência interna, não uma avaliação oficial.

## Escopo implementado

| Critério da disciplina | Implementação |
|---|---|
| Telas do MVP (25%) | Home; biblioteca pessoal/explorar com busca e vazio; disciplinas; material; capítulos; leitor; destaques/notas; criação e revisão de cartões; quiz; progresso; perfil; superfícies de login/cadastro com validação demonstrativa. |
| Navegação (25%) | Navigation Compose 2.9.2, rotas serializáveis tipadas, argumentos de material/capítulo, grafo aninhado da biblioteca e pilha de retorno. Deep link `revysa://material/{id}` resolvido pelo grafo, também quando o Android recebe novo Intent. |
| Tema/adaptação (20%) | Material 3, opções sistema/claro/escuro; cores derivadas da paleta; barra inferior abaixo de 840 dp e lateral a partir dessa largura; conteúdo limitado a 760 dp e listas roláveis. |
| Acessibilidade (15%) | Rótulos visíveis, semântica de seleção, títulos como cabeçalhos, feedback com live region, campos com erro e ações de ≥48 dp. Testes de contraste da paleta e roteiro de verificação com TalkBack. |
| Testes de interface (15%) | Suíte Desktop sobre a mesma UI compartilhada do Android: navegação, argumentos, deep links, formulários, notas, leitura/progresso, revisão, quiz e duas larguras/temas. CI executa `allTests` com Xvfb e publica relatórios/capturas. |
| Formulário | Pergunta/resposta obrigatórias, inclusive rejeição de apenas espaços. Cartões vinculados ao material/capítulo entram na revisão da sessão. |

## Checklist de fechamento

- [x] Telas do MVP implementadas e ligadas ao fluxo demonstrativo.
- [x] Navigation Compose, rotas tipadas, argumentos, aninhamento e deep link implementados.
- [x] Tema claro/escuro e adaptação entre navegação inferior/lateral implementados.
- [x] Formulário de flashcard com validação e integração à revisão em memória.
- [x] Semântica, áreas mínimas e testes de contraste implementados.
- [x] Suíte de UI e execução em CI configuradas.
- [x] Confirmar portão local completo e registrar o resultado em `docs/sprint-1.md`.
- [ ] Conferir CI verde no commit entregue e na `main` após integração.
- [ ] Demonstrar o deep link no Android em aparelho/emulador.
- [ ] Executar a passagem manual com TalkBack e escala de fontes; registrar evidência.
- [ ] Gravar vídeo de cinco minutos com participação do responsável e ligar no README.

## Entrega e evidências

- [Implementação e validação](sprint-1.md).
- [Acessibilidade e verificação Android](acessibilidade-sprint-1.md).
- [Roteiro de vídeo e comandos de deep link](roteiro-sprint-1.md).

## Limites de escopo

Conteúdo próprio demonstrativo e estado em memória. Login/cadastro representam a interface e sua validação; não criam contas reais. Persistência, Supabase, sincronização e política offline são Sprint 3. O algoritmo de repetição espaçada e a separação completa com ViewModels/casos de uso pertencem às etapas seguintes. Não marcar testes de dispositivo, vídeo ou CI remoto como concluídos sem a evidência correspondente.
