# Acessibilidade e adaptação — Sprint 1

## Implementação

- Controles Material 3 com texto visível e semântica de ação: botões, chips, campos e opções de quiz.
- Ícones decorativos da navegação removidos da árvore semântica; os rótulos textuais identificam os destinos, com estado selecionado.
- Títulos de tela marcados como cabeçalhos. Resposta de revisão, resultado do quiz e confirmação de leitura usam região de anúncio educado.
- Pergunta/resposta obrigatórias e erros nos campos; entrada com senha mascarada e sem participação no estado salvo da Activity.
- Botões e chips com altura mínima de 48 dp. Alternativas do quiz selecionáveis com papel de botão de opção e altura mínima de 48 dp.
- Listas roláveis com chaves para materiais/capítulos; conteúdo com largura máxima de 760 dp, respeitando os limites da janela.
- Abaixo de 840 dp: navegação inferior; a partir de 840 dp: navegação lateral. Insets de status, navegação e teclado considerados no Android.
- Cores de texto derivadas do tema; pares de texto/fundo da paleta própria verificados por teste de contraste WCAG AA (≥4,5:1).

## Verificação automatizada

`ThemeContrastTest` verifica seis pares de cores em cada tema. `SprintOneUiTest` exercita as telas em 390×740 e 1000×740 dp e salva capturas em `composeApp/build/reports/sprint-1/`. Isso confere composição e navegação; não substitui inspeção visual, escala de fontes ou leitor de tela Android.

## Passagem manual Android — ainda necessária

Usar aparelho ou emulador com TalkBack instalado e registrar modelo, versão Android e resultado. Não há dispositivo Android conectado neste ambiente de implementação.

- [ ] Percorrer Home e os cinco destinos; confirmar rótulos, ordem de foco e destino selecionado, sem leitura dos símbolos decorativos.
- [ ] Abrir material, escolher capítulo e usar Voltar, incluindo o botão de retorno do sistema.
- [ ] Tentar salvar cartão vazio; confirmar identificação dos campos e anúncio dos erros; preencher e salvar.
- [ ] Destacar, salvar nota, revelar cartão e conferir quiz; ouvir feedback e estado das alternativas.
- [ ] Conferir temas claro e escuro e áreas de toque, especialmente teclado aberto e navegação inferior.
- [ ] Aumentar fonte para 200% e repetir os formulários em janela estreita; conferir leitura e rolagem sem corte de ações.
- [ ] Registrar a evidência de TalkBack no vídeo ou neste documento, com data e eventuais ajustes.
