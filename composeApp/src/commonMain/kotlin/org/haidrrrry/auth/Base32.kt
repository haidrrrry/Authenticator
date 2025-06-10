package org.haidrrrry.auth

object Base32 {
    private val ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567"
    private val DECODE_TABLE = IntArray(128) { -1 }.apply {
        ALPHABET.forEachIndexed { index, c ->
            this[c.code] = index
        }
    }

    fun decode(encoded: String): ByteArray {
        val cleanInput = encoded.uppercase().replace("=", "")
        val outputList = mutableListOf<Byte>()
        var buffer = 0
        var bitsLeft = 0

        for (c in cleanInput) {
            val charCode = c.code
            if (charCode >= DECODE_TABLE.size) {
                throw IllegalArgumentException("Invalid character: $c")
            }

            val value = DECODE_TABLE[charCode]
            if (value < 0) {
                throw IllegalArgumentException("Invalid character: $c")
            }

            buffer = (buffer shl 5) or value
            bitsLeft += 5

            if (bitsLeft >= 8) {
                outputList.add((buffer shr (bitsLeft - 8)).toByte())
                bitsLeft -= 8
            }
        }

        return outputList.toByteArray()
    }
}

