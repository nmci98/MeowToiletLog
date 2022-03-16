package com.meow.toilet.log.util.binding

import androidx.databinding.InverseMethod
import com.meow.toilet.log.R
import com.meow.toilet.log.repository.local.entity.Gender

object GenderConverter {
    @JvmStatic
    @InverseMethod("toGender")
    fun toInt(gender: Gender?): Int {
        return when (gender) {
            Gender.MALE -> R.id.radio_button_gender_male
            Gender.FEMALE -> R.id.radio_button_gender_female
            Gender.UNKNOWN -> R.id.radio_button_gender_other
            else -> R.id.radio_button_gender_other
        }
    }

    @JvmStatic
    fun toGender(id: Int): Gender {
        return when (id) {
            R.id.radio_button_gender_male -> Gender.MALE
            R.id.radio_button_gender_female -> Gender.FEMALE
            R.id.radio_button_gender_other -> Gender.UNKNOWN
            else -> Gender.UNKNOWN
        }
    }
}