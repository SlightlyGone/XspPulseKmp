package com.xsppulse.app

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class XspApiClient(
    private val baseUrl: String,
    private val apiKey: String
) {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                }
            )
        }
    }

    suspend fun fetchPrice(symbol: String = "SPY"): PriceSnapshot {
        return client.get("$baseUrl/price/xsp") {
            header(HttpHeaders.Authorization, "Bearer $apiKey")
            parameter("symbol", symbol)
        }.body()
    }

    fun close() {
        client.close()
    }
}
