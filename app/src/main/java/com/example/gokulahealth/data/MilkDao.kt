package com.example.gokulahealth.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MilkDao {
    @Query("SELECT * FROM milk_records WHERE cattleId = :cattleId ORDER BY date DESC")
    fun getRecordsForCattle(cattleId: Long): LiveData<List<MilkRecord>>

    @Query("SELECT SUM(morningYield + eveningYield) FROM milk_records WHERE date = :date")
    fun getTotalYieldForDate(date: String): LiveData<Double?>

    @Query("SELECT AVG(morningYield + eveningYield) FROM milk_records WHERE cattleId = :cattleId AND date BETWEEN :from AND :to")
    suspend fun getMonthlyAverage(cattleId: Long, from: String, to: String): Double?

    @Query("SELECT * FROM milk_records WHERE cattleId = :cattleId AND date = :date LIMIT 1")
    suspend fun getRecordByDate(cattleId: Long, date: String): MilkRecord?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: MilkRecord): Long

    @Update
    suspend fun update(record: MilkRecord)

    @Delete
    suspend fun delete(record: MilkRecord)
}
