package org.haidrrrry.auth.utils

import kotlin.math.pow

// Simple multiplatform time provider - you'll need to implement actual() in each platform
expect fun getCurrentTimeMillis(): Long

object TOTPGenerator {

    fun generateTOTP(
        secret: String,
        timestamp: Long = getCurrentTimeMillis() / 1000,
        period: Int = 30,
        digits: Int = 6,
        algorithm: String = "SHA1"
    ): String {
        try {
            val secretBytes = Base32.decode(secret)
            val timeCounter = timestamp / period

            // Convert time counter to big-endian byte array
            val data = ByteArray(8)
            var value = timeCounter
            for (i in 7 downTo 0) {
                data[i] = (value and 0xFF).toByte()
                value = value ushr 8
            }

            // Use multiplatform HMAC
            val hash = when (algorithm.uppercase()) {
                "SHA1" -> hmacSHA1(secretBytes, data)

                else -> hmacSHA1(secretBytes, data)
            }

            // Dynamic truncation
            val offset = (hash[hash.size - 1].toInt() and 0x0F)
            val truncatedHash = (
                    ((hash[offset].toInt() and 0x7F) shl 24) or
                            ((hash[offset + 1].toInt() and 0xFF) shl 16) or
                            ((hash[offset + 2].toInt() and 0xFF) shl 8) or
                            (hash[offset + 3].toInt() and 0xFF)
                    )

            val code = truncatedHash % (10.0.pow(digits).toInt())
            return code.toString().padStart(digits, '0')
        } catch (e: Exception) {
            throw RuntimeException("Failed to generate TOTP: ${e.message}", e)
        }
    }

    fun getRemainingTime(period: Int = 30): Int {
        val currentTime = getCurrentTimeMillis() / 1000
        return period - (currentTime % period).toInt()
    }
}
expect fun hmacSHA1(key: ByteArray, data: ByteArray): ByteArray
