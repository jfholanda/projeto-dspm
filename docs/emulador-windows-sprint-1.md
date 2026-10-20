# Emulador Windows — Sprint 1

## Ambiente identificado em 20/10/2026

Android Studio e SDK já instalados no Windows. AVD `Pixel_7`, Google Play, Android 16/API 36; AVD `Pixel_8` também disponível. O Pixel 7 foi iniciado e o pacote `com.google.android.marvin.talkback` está instalado. A compilação usa API 37; o APK pode executar na API 36 porque o mínimo do app é API 24.

Usar o ADB do Windows no PowerShell. Não é necessário instalar outro emulador nem conectar o ADB Linux ao Windows.

```powershell
$adb = "$env:LOCALAPPDATA\Android\Sdk\platform-tools\adb.exe"
$emulator = "$env:LOCALAPPDATA\Android\Sdk\emulator\emulator.exe"
& $adb devices -l
# Se ainda não estiver aberto:
Start-Process -FilePath $emulator -ArgumentList @('-avd', 'Pixel_7', '-no-snapshot-save')
```

Primeiro boot pode ser lento. Foi observado um aviso de lentidão do System UI; após aguardar, o emulador abriu o Revysa. Não apagar os dados do AVD para contornar lentidão.

## Instalar o APK

Depois do build, ajustar o caminho ao local do checkout. Neste ambiente, o projeto fica no WSL:

```powershell
$apk = '\\wsl.localhost\Ubuntu\home\jota\faculdade\dspm\projeto-dspm\composeApp\build\outputs\apk\debug\composeApp-debug.apk'
& $adb -s emulator-5554 install -r $apk
```

Se o emulador tiver outro serial, usar o identificado em `devices -l`.

## Deep link e Voltar

```powershell
& $adb -s emulator-5554 shell am force-stop br.ufrn.revysa
& $adb -s emulator-5554 shell am start -W -a android.intent.action.VIEW -d 'revysa://material/design-software' br.ufrn.revysa
& $adb -s emulator-5554 shell input keyevent 4
# Repetir com o aplicativo já aberto:
& $adb -s emulator-5554 shell am start -W -a android.intent.action.VIEW -d 'revysa://material/redes' br.ufrn.revysa
& $adb -s emulator-5554 shell am start -W -a android.intent.action.VIEW -d 'revysa://material/inexistente' br.ufrn.revysa
```

Conferir material/capítulo correto, retorno à biblioteca ou Home e recuperação para ID inexistente. Gravar a execução real, não apenas a saída do terminal.

## TalkBack e fonte ampliada — passagem humana

1. Abrir Configurações do Android → Acessibilidade → TalkBack e ativar. Toque seleciona/fala; toque duplo ativa; usar dois dedos para rolar.
2. Percorrer as telas e preencher o checklist em `acessibilidade-sprint-1.md`, registrando nomes, foco, estados, mensagens e ações alcançáveis.
3. Nos formulários, conferir teclados de e-mail/senha, avanço de foco e Concluir. Verificar pergunta/resposta vazias e notas/cartões com várias linhas.
4. Aumentar a fonte nas configurações de exibição, repetir formulários com teclado aberto e conferir retrato/paisagem. Restaurar a preferência após a passagem.
5. Gravar o leitor percorrendo uma tela, com áudio do sistema. Registrar somente resultados observados; testes automatizados não equivalem à passagem humana.

## Vídeo

Gravar a janela do emulador e sua apresentação, por exemplo com OBS Studio ou a ferramenta de captura do Windows que você já utiliza. Mostrar também a janela Desktop redimensionada e o CI verde. Seguir `roteiro-sprint-1.md`, publicar como não listado no YouTube e conferir o acesso em janela anônima. O responsável optou por enviar o link diretamente ao professor; a publicação do PR de evidências não depende de inserir essa URL no README.

## Previews no Android Studio

Abrir o projeto e sincronizar o Gradle. Em `commonMain`, abrir `LibraryPreviews.kt`, `ReaderPreviews.kt`, `StudyPreviews.kt` e `ProfilePreviews.kt`; selecionar a visualização de previews. São dados fixos, sem NavController, catálogo global ou sessão de produção. Conferir especialmente os casos vazios e erros. A renderização dos componentes é exercitada pela suíte Desktop, mas a inspeção no Android Studio ainda deve ser registrada pelo responsável.

## Teclado do emulador

Se o Gboard abrir “Try out your stylus”, cancelar o tutorial antes de testar campos. Na passagem de 20/10 foi necessário desativar escrita por caneta apenas no AVD: `adb shell settings put secure stylus_handwriting_enabled 0`. Isso permitiu a entrada convencional no formulário. Não altera o aplicativo. O System UI também apresentou lentidão durante a preparação; repetir a demonstração somente depois que o emulador estiver estável.
