package com.example.gokulahealth.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BreedingDao {
    @Query("SELECT * FROM breeding_records WHERE cattleId = :cattleId ORDER BY heatDate DESC")
    fun getBreedingForCattle(cattleId: Long): LiveData<List<BreedingRecord>>

    @Query("SELECT * FROM breeding_records WHERE isPregnant = 0 AND calvingDate = '' ORDER BY expectedCalvingDate ASC")
    fun getPendingCalvings(): LiveData<List<BreedingRecord>>

    @Query("SELECT * FROM breeding_records WHERE expectedCalvingDate BETWEEN :from AND :to AND calvingDate = ''")
    suspend fun getDueCalvings(from: String, to: String): List<BreedingRecord>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: BreedingRecord): Long

    @Update
    suspend fun update(record: BreedingRecord)

    @Delete
    suspend fun delete(record: BreedingRecord)

    @Query("UPDATE breeding_records SET isPregnant = 1, calvingDate = :date WHERE id = :id")
    suspend fun markCalved(id: Long, date: String)
}
