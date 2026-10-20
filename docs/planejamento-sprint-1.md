# Planejamento — Sprint 1: interface e navegação

**Prazo de fechamento:** 20/10/2026. O responsável informou prorrogação formalizada no SIGAA; horário adotado para organização: 23h59, America/Sao_Paulo. O repositório público da disciplina ainda registra 16/10 como prazo anterior.

**Objetivo:** fechar telas e navegação do Revysa, com dados em memória, segundo as tarefas atuais da disciplina. Projeto individual de João Felipe de Holanda.

## Rubrica atual e rastreabilidade

| Critério | Peso | Fechamento |
|---|---:|---|
| Telas do MVP | 30% | Componentes visuais recebem estados e callbacks; adaptadores de rota controlam estado e alterações. Previews das 13 superfícies, incluindo vazios e erros. T2 [#11](https://github.com/jfholanda/projeto-dspm/issues/11), formulários T3 [#12](https://github.com/jfholanda/projeto-dspm/issues/12). |
| Navegação | 30% | Grafo tipado, argumentos, retorno e deep link. Conferências Android: T4 [#13](https://github.com/jfholanda/projeto-dspm/issues/13) e T5 [#14](https://github.com/jfholanda/projeto-dspm/issues/14). |
| Tema e adaptação | 20% | Material 3 claro/escuro; classe de janela obtida por `currentWindowAdaptiveInfo().windowSizeClass`; navegação inferior abaixo de 840 dp e lateral a partir de 840 dp. T6 [#15](https://github.com/jfholanda/projeto-dspm/issues/15) e T7 [#16](https://github.com/jfholanda/projeto-dspm/issues/16). |
| Acessibilidade | 20% | Rótulos, seleção, cabeçalhos, erros, contraste e alvos mínimos. A passagem humana com TalkBack continua necessária: T8 [#17](https://github.com/jfholanda/projeto-dspm/issues/17). |

**T9:** testes de interface passaram para a Sprint 2. A suíte existente foi preservada e ampliada como verificação do fechamento; não é apresentada como requisito atual da Sprint 1.

**T10:** vídeo de cinco minutos com fala do responsável, conforme [#18](https://github.com/jfholanda/projeto-dspm/issues/18) e [roteiro](roteiro-sprint-1.md).

## Checklist de aceite

- [x] Telas do MVP e componentes reutilizáveis.
- [x] Estado elevado aos adaptadores; telas recebem dados e callbacks, sem acessar sessão ou navegar.
- [x] Previews com dados fixos, casos vazios e erros; renderização automatizada em Desktop.
- [ ] Conferir os previews visualmente no Android Studio.
- [x] Formulários validados, teclados apropriados e IME Próximo/Concluir.
- [x] Navegação tipada, argumentos e deep link implementados.
- [x] Material 3 claro/escuro e decisão de layout por classe de janela.
- [x] Testes de limites de largura, validação, IME, múltiplas linhas e retorno.
- [ ] Integrar PR das correções novas com CI verde e registrar hash/link final.
- [ ] Registrar as conferências Android, incluindo Voltar do sistema, deep link e orientação.
- [ ] Percorrer as telas com TalkBack e fonte ampliada; registrar resultados e ajustes.
- [ ] Gravar/publicar vídeo acessível e inserir URL no README.
- [ ] Adicionar os oito cartões ao GitHub Projects, com responsável e vínculos aos PRs.
- [ ] Integrar PR das evidências e conferir o CI da main no hash entregue.

## Processo e limites

O commit anterior `c27b8f2` entrou diretamente na main. As correções novas usam PRs; o histórico anterior não é reescrito. Os cartões #11–#18 estão atribuídos a `jfholanda`. O conector permite gerenciar issues/PRs, mas não oferece edição de GitHub Projects; a inclusão no quadro ainda precisa ser confirmada. Não há revisão por outro integrante, pois o projeto é individual.

Dados, catálogo e progresso continuam demonstrativos/em memória. ViewModels, rede, autenticação real e persistência não entram neste fechamento. O cronograma atualizado concentra a continuidade na Sprint 2, que é a entrega final; as referências antigas a Sprint 3 não determinam o escopo desta sprint.

## Evidências

- [Implementação e testes](sprint-1.md).
- [Acessibilidade e passagem manual](acessibilidade-sprint-1.md).
- [Roteiro do vídeo](roteiro-sprint-1.md).
- [Preparação e comandos no Windows](emulador-windows-sprint-1.md).
