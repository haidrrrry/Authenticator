package org.haidrrrry.auth

import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970

actual fun getCurrentTimeSeconds(): Long {
    return NSDate().timeIntervalSince1970.toLong()
}