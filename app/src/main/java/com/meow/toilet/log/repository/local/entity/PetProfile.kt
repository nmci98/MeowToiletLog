package com.meow.toilet.log.repository.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.meow.toilet.log.repository.local.LocalDateConverter
import java.time.LocalDate

/**
 * ペットプロファイル.
 * @param name 名前
 * @param dateOfBirth 誕生日
 * @param gender 性別
 * @param breed 種類
 * @param weight 体重
 */
@Entity
@TypeConverters(LocalDateConverter::class)
data class PetProfile(
    @PrimaryKey val id: Int,
    val name: String,
    val dateOfBirth: LocalDate,
    val gender: Int,
    val breed: String?,
    val weight: Double?
) {

    companion object {
        const val ONLY_ONE_ID = 0
    }

    constructor(
        name: String,
        dateOfBirth: LocalDate,
        gender: Int,
        breed: String?,
        weight: Double?
    ) : this(
        ONLY_ONE_ID,
        name = name,
        dateOfBirth = dateOfBirth,
        gender = gender,
        breed = breed,
        weight = weight
    )
}

enum class Gender(val value: Int, val symbol: String) {
    MALE(0, "♂"),
    FEMALE(1, "♀")
    ;

    companion object {
        fun from(value: Int): Gender? = values().firstOrNull { it.value == value }
    }
}