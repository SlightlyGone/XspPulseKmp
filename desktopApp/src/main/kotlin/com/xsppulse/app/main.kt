package com.xsppulse.app

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.io.File
import java.util.Properties

fun main() = application {
    val config = listOf(File("local-config/api.properties"), File("../local-config/api.properties"))
        .firstOrNull { it.isFile }
    val apiKey = if (config != null) {
        config.inputStream().use { Properties().apply { load(it) }.getProperty("apiKey", "") }
    } else {
        ""
    }
    Window(
        onCloseRequest = ::exitApplication,
        title = "XspPulseKmp",
    ) {
        App(apiKey = apiKey)
    }
}