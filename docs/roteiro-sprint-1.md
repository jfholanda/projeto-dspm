# Demonstração da Sprint 1 — cinco minutos

Gravar o aplicativo em execução, com a explicação do responsável pelo projeto. Usar dados fictícios nos formulários. Este roteiro não é uma evidência de gravação: adicionar o link do vídeo ao README depois de gravar e publicar.

| Tempo | Demonstração e explicação |
|---|---|
| 0:00–0:30 | Apresentar o Revysa e o fluxo de estudo ativo. Mostrar Home, próxima revisão e materiais. Explicar que o conteúdo é próprio e que os dados desta etapa ficam em memória. |
| 0:30–1:15 | Abrir biblioteca, buscar material e experimentar uma busca sem resultados. Explorar o catálogo e adicionar Redes à biblioteca. Abrir disciplinas e escolher um material. |
| 1:15–2:00 | Abrir dois capítulos diferentes, destacar uma ideia, salvar uma nota e voltar para confirmar que ela continua disponível na sessão. Marcar uma seção como lida e conferir progresso. |
| 2:00–2:50 | Criar flashcard: tentar salvar vazio, mostrar os erros, preencher pergunta/resposta e salvar. Abrir revisão, revelar resposta, avaliar o cartão e concluir a fila. |
| 2:50–3:20 | Abrir quiz, mostrar que conferir exige uma alternativa, responder e mostrar o feedback. Abrir perfil e mostrar as superfícies de login/cadastro. |
| 3:20–4:00 | Alternar claro/escuro. Redimensionar a janela entre 390 e 1000 dp para mostrar barra inferior e navegação lateral. Mostrar foco e rótulos com TalkBack no Android. |
| 4:00–4:30 | Disparar o deep link abaixo. Mostrar que ele abre diretamente o material, com retorno à Home. Explicar rotas tipadas e o grafo aninhado da biblioteca. |
| 4:30–5:00 | Mostrar os resultados dos testes e do CI. Justificar Navigation Compose pela pilha de retorno e argumentos; Material 3 pelos componentes e semântica; testes Desktop pela UI compartilhada. Informar que backend, autenticação real, persistência e agendamento real de revisões ficam para etapas seguintes. |

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
