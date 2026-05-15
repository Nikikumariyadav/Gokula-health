package com.example.gokulahealth.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "vaccinations",
    foreignKeys = [ForeignKey(
        entity = Cattle::class,
        parentColumns = ["id"],
        childColumns = ["cattleId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("cattleId")]
)
data class Vaccination(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val cattleId: Long,
    val vaccineName: String,
    val scheduledDate: String,
    val isCompleted: Boolean = false,
    val notes: String = ""
)
