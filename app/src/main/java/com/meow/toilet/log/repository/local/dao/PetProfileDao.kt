package com.meow.toilet.log.repository.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.meow.toilet.log.repository.local.entity.PetProfile

@Dao
abstract class PetProfileDao {
    @Query("SELECT * FROM PetProfile")
    abstract fun select(): PetProfile?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(data: PetProfile)

    @Transaction
    open fun deleteAndInsert(data: PetProfile){
        deleteAll()
        insert(data)
    }

    @Query("DELETE FROM PetProfile")
    abstract fun deleteAll()

    @Query(" SELECT * FROM PetProfile")
    abstract fun observe(): LiveData<PetProfile?>
}