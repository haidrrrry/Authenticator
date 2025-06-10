package org.haidrrrry.auth

import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

actual object HmacSha1 {
    actual fun hmacSha1(key: ByteArray, data: ByteArray): ByteArray {
        return try {
            val mac = Mac.getInstance("HmacSHA1")
            val signingKey = SecretKeySpec(key, "HmacSHA1")
            mac.init(signingKey)
            mac.doFinal(data)
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException("HmacSHA1 algorithm not available", e)
        } catch (e: InvalidKeyException) {
            throw RuntimeException("Invalid key for HmacSHA1", e)
        }
    }
}

