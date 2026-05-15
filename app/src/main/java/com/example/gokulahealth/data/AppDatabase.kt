package com.example.gokulahealth.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Cattle::class, MilkRecord::class, Vaccination::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cattleDao(): CattleDao
    abstract fun milkDao(): MilkDao
    abstract fun vaccinationDao(): VaccinationDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "gokula_health.db")
                    .build().also { INSTANCE = it }
            }
    }
}
