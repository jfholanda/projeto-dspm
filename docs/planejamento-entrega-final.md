# Planejamento — Entrega final

**Bloco:** 23/11 a 11/12/2026 · **Entrega:** 11/12/2026, 23h59
**Apresentação da Coorte B:** 07/12/2026, online.
**Objetivo:** estabilizar o fluxo do MVP na plataforma-alvo e entregar recursos de dispositivo, segurança, desempenho, testes, distribuição e documentação pública.

## Escopo

| ID | Trabalho | Pronto quando |
|---|---|---|
| F-01 | Integrar pelo menos dois recursos do dispositivo via `expect`/`actual`. | Recursos servem ao produto — proposta: notificações para revisões pendentes e câmera/galeria para anexar contexto a nota ou cartão — com permissões solicitadas em contexto, negação permanente tratada e alternativa de recuperação. |
| F-02 | Segurança e privacidade. | Token/dados sensíveis protegidos; nenhum segredo no código; `docs/seguranca.md` cobre OWASP Mobile Top 10, mitigação e localização no código. |
| F-03 | Medir e otimizar desempenho. | `docs/desempenho.md` registra medição antes/depois com números e explica ao menos uma otimização de recomposição. |
| F-04 | Automatizar distribuição Android. | GitHub Actions gera APK assinado a partir de `main` e publica em um canal de distribuição; segredos ficam nos secrets do CI. |
| F-05 | Configurar ambientes. | Pelo menos dois ambientes/build types ou flavors, com configuração externalizada e sem segredos no repositório. |
| F-06 | Completar a suíte. | CI roda análise estática, unitários, ViewModels, UI e ≥3 testes de integração; relatório de cobertura publicado. |
| F-07 | Estabilizar MVP. | Fluxo do critério de sucesso do PRD funciona sem crash na plataforma-alvo, incluindo falhas recuperáveis. |
| F-08 | Documentar e apresentar. | README executável em menos de 15 min, visão/arquitetura/limitações e capturas; licença definida; vídeo de dez minutos e apresentação ao vivo. |

## Critérios de aceite da disciplina

- [ ] Fluxos do MVP estáveis na plataforma-alvo.
- [ ] ≥2 recursos de dispositivo por `expect`/`actual`, com permissões completas.
- [ ] Armazenamento seguro e `docs/seguranca.md` baseado no OWASP Mobile Top 10.
- [ ] `docs/desempenho.md` com medição antes/depois de otimização.
- [ ] Pipeline gera artefato assinado de `main` e publica automaticamente.
- [ ] Análise estática, testes unitários, ViewModel, UI e ≥3 testes de integração verdes no CI; cobertura reportada.
- [ ] ≥2 ambientes configurados sem segredo versionado.
- [ ] Documentação pública, README com execução em <15 min, capturas e licença.
- [ ] Vídeo de dez minutos, apresentação e CI verde.

## Gestão de escopo

Recursos do dispositivo e publicação pertencem a esta entrega final, conforme a rúbrica. Não puxar IA, social, marketplace ou importação arbitrária de PDF para o caminho crítico: o PRD os deixa fora do MVP ou como extensões. Se o tempo apertar, priorizar fluxo completo, segurança, distribuição e evidência dos critérios obrigatórios.
