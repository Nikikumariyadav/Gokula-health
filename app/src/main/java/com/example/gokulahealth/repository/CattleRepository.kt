package com.example.gokulahealth.repository

import com.example.gokulahealth.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CattleRepository(
    private val cattleDao: CattleDao,
    private val milkDao: MilkDao,
    private val vaccinationDao: VaccinationDao,
    private val breedingDao: BreedingDao
) {
    val allCattle = cattleDao.getAllCattle()
    val cattleCount = cattleDao.getCattleCount()
    val pendingVaccinations = vaccinationDao.getAllPending()
    val pendingCalvings = breedingDao.getPendingCalvings()

    // Cattle
    suspend fun insertCattle(c: Cattle) = withContext(Dispatchers.IO) { cattleDao.insert(c) }
    suspend fun updateCattle(c: Cattle) = withContext(Dispatchers.IO) { cattleDao.update(c) }
    suspend fun deleteCattle(c: Cattle) = withContext(Dispatchers.IO) { cattleDao.delete(c) }
    suspend fun getCattleById(id: Long) = withContext(Dispatchers.IO) { cattleDao.getCattleById(id) }

    // Milk
    fun getMilkForCattle(id: Long) = milkDao.getRecordsForCattle(id)
    fun getTotalYieldToday(date: String) = milkDao.getTotalYieldForDate(date)
    suspend fun insertMilk(r: MilkRecord) = withContext(Dispatchers.IO) { milkDao.insert(r) }
    suspend fun updateMilk(r: MilkRecord) = withContext(Dispatchers.IO) { milkDao.update(r) }
    suspend fun deleteMilk(r: MilkRecord) = withContext(Dispatchers.IO) { milkDao.delete(r) }
    suspend fun getMilkByDate(cattleId: Long, date: String) = withContext(Dispatchers.IO) { milkDao.getRecordByDate(cattleId, date) }
    suspend fun getMonthlyAverage(cattleId: Long, from: String, to: String) = withContext(Dispatchers.IO) { milkDao.getMonthlyAverage(cattleId, from, to) }

    // Vaccination
    fun getVaccinationsForCattle(id: Long) = vaccinationDao.getVaccinationsForCattle(id)
    fun getOverdueCount(today: String) = vaccinationDao.getOverdueCount(today)
    suspend fun insertVaccination(v: Vaccination) = withContext(Dispatchers.IO) { vaccinationDao.insert(v) }
    suspend fun deleteVaccination(v: Vaccination) = withContext(Dispatchers.IO) { vaccinationDao.delete(v) }
    suspend fun markCompleted(id: Long) = withContext(Dispatchers.IO) { vaccinationDao.markCompleted(id) }
    suspend fun getDueBetween(from: String, to: String) = withContext(Dispatchers.IO) { vaccinationDao.getDueBetween(from, to) }

    // Breeding
    fun getBreedingForCattle(id: Long) = breedingDao.getBreedingForCattle(id)
    suspend fun insertBreeding(r: BreedingRecord) = withContext(Dispatchers.IO) { breedingDao.insert(r) }
    suspend fun deleteBreeding(r: BreedingRecord) = withContext(Dispatchers.IO) { breedingDao.delete(r) }
    suspend fun markCalved(id: Long, date: String) = withContext(Dispatchers.IO) { breedingDao.markCalved(id, date) }
}
