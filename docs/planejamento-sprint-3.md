# Planejamento — Sprint 3: dados, rede e offline-first

**Período:** 26/10 a 20/11/2026 · **Entrega:** 20/11/2026, 23h59

**Objetivo:** substituir dados demonstrativos por dados do backend escolhido e permitir estudar sem conexão, sincronizando alterações depois.

## Decisões de produto e dados

- Backend previsto no PRD/proposta: Supabase. Consumir suas APIs com Ktor Client; manter o domínio desacoplado do serviço.
- Persistência local candidata: SQLDelight em `commonMain`, com esquema versionado e migrações. Confirmar compatibilidade das versões adotadas antes de integrar.
- Sincronizar materiais/capítulos e dados pessoais necessários ao fluxo do MVP: progresso de leitura, cartões e revisões.
- Não distribuir conteúdo protegido sem licença; usar material próprio, autorizado ou de licença compatível.

## Tarefas e pronto quando

| ID | Trabalho | Pronto quando |
|---|---|---|
| S3-01 | Configurar Supabase e autenticação. | Cadastro/login e sessão funcionam; configuração não contém segredo versionado; políticas do backend limitam dados ao usuário. |
| S3-02 | Implementar cliente Ktor e serialização. | Dados reais são requisitados; DTOs serializados são mapeados para entidades de domínio; timeout e retentativas têm limites. |
| S3-03 | Tratar falhas da rede. | Timeout, falha de conexão e resposta inválida viram estados distintos na UI; falhas não causam crash silencioso. |
| S3-04 | Criar banco local com SQLDelight. | Esquema e versão/migrações registrados; consultas tipadas; dados permanecem após fechar o app. |
| S3-05 | Implementar cache e fila offline. | App abre sem rede com dados já sincronizados; escritas ficam pendentes e são enviadas ao reconectar. |
| S3-06 | Definir e implementar resolução de conflito. | `docs/offline.md` descreve comportamento e há sincronização repetível sem duplicar revisões. Proposta: catálogo remoto é fonte autoritativa; progresso editável usa versão/`updatedAt` por registro; revisões são eventos imutáveis com ID idempotente. |
| S3-07 | Proteger sessão/token no alvo Android. | Token fica no armazenamento seguro do sistema, com renovação e encerramento de sessão tratados; nenhum segredo real está no repositório. |
| S3-08 | Integrar e demonstrar fluxo offline/online. | Ler material e registrar/revisar cartão em modo avião; reconectar e confirmar sincronização; vídeo de cinco minutos no README. |

## Critérios de aceite da disciplina

- [ ] Dados reais do backend escolhido via Ktor Client.
- [ ] kotlinx.serialization com DTO separado da entidade de domínio.
- [ ] Timeout, falha de rede e resposta inválida tratados separadamente; backoff quando aplicável.
- [ ] SQLDelight ou Room com esquema versionado, migração e consultas tipadas.
- [ ] App utilizável offline, com cache, fila de escrita e sincronização ao reconectar.
- [ ] Política de conflito em `docs/offline.md`.
- [ ] Login e token armazenado de forma segura.
- [ ] Vídeo de cinco minutos, PRs/issues rastreáveis e CI verde.

## Riscos

- **Credenciais e política RLS:** usar configuração de ambiente e políticas por usuário; nunca tratar chave pública do cliente como autorização suficiente.
- **Conflitos offline:** começar sem edição colaborativa; usar operações idempotentes e política simples documentada.
- **Tempo de integração:** estabilizar a fatia essencial (biblioteca, leitura, progresso e revisões); manter funções secundárias demonstrativas se necessário e reportar limites.
- **Alvo desktop:** segurança de sessão pode variar por plataforma; validar o alvo Android prioritário e não fingir equivalência entre alvos.
