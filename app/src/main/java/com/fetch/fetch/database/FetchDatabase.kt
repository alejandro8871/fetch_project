package com.fetch.fetch.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fetch.fetch.data.local.HiringDAO
import com.fetch.fetch.data.model.Hiring

@Database(entities = [Hiring::class], version =2, exportSchema = false)
abstract class FetchDatabase : RoomDatabase() {
    abstract fun hiringDAO(): HiringDAO

    companion object {
        @Volatile
        private var INSTANCE: FetchDatabase? = null

        fun getDatabase(context: Context): FetchDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FetchDatabase::class.java,
                    "fetch_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}