package org.haidrrrry.auth

import kotlinx.cinterop.*
import platform.CoreCrypto.CCHmac
import platform.CoreCrypto.CC_SHA1_DIGEST_LENGTH
import platform.CoreCrypto.kCCHmacAlgSHA1
import platform.Foundation.*
import platform.Security.*

actual object HmacSha1 {
    @OptIn(ExperimentalForeignApi::class)
    actual fun hmacSha1(key: ByteArray, data: ByteArray): ByteArray {
        return memScoped {
            val keyData = key.toCValues()
            val inputData = data.toCValues()
            val outputLength = CC_SHA1_DIGEST_LENGTH
            val output = allocArray<ByteVar>(outputLength)

            CCHmac(
                kCCHmacAlgSHA1.toUInt(),
                keyData.ptr,
                key.size.convert(),
                inputData.ptr,
                data.size.convert(),
                output
            )

            ByteArray(outputLength) { output[it] }
        }
    }
}