package com.meow.toilet.log.screen.n02_home

import android.net.Uri
import android.os.Parcelable
import com.meow.toilet.log.repository.local.entity.Gender
import com.meow.toilet.log.repository.local.entity.PetProfile
import kotlinx.parcelize.Parcelize
import timber.log.Timber
import java.time.LocalDate
import java.time.Period

/**
 * ペットプロファイル.
 * @param name 名前
 * @param dateOfBirth 誕生日
 * @param age 年齢
 * @param gender 性別
 * @param breed 種類
 * @param weight 体重
 * @param profileImageUri プロファイル画像URL
 */
@Parcelize
data class PetProfileData(
    val name: String,
    val dateOfBirth: LocalDate,
    val age: String?,
    val gender: String?,
    val breed: String?,
    val weight: String?,
    val profileImageUri: Uri?
) : Parcelable {

    constructor(petProfile: PetProfile) : this(
        name = petProfile.name,
        dateOfBirth = petProfile.dateOfBirth,
        age = try {
            Period.between(petProfile.dateOfBirth, LocalDate.now()).let {
                "${it.years}歳${it.months}ヶ月"
            }
        } catch (e: Exception) {
            Timber.e(e)
            null
        },
        gender = Gender.from(petProfile.gender)?.symbol,
        breed = petProfile.breed,
        weight = petProfile.weight?.toString(),
        profileImageUri = try {
            petProfile.profileImageUri?.let { Uri.parse(it) }
        } catch (e: Exception) {
            Timber.e(e)
            null
        }
    )
}