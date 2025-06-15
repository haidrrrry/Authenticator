package org.haidrrrry.auth.dao

import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSHomeDirectory

actual class DatabaseFactory {
    actual fun create(): RoomDatabase.Builder<FavoriteBookDatabase> {
        val dbFilePath = documentDirectory() + "/${FavoriteBookDatabase.DATABASE_NAME}"
        return Room.databaseBuilder<FavoriteBookDatabase>(
            name = dbFilePath,
        )
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
    val documentDirectory = NSHomeDirectory() + "/Documents"
    return documentDirectory
}