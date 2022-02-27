package com.meow.toilet.log.repository.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.meow.toilet.log.repository.local.dao.PetProfileDao
import com.meow.toilet.log.repository.local.dao.StoolLogDao
import com.meow.toilet.log.repository.local.dao.UrineLogDao
import com.meow.toilet.log.repository.local.entity.PetProfile
import com.meow.toilet.log.repository.local.entity.StoolLog
import com.meow.toilet.log.repository.local.entity.UrineLog

@androidx.room.Database(
    entities = [
        PetProfile::class,
        UrineLog::class,
        StoolLog::class
    ],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private const val DATABASE_NAME = "app.db"
        fun getInstance(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }

    abstract fun getPetProfileDao(): PetProfileDao
    abstract fun getUrineLogDao(): UrineLogDao
    abstract fun getStoolLogDao(): StoolLogDao
}