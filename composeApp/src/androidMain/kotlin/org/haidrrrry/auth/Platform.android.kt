package org.haidrrrry.auth

import android.os.Build

class AndroidPlatform : Platform() {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()
actual open class Platform {
    actual open val name: String
        get() = TODO("Not yet implemented")
}