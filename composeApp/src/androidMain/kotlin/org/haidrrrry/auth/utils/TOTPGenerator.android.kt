package org.haidrrrry.auth.utils

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

actual fun getCurrentTimeMillis(): Long = System.currentTimeMillis()

actual fun hmacSHA1(key: ByteArray, data: ByteArray): ByteArray {
    val mac = Mac.getInstance("HmacSHA1")
    mac.init(SecretKeySpec(key, "HmacSHA1"))
    return mac.doFinal(data)
}

