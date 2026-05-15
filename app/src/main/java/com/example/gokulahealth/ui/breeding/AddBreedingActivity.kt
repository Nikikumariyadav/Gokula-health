package com.example.gokulahealth.ui.breeding

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.gokulahealth.GokulaApp
import com.example.gokulahealth.data.BreedingRecord
import com.example.gokulahealth.databinding.ActivityAddBreedingBinding
import com.example.gokulahealth.viewmodel.CattleViewModel
import com.example.gokulahealth.viewmodel.CattleViewModelFactory
import kotlinx.coroutines.launch
import java.time.LocalDate

class AddBreedingActivity : AppCompatActivity() {
    private lateinit var b: ActivityAddBreedingBinding
    private lateinit var vm: CattleViewModel
    private var cattleId = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityAddBreedingBinding.inflate(layoutInflater)
        setContentView(b.root)
        supportActionBar?.title = "🐄 Record Breeding"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        cattleId = intent.getLongExtra("cattle_id", -1L)
        if (cattleId == -1L) { finish(); return }
        vm = ViewModelProvider(this, CattleViewModelFactory((application as GokulaApp).repository))[CattleViewModel::class.java]

        val methods = listOf("Artificial Insemination (AI)", "Natural Mating")
        b.autoMethod.setAdapter(ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, methods))
        b.etHeatDate.setText(LocalDate.now().toString())
        b.etBreedingDate.setText(LocalDate.now().toString())

        b.btnSave.setOnClickListener {
            val heatDate = b.etHeatDate.text.toString().trim()
            val breedingDate = b.etBreedingDate.text.toString().trim()
            val method = b.autoMethod.text.toString().trim()
            if (heatDate.isBlank() || breedingDate.isBlank() || method.isBlank()) {
                Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Auto-calculate expected calving date (~283 days from breeding)
            val expectedCalving = try {
                LocalDate.parse(breedingDate).plusDays(283).toString()
            } catch (e: Exception) { "" }

            val record = BreedingRecord(
                cattleId = cattleId,
                heatDate = heatDate,
                breedingDate = breedingDate,
                method = method,
                bullOrSemenId = b.etBullId.text.toString().trim(),
                expectedCalvingDate = expectedCalving,
                notes = b.etNotes.text.toString().trim()
            )
            lifecycleScope.launch {
                (application as GokulaApp).repository.insertBreeding(record)
                Toast.makeText(
                    this@AddBreedingActivity,
                    "✅ Breeding recorded!\n📅 Expected calving: $expectedCalving",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
        }
    }
    override fun onSupportNavigateUp(): Boolean { finish(); return true }
}
