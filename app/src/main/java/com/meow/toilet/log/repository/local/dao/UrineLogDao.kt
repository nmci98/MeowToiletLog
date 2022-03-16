package com.meow.toilet.log.repository.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.meow.toilet.log.repository.local.entity.UrineLog

@Dao
abstract class UrineLogDao {
    @Query("SELECT * FROM UrineLog WHERE targetDate >= :startDate AND targetDate <= :endDate")
    abstract fun select(startDate: String, endDate: String): List<UrineLog>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(data: UrineLog)

    @Query("DELETE FROM UrineLog WHERE targetDate = :date")
    abstract fun delete(date: String?)

    @Query(" SELECT * FROM UrineLog")
    abstract fun observe() : LiveData<List<UrineLog>>
}