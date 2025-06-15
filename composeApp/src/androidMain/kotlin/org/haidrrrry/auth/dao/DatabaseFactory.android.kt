package org.haidrrrry.auth.dao

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase



actual class DatabaseFactory(private val context: Context) {
    actual fun create(): RoomDatabase.Builder<FavoriteBookDatabase> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath(FavoriteBookDatabase.DATABASE_NAME)
        return Room.databaseBuilder<FavoriteBookDatabase>(
            context = appContext,
            name = dbFile.absolutePath
        )
    }
}