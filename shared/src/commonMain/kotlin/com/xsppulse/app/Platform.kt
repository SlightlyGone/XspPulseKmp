package com.xsppulse.app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform