package org.haidrrrry.auth.dao

import androidx.room.RoomDatabase


expect class DatabaseFactory {
    fun create(): RoomDatabase.Builder<FavoriteBookDatabase>
}