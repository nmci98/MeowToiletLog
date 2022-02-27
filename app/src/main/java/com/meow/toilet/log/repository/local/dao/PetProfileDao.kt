package com.meow.toilet.log.repository.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.meow.toilet.log.repository.local.entity.PetProfile

@Dao
abstract class PetProfileDao {
    @Query("SELECT * FROM PetProfile")
    abstract fun select(): PetProfile

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(data: PetProfile)
}