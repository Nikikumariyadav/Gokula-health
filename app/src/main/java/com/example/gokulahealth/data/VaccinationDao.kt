package com.example.gokulahealth.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface VaccinationDao {
    @Query("SELECT * FROM vaccinations WHERE cattleId = :cattleId ORDER BY scheduledDate ASC")
    fun getVaccinationsForCattle(cattleId: Long): LiveData<List<Vaccination>>

    @Query("SELECT * FROM vaccinations WHERE isCompleted = 0 ORDER BY scheduledDate ASC")
    fun getAllPending(): LiveData<List<Vaccination>>

    @Query("SELECT * FROM vaccinations WHERE isCompleted = 0 AND scheduledDate BETWEEN :from AND :to ORDER BY scheduledDate ASC")
    suspend fun getDueBetween(from: String, to: String): List<Vaccination>

    @Query("SELECT COUNT(*) FROM vaccinations WHERE isCompleted = 0 AND scheduledDate <= :today")
    fun getOverdueCount(today: String): LiveData<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vaccination: Vaccination): Long

    @Update
    suspend fun update(vaccination: Vaccination)

    @Delete
    suspend fun delete(vaccination: Vaccination)

    @Query("UPDATE vaccinations SET isCompleted = 1 WHERE id = :id")
    suspend fun markCompleted(id: Long)
}
