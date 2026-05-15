package com.example.gokulahealth.viewmodel

import androidx.lifecycle.*
import com.example.gokulahealth.data.Cattle
import com.example.gokulahealth.data.MilkRecord
import com.example.gokulahealth.data.Vaccination
import com.example.gokulahealth.repository.CattleRepository
import kotlinx.coroutines.launch
import java.time.LocalDate

class CattleViewModel(private val repo: CattleRepository) : ViewModel() {

    val allCattle = repo.allCattle
    val cattleCount = repo.cattleCount
    val pendingVaccinations = repo.pendingVaccinations

    private val _today = LocalDate.now().toString()
    val totalYieldToday = repo.getTotalYieldToday(_today)

    fun insertCattle(c: Cattle) = viewModelScope.launch { repo.insertCattle(c) }
    fun updateCattle(c: Cattle) = viewModelScope.launch { repo.updateCattle(c) }
    fun deleteCattle(c: Cattle) = viewModelScope.launch { repo.deleteCattle(c) }

    fun getMilkForCattle(id: Long) = repo.getMilkForCattle(id)
    fun insertMilk(r: MilkRecord) = viewModelScope.launch { repo.insertMilk(r) }
    fun updateMilk(r: MilkRecord) = viewModelScope.launch { repo.updateMilk(r) }
    fun deleteMilk(r: MilkRecord) = viewModelScope.launch { repo.deleteMilk(r) }

    fun getVaccinationsForCattle(id: Long) = repo.getVaccinationsForCattle(id)
    fun getOverdueCount() = repo.getOverdueCount(_today)
    fun insertVaccination(v: Vaccination) = viewModelScope.launch { repo.insertVaccination(v) }
    fun deleteVaccination(v: Vaccination) = viewModelScope.launch { repo.deleteVaccination(v) }
    fun markCompleted(id: Long) = viewModelScope.launch { repo.markCompleted(id) }

    suspend fun getMonthlyAverage(cattleId: Long): Double? {
        val from = LocalDate.now().withDayOfMonth(1).toString()
        val to = _today
        return repo.getMonthlyAverage(cattleId, from, to)
    }
}

class CattleViewModelFactory(private val repo: CattleRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = CattleViewModel(repo) as T
}
