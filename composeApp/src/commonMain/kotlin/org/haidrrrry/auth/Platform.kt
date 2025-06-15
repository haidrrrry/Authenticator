package org.haidrrrry.auth

expect open class Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun getCurrentTimeMillis(): Long


