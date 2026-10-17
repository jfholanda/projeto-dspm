package br.ufrn.revysa

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main(args: Array<String>) =
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Revysa",
            state = rememberWindowState(width = 1100.dp, height = 800.dp),
        ) {
            App(deepLink = args.firstOrNull())
        }
    }
