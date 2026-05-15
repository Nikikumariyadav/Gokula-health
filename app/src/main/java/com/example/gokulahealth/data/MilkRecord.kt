package com.example.gokulahealth.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "milk_records",
    foreignKeys = [ForeignKey(
        entity = Cattle::class,
        parentColumns = ["id"],
        childColumns = ["cattleId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("cattleId")]
)
data class MilkRecord(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val cattleId: Long,
    val date: String,
    val morningYield: Double,
    val eveningYield: Double,
    val notes: String = ""
) {
    val totalYield: Double get() = morningYield + eveningYield
}
