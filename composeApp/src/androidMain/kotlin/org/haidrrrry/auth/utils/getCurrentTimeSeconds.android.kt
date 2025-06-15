package org.haidrrrry.auth.utils

actual fun getCurrentTimeSeconds(): Long {
    return System.currentTimeMillis() / 1000
}