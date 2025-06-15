package org.haidrrrry.auth

actual open class Platform {
    actual val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = Platform()