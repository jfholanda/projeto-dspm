# Evidências Android — Sprint 1, 20/10/2026

## Ambiente e método

Pixel_7 no emulador do Android Studio no Windows, imagem Google Play API 36, Android 16. APK debug compilado com o portão de qualidade do projeto; instalação e comandos pelo `adb.exe` do Windows. TalkBack (`com.google.android.marvin.talkback`) está instalado.

Esta passagem automatizada usou ADB, UIAutomator e capturas reais do dispositivo. Não substitui a avaliação humana da fala e da ordem de foco com TalkBack. Os dados digitados são fictícios.

O emulador apresentou diálogos de lentidão do **System UI**, inclusive após reinstalação. A passagem foi retomada após fechar o diálogo e reiniciar apenas esse componente pelo próprio alerta. Essa instabilidade do ambiente deve ser considerada ao repetir a demonstração; não se registra aprovação humana por esta automação.

Código validado: `b4688aed0d78f3557dc1269490861295b463434f`, integrado pelo [PR #19](https://github.com/jfholanda/projeto-dspm/pull/19). [CI do código aprovado](https://github.com/jfholanda/projeto-dspm/actions/runs/39466151662). APK SHA-256: `59af28bf4a7c2ebdccfabaee07db7922f0e008fcafe1b7b4b864cf44ea5d2aca`.

[Registro estruturado das verificações](imagens/sprint-1/android/resultados.json).

## Resultado

| Verificação | Evidência |
| --- | --- |
| Deep link com app fechado para Design de Software | [Captura](imagens/sprint-1/android/01-deep-link-cold.png) |
| Voltar do sistema retorna ao início | [Captura](imagens/sprint-1/android/02-system-back.png) |
| Novo deep link com app aberto para Redes | [Captura](imagens/sprint-1/android/03-deep-link-warm.png) |
| ID inexistente apresenta recuperação | [Captura](imagens/sprint-1/android/04-invalid-id.png) |
| Nota salva e recuperada ao voltar | [Captura](imagens/sprint-1/android/05-note-saved.png) |
| Leitura atualiza progresso | [Captura](imagens/sprint-1/android/06-progress-updated.png) |
| Tema escuro | [Captura](imagens/sprint-1/android/07-dark-theme.png) |
| Rotação preserva a tela atual | [Captura](imagens/sprint-1/android/08-landscape.png) |
| Perfil alcançável pela lateral com rolagem | [Captura](imagens/sprint-1/android/09-landscape-rail.png) |
| Retorno ao retrato e tema claro | [Captura](imagens/sprint-1/android/10-portrait-light.png) |
| Progresso preservado após rotação | [Captura](imagens/sprint-1/android/11-progress-after-rotation.png) |
| Fonte do sistema ampliada para 1,3 | [Captura](imagens/sprint-1/android/12-large-font.png) |
| Cartão vazio apresenta erros junto aos campos | [Captura](imagens/sprint-1/android/13-card-errors.png) |

A passagem encontrou e corrigiu a repetição do deep link na recriação da Activity, a perda do estado demonstrativo na rotação e a navegação lateral cortada em janelas baixas. Os testes de regressão verificam restauração da rota, nota salva e acesso ao Perfil em 1000×360 dp. O snapshot de interface usa estado salvo da Activity, sem banco ou backend. A sessão não oferece persistência durável ao usuário.

A digitação rápida por `adb shell input text` foi truncada durante a automação. A nota efetivamente salva aparece como `Not`; sua recuperação foi conferida ao sair e retornar. A digitação pelo teclado do usuário ainda precisa ser confirmada na passagem humana Android. Não se atribui a causa desse comportamento ao aplicativo ou ao emulador sem essa confirmação.

## Estado de entrega

A main passou no [CI de integração do código](https://github.com/jfholanda/projeto-dspm/actions/runs/39466669530), no commit `94812fc9bc29ccbba48ce2d029aa71f351c415ab`. O hash final após integrar a documentação e seu resultado de CI serão registrados no acompanhamento [#10](https://github.com/jfholanda/projeto-dspm/issues/10).

## Pendências humanas

- Confirmar digitação pelo teclado Android e o fluxo completo de criação/revisão de cartão e quiz; essas interações estão cobertas nos testes Desktop, mas a automação Android de entrada ainda não concluiu esse percurso.
- Percorrer o fluxo completo com TalkBack, ouvir anúncios, conferir ordem de foco, erros, seleção e alcance de ações; registrar resultado e eventuais correções em [acessibilidade](acessibilidade-sprint-1.md).
- Abrir os previews no Android Studio, incluindo vazios e erros; o teste automatizado já renderiza 20 variantes.
- Gravar a apresentação de aproximadamente cinco minutos. O responsável optou por enviar o link separadamente ao professor; esse envio ainda não foi confirmado.
- Vincular os cartões #11–#18 a um GitHub Project. A listagem fornecida (`https://github.com/jfholanda/projeto-dspm/projects`) não exibia quadros públicos vinculados nesta consulta sem autenticação. O conector disponível permite issues/PRs, mas não editar Projects.

Projeto individual de João Felipe de Holanda, com auxílio de IA registrado em [uso de IA](uso-de-ia.md). Não houve revisão atribuída a outro integrante. A submissão no SIGAA é feita pelo responsável, com o repositório e o hash efetivamente entregue.
