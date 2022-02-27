package com.meow.toilet.log.repository

import com.meow.toilet.log.repository.local.AppDatabase
import com.meow.toilet.log.repository.local.entity.StoolLog
import com.meow.toilet.log.repository.local.entity.UrineLog
import timber.log.Timber
import java.time.LocalDate

class DataRepository(
    private val appDatabase: AppDatabase
//    , private val repository
) {

    // region 変数

    // endregion 変数

    // region データベース

    /**
     * トイレログ保存処理.
     * @param urineData トイレログデータ
     */
    fun updateToiletLog(targetDate: LocalDate, urineData: UrineLog, stoolData: StoolLog) {
        Timber.d("updateToiletLog : $urineData $stoolData")
        // 対象日のレコードを全て削除する
        appDatabase.getUrineLogDao().delete(targetDate.toString())
        appDatabase.getStoolLogDao().delete(targetDate.toString())

        // おしっこの回数を更新する
        appDatabase.getUrineLogDao().insert(urineData)
        // うんちの回数を更新する
        appDatabase.getStoolLogDao().insert(stoolData)
    }

//    fun observeUrineLog() = appDatabase.getUrineLogDao().observe()
//    fun observeStoolLog() = appDatabase.getStoolLogDao().observe()

    /**
     * トイレログデータ取得処理.
     * @param startDate 取得開始日付
     * @param endDate 取得日付
     * @return Pair(おしっこログ、うんちログ)
     */
    fun getToiletLogData(
        startDate: LocalDate,
        endDate: LocalDate
    ): Pair<List<UrineLog>?, List<StoolLog>?> {
        return Pair(
            appDatabase.getUrineLogDao().select(startDate.toString(), endDate.toString()),
            appDatabase.getStoolLogDao().select(startDate.toString(), endDate.toString())
        )
    }

    /**
     * トイレログ回数処理.
     * 指定した期間のおしっことうんちの回数を返却する
     * @param startDate 取得開始日付
     * @param endDate 取得日付
     * @return Pair(おしっこ回数、うんち回数)
     */
    fun getToiletCount(startDate: LocalDate, endDate: LocalDate): Pair<Int, Int> {
        val urineLog = appDatabase.getUrineLogDao().select(startDate.toString(), endDate.toString())
        Timber.d("urineLog $urineLog")
        var urineCount = 0
        urineLog?.forEach {
            urineCount += it.count
        }
        val stoolLog = appDatabase.getStoolLogDao().select(startDate.toString(), endDate.toString())
        Timber.d("stoolLog $stoolLog")
        var stoolCount = 0
        stoolLog?.forEach {
            stoolCount = +it.count
        }
        Timber.d("getToiletCount - urine : $urineCount stool : $stoolCount")
        return Pair(urineCount, stoolCount)
    }

    // endregion データベース
}