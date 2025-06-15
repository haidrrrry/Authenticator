

package org.haidrrrry.auth.dao

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.haidrrrry.auth.classes.AuthAccount
import org.haidrrrry.auth.utils.StringListTypeConverter

@Database(
    entities = [AuthAccount::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(StringListTypeConverter::class)
@ConstructedBy(BookDatabaseConstructor::class)
abstract class FavoriteBookDatabase : RoomDatabase() {
    abstract val favoriteBookDao: AuthAccountDao

    companion object {
        const val DATABASE_NAME = "auth.db"
    }
}