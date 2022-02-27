package com.meow.toilet.log.repository.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * ペットプロファイル.
 * @param name 名前
 * @param dateOfBirth 誕生日
 * @param gender 性別
 * @param bleed 種類
 */
@Entity
data class PetProfile(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val dateOfBirth: String?,
    val gender: Int?,
    val bleed: String?
)

enum class Gender(val value: Int) {
    MALE(0),
    FEMALE(1)
    ;

    companion object {
        fun from(value: Int): Gender? = values().firstOrNull { it.value == value }
    }
}