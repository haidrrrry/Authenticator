package org.haidrrrry.auth

actual fun getCurrentTimeSeconds(): Long {
    return System.currentTimeMillis() / 1000
}