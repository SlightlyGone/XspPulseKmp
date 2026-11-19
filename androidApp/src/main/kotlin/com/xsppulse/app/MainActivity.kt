package com.xsppulse.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import java.io.FileNotFoundException
import java.util.Properties

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val apiKey = try {
            assets.open("api.properties").use { input ->
                Properties().apply { load(input) }.getProperty("apiKey", "")
            }
        } catch (_: FileNotFoundException) {
            ""
        }
        setContent {
            App(apiKey = apiKey)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App(apiKey = "")
}