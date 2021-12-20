package com.example.animeapp.data.local.converter

import androidx.room.TypeConverter
import com.example.animeapp.util.Constants.SEPARATOR


class DatabaseConverter {

    @TypeConverter
    fun convertListOfStringToString(listOfString: List<String>) = with(listOfString) {
        val stringBuilder = StringBuilder()
        this.forEach { item ->
            stringBuilder.append(item).append(SEPARATOR)
        }
        stringBuilder.toString().dropLast(1)
    }

    @TypeConverter
    fun convertStringToListOfString(string: String) = with(string) {
        string.split(SEPARATOR)
    }
}