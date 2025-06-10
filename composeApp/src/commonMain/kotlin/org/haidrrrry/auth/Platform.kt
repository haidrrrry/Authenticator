package org.haidrrrry.auth

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform