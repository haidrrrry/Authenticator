package org.haidrrrry.auth

import platform.UIKit.UIDevice

class IOSPlatform : Platform() {
    override val name: String = "iOS ${UIDevice.currentDevice.systemVersion}"
}

actual fun getPlatform(): Platform = IOSPlatform()

actual open class Platform {
    actual open val name: String
        get() = TODO("Not yet implemented")
}