package com.example.gokulahealth.ui.vaccination

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.gokulahealth.GokulaApp
import com.example.gokulahealth.data.Vaccination
import com.example.gokulahealth.databinding.ActivityAddVaccinationBinding
import com.example.gokulahealth.notification.VaccinationScheduler
import com.example.gokulahealth.viewmodel.CattleViewModel
import com.example.gokulahealth.viewmodel.CattleViewModelFactory
import kotlinx.coroutines.launch

class AddVaccinationActivity : AppCompatActivity() {
    private lateinit var b: ActivityAddVaccinationBinding
    private lateinit var vm: CattleViewModel
    private var cattleId = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityAddVaccinationBinding.inflate(layoutInflater)
        setContentView(b.root)
        supportActionBar?.title = "Schedule Vaccination"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        cattleId = intent.getLongExtra("cattle_id", -1L)
        if (cattleId == -1L) { finish(); return }
        vm = ViewModelProvider(this, CattleViewModelFactory((application as GokulaApp).repository))[CattleViewModel::class.java]

        val vaccines = listOf("FMD", "HS (Hemorrhagic Septicemia)", "BQ (Black Quarter)", "Anthrax", "Brucellosis", "IBR", "Other")
        b.autoVaccine.setAdapter(ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, vaccines))

        b.btnSave.setOnClickListener {
            val vaccine = b.autoVaccine.text.toString().trim()
            val date = b.etScheduledDate.text.toString().trim()
            if (vaccine.isBlank() || date.isBlank()) {
                Toast.makeText(this, "Fill in vaccine name and date", Toast.LENGTH_SHORT).show(); return@setOnClickListener
            }
            val v = Vaccination(cattleId = cattleId, vaccineName = vaccine, scheduledDate = date, notes = b.etNotes.text.toString().trim())
            lifecycleScope.launch {
                val id = (application as GokulaApp).repository.insertVaccination(v)
                VaccinationScheduler.schedule(this@AddVaccinationActivity, v.copy(id = id))
                Toast.makeText(this@AddVaccinationActivity, "💉 $vaccine scheduled for $date. Reminder set!", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }
    override fun onSupportNavigateUp(): Boolean { finish(); return true }
}
