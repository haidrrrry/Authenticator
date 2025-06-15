package org.haidrrrry.auth.utils

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object StringListTypeConverter {

    @TypeConverter
    fun fromString(value :String) : List<String>
    {
        return Json.decodeFromString(value)
    }
    @TypeConverter
    fun fromList(list : List<String>)  :String
    {
        return Json.encodeToString(list)
    }
}