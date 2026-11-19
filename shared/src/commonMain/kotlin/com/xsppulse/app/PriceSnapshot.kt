package com.xsppulse.app

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PriceSnapshot(
    val symbol: String = "SPY",
    val bid: String? = null,
    val ask: String? = null,
    val mid: String? = null,
    val status: String = "DISCONNECTED",
    @SerialName("updated_at")
    val updatedAt: String = ""
)