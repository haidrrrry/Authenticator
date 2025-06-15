package org.haidrrrry.auth

import androidx.compose.ui.window.ComposeUIViewController
import org.haidrrrry.auth.di.initializeKoin

fun MainViewController() = ComposeUIViewController(
    configure={
        initializeKoin()
    }
) { App(

) }