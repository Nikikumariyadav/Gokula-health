package com.example.gokulahealth.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "breeding_records",
    foreignKeys = [ForeignKey(
        entity = Cattle::class,
        parentColumns = ["id"],
        childColumns = ["cattleId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("cattleId")]
)
data class BreedingRecord(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val cattleId: Long,
    val heatDate: String,          // Date heat cycle detected
    val breedingDate: String,      // Date of insemination/breeding
    val method: String,            // "Natural" or "AI" (Artificial Insemination)
    val bullOrSemenId: String = "", // Bull ID or semen straw ID
    val expectedCalvingDate: String = "", // heatDate + ~283 days
    val isPregnant: Boolean = false,
    val calvingDate: String = "",  // Actual calving date
    val notes: String = ""
)
