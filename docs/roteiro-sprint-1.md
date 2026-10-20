# Demonstração da Sprint 1 — cinco minutos

Gravar o aplicativo em execução, com a explicação do responsável pelo projeto. Usar dados fictícios nos formulários. Este roteiro não é uma evidência de gravação: o responsável enviará o link separadamente ao professor depois de gravar e publicar, conforme decisão de 20/10.

| Tempo | Demonstração e explicação |
|---|---|
| 0:00–0:30 | Apresentar o incremento em uma frase por tela, com conteúdo próprio e dados em memória. |
| 0:30–2:00 | Mostrar biblioteca, material, capítulo, destaque/nota, formulário com erro e criação/revisão de cartão; usar Voltar e mostrar o estado atualizado. |
| 2:00–3:00 | Executar deep link no emulador e demonstrar layout estreito/largo, incluindo claro/escuro. |
| 3:00–4:00 | TalkBack percorrendo uma tela, com áudio; explicar os ajustes de rótulos, foco, feedback, toque e cores. |
| 4:00–5:00 | Mostrar CI verde, justificar estado elevado, rotas tipadas e classes de janela; dizer o que fica para a Sprint 2, a entrega final. |

Prazo de fechamento: 20/10/2026, segundo o SIGAA informado pelo responsável. Preparar o emulador conforme [guia Windows](emulador-windows-sprint-1.md). João deve gravar sua apresentação; este roteiro não constitui evidência de vídeo.

## Deep link Android

Com o APK instalado e dispositivo/emulador conectado:

```bash
adb shell am start -W -a android.intent.action.VIEW \
  -d 'revysa://material/design-software' br.ufrn.revysa
```

Repetir com o app já aberto e com `revysa://material/redes`. Verificar retorno e navegação entre capítulos. Um ID inexistente deve mostrar a recuperação para a biblioteca.

## Deep link Desktop

```bash
./gradlew :composeApp:run --args='revysa://material/design-software'
```

## Evidências finais a registrar

- URL do vídeo de cinco minutos, com fala do responsável e execução real do aplicativo.
- URL do PR e execução verde do CI no commit entregue.
- Dispositivo/versão Android, resultado da passagem com TalkBack e eventuais limitações.
