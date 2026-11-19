package com.xsppulse.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun App(
    baseUrl: String = "https://145-241-201-25.nip.io",
    //baseUrl: String = "https://localhost:8000",
    apiKey: String
) {
    var selectedSymbol by remember { mutableStateOf("XSP") }
    val apiClient = remember(baseUrl, apiKey) { XspApiClient(baseUrl, apiKey) }
    var snapshot by remember { mutableStateOf(PriceSnapshot(status = "CONNECTING")) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    DisposableEffect(apiClient) {
        onDispose { apiClient.close() }
    }

    LaunchedEffect(apiClient, selectedSymbol) {
        while (true) {
            try {
                snapshot = apiClient.fetchPrice(selectedSymbol)
                errorMessage = null
            } catch (e: Exception) {
                errorMessage = e.message ?: "Network error"
            }
            delay(2000L)
        }
    }

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize().padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row {listOf("SPY" to "SPY", "XSP" to "XSP", "ES" to "/ES").forEachIndexed { index, (key, label) ->
                    if (index > 0) Spacer(modifier = Modifier.width(8.dp))
                    if (selectedSymbol == key) {
                        Button(onClick = { selectedSymbol = key }) { Text(label) }
                    } else {
                        OutlinedButton(onClick = { selectedSymbol = key }) { Text(label) }
                    }
                }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = snapshot.symbol,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = snapshot.mid?.let { "$$it" } ?: "--.--",
                    style = MaterialTheme.typography.displayLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Bid: ${snapshot.bid ?: "--"}  |  Ask: ${snapshot.ask ?: "--"}",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Status: ${errorMessage ?: snapshot.status}",
                    style = MaterialTheme.typography.bodyMedium
                )
                if (snapshot.updatedAt.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Updated: ${snapshot.updatedAt}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}
