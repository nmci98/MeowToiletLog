package com.meow.toilet.log.repository.local

import androidx.room.TypeConverter
import java.time.LocalDate

class LocalDateConverter {
    @TypeConverter
    fun toLocalDate(value: String?): LocalDate? = value?.let {
        LocalDate.parse(it)
    }

    @TypeConverter
    fun fromLocalDate(date: LocalDate?): String? = date?.toString()
}