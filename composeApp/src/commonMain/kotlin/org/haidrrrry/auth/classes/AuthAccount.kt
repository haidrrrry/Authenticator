package org.haidrrrry.auth.classes


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

@Entity(tableName = "auth_accounts")
@TypeConverters(ColorConverter::class)
data class AuthAccount(
    @PrimaryKey val id: String,
    val serviceName: String,
    val accountName: String,
    val code: String,
    val brandColor: Color,
    val serviceIcon: String
)

// 2. Type Converter for Color
class ColorConverter {
    @TypeConverter
    fun fromColor(color: Color): Int {
        return color.toArgb()
    }

    @TypeConverter
    fun toColor(colorInt: Int): Color {
        return Color(colorInt)
    }
}

enum class ViewMode { LIST, GRID }