package com.example.bitfit

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SleepDao {
    @Query("SELECT * FROM sleep_table")
    fun getAll(): Flow<List<SleepEntity>>

    @Insert
    fun insertAll(articles: List<SleepEntity>)

    @Query("DELETE FROM sleep_table")
    fun deleteAll()
}
