package com.meow.toilet.log.repository.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * うんちログ.
 * @param targetDate 日付
 * @param count 回数
 * @param createAt 登録日
 * @param imageUrl 画像URL
 */
@Entity
data class StoolLog(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val targetDate: String?,
    val count: Int,
    val createAt: String,
    val imageUrl: String?
) {
    constructor(
        targetDate: String?,
        count: Int,
        createAt: String,
        imageUrl: String?
    ) : this(
        0,
        targetDate,
        count,
        createAt,
        imageUrl
    )
}