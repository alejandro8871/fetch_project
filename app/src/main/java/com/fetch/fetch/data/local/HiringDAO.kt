package com.fetch.fetch.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fetch.fetch.data.model.Hiring

@Dao
interface HiringDAO {
    @Query("SELECT * FROM hiring ORDER BY listId ASC, name ASC")
    fun getFetchItems(): List<Hiring>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFetchItems(items: List<Hiring>)

    @Query("DELETE FROM hiring")
    suspend fun clearFetchItems()
}