package com.meow.toilet.log.screen.n04_Log

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

/**
 * ログ画面リスト表示データ.
 * @param date 日付
 * @param urineCount おしっこ回数
 * @param stoolCount うんち回数
 */
@Parcelize
data class LogItemData(
    val date: LocalDate?,
    val urineCount: Int,
    val stoolCount: Int
) : Parcelable