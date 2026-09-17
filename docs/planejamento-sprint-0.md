# Planejamento do Projeto Revysa — Sprint 0

## Objetivo

Preparar a base técnica e documental do Revysa, uma plataforma mobile de estudo ativo que conecta leitura, compreensão, flashcards, quizzes e revisão espaçada.

A Sprint 0 não implementará telas ou funcionalidades de negócio. A primeira tela em Compose é opcional na rúbrica e não será entregue nesta sprint.

## Escopo

### Incluído

- estrutura Kotlin Multiplatform com Android e Desktop;
- configuração de Gradle, `ktlint` e `detekt`;
- pipeline de CI;
- proposta do produto e definição do MVP;
- backlog inicial;
- README e registro de uso de IA;
- preparação do vídeo da Sprint 0.

### Fora do escopo

- qualquer tela em Compose;
- navegação;
- autenticação e integração com Supabase;
- banco de dados e persistência local;
- offline-first e sincronização;
- IA, PDF e testes de interface.

## Decisões

| Item | Decisão |
|---|---|
| Nome | Revysa |
| Equipe | João Felipe de Holanda, único integrante |
| Alvo prioritário | Android |
| Alvo secundário | Desktop |
| Interface futura | Compose Multiplatform |
| Backend futuro | Supabase |
| Integração externa | Não prevista |
| Coorte | Coorte B como padrão, caso ainda não definida |

Android atende ao público estudantil e permite desenvolvimento e distribuição com baixo custo. Desktop acelera o ciclo de desenvolvimento. Supabase oferece autenticação, PostgreSQL e armazenamento sem exigir a implementação de uma infraestrutura própria nesta fase. Compose Multiplatform mantém a possibilidade de compartilhar a interface entre plataformas.

João Felipe de Holanda é o responsável pela concepção do produto, pelas decisões de escopo e tecnologia, pela implementação, pela documentação e pela apresentação. Ferramentas de IA foram utilizadas somente como apoio auxiliar, conforme registrado em [`docs/uso-de-ia.md`](uso-de-ia.md).

## Entregáveis

- projeto KMP compilável para Android e Desktop;
- CI com `ktlintCheck`, `detekt`, `assembleDebug` e `jvmJar`;
- `docs/proposta.md`;
- backlog com pelo menos cinco histórias, sendo três estimadas;
- `docs/backlog-sprint-0.md` como fonte para criação do GitHub Project;
- `README.md`;
- `docs/uso-de-ia.md`;
- vídeo de aproximadamente cinco minutos.

## Critérios de aceite

- [ ] O projeto compila para Android.
- [ ] O projeto compila para Desktop.
- [ ] `ktlintCheck` passa.
- [ ] `detekt` passa sem problemas bloqueantes.
- [ ] O GitHub Actions está configurado para pushes e pull requests na `main`.
- [ ] A proposta define problema, público, MVP e fora de escopo.
- [ ] Plataforma, backend, coorte e intenção multiplataforma estão justificados.
- [ ] O backlog contém pelo menos cinco histórias priorizadas.
- [ ] Pelo menos três histórias possuem estimativa.
- [ ] O README documenta execução e validação.
- [ ] O uso de IA está registrado.
- [ ] O vídeo está preparado.

## Riscos

- Escopo excessivo: controlado pela definição explícita do que fica fora do MVP.
- Falta de tempo: priorizar build, CI e documentação exigida.
- Dependência de infraestrutura: integração real com Supabase fica para sprint posterior.
- Direitos autorais: materiais do catálogo deverão ter licença compatível.
- Contribuição individual: usar issues e pull requests pequenos para manter rastreabilidade.

## Checklist final

- [ ] Nome Revysa aplicado na documentação e configuração.
- [ ] Nenhuma tela implementada na Sprint 0.
- [ ] Android e Desktop declarados e configurados.
- [ ] CI configurado.
- [ ] Documentação revisada.
- [ ] Backlog criado no GitHub Projects.
- [ ] Vídeo gravado com a participação do integrante.
