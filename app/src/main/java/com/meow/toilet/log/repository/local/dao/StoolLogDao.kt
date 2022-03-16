package com.meow.toilet.log.repository.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.meow.toilet.log.repository.local.entity.StoolLog

@Dao
abstract class StoolLogDao {
    @Query("SELECT * FROM StoolLog WHERE targetDate >= :startDate AND targetDate <= :endDate")
    abstract fun select(startDate: String, endDate: String): List<StoolLog>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(data: StoolLog)

    @Query("DELETE FROM StoolLog WHERE targetDate = :date")
    abstract fun delete(date: String?)

    @Query(" SELECT * FROM StoolLog")
    abstract fun observe(): LiveData<List<StoolLog>>
}