package com.example.gokulahealth.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CattleDao {
    @Query("SELECT * FROM cattle ORDER BY name ASC")
    fun getAllCattle(): LiveData<List<Cattle>>

    @Query("SELECT * FROM cattle WHERE id = :id")
    suspend fun getCattleById(id: Long): Cattle?

    @Query("SELECT COUNT(*) FROM cattle")
    fun getCattleCount(): LiveData<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cattle: Cattle): Long

    @Update
    suspend fun update(cattle: Cattle)

    @Delete
    suspend fun delete(cattle: Cattle)
}
