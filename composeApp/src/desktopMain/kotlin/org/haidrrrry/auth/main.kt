package org.haidrrrry.auth

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Authenticator",
    ) {
        App()
    }
}