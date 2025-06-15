package org.haidrrrry.auth.utils

import kotlinx.cinterop.*
import platform.CoreCrypto.CCHmac
import platform.CoreCrypto.CCHmacAlgorithm
import platform.CoreCrypto.CC_SHA1_DIGEST_LENGTH
import platform.CoreCrypto.CC_SHA256_DIGEST_LENGTH
import platform.CoreCrypto.CC_SHA512_DIGEST_LENGTH
import platform.CoreCrypto.kCCHmacAlgSHA1
import platform.CoreCrypto.kCCHmacAlgSHA256
import platform.CoreCrypto.kCCHmacAlgSHA512
import platform.Foundation.*
import platform.Security.*

actual fun getCurrentTimeMillis(): Long = (NSDate().timeIntervalSince1970 * 1000).toLong()

actual fun hmacSHA1(key: ByteArray, data: ByteArray): ByteArray {
    return hmac(key, data, kCCHmacAlgSHA1, CC_SHA1_DIGEST_LENGTH)
}



@OptIn(ExperimentalForeignApi::class)
private fun hmac(key: ByteArray, data: ByteArray, algorithm: CCHmacAlgorithm, digestLength: Int): ByteArray {
    val result = ByteArray(digestLength)

    key.usePinned { keyPinned ->
        data.usePinned { dataPinned ->
            result.usePinned { resultPinned ->
                CCHmac(
                    algorithm,
                    keyPinned.addressOf(0),
                    key.size.toULong(),
                    dataPinned.addressOf(0),
                    data.size.toULong(),
                    resultPinned.addressOf(0)
                )
            }
        }
    }

    return result
}