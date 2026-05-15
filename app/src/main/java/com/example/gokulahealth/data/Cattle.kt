package com.example.gokulahealth.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cattle")
data class Cattle(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val earTag: String,
    val name: String,
    val breed: String,
    val dateOfBirth: String,
    val photoUri: String = "",
    val notes: String = ""
)
