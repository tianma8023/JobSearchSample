package com.example.jobsearch.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.jobsearch.data.db.dao.JobSearchHistoryDao
import com.example.jobsearch.data.db.entity.JobSearchHistory


@Database(
    entities = [
        JobSearchHistory::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    companion object {

        private const val DATABASE_NAME = "app_database"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room
                    .databaseBuilder(context.applicationContext, AppDatabase::class.java, DATABASE_NAME)
                    .build()

                INSTANCE = instance

                instance
            }
        }
    }


    abstract fun jobSearchHistoryDao(): JobSearchHistoryDao

}