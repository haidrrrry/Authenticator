package org.haidrrrry.auth

expect object HmacSha1 {
    fun hmacSha1(key: ByteArray, data: ByteArray): ByteArray
}