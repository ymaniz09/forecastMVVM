package com.github.ymaniz09.forecastmvvm.data.db.entity

import androidx.room.TypeConverter

class StringListConverter {
    @TypeConverter
    fun fromString(stringListString: String): List<String> {
        return stringListString.split(";").map { it }
    }

    @TypeConverter
    fun toString(stringList: List<String>): String {
        return stringList.joinToString(separator = ";")
    }
}