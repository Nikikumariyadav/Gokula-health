package com.example.gokulahealth.ui.milk

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.gokulahealth.GokulaApp
import com.example.gokulahealth.data.MilkRecord
import com.example.gokulahealth.databinding.ActivityAddMilkBinding
import com.example.gokulahealth.viewmodel.CattleViewModel
import com.example.gokulahealth.viewmodel.CattleViewModelFactory
import kotlinx.coroutines.launch
import java.time.LocalDate

class AddMilkActivity : AppCompatActivity() {
    private lateinit var b: ActivityAddMilkBinding
    private lateinit var vm: CattleViewModel
    private var cattleId = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityAddMilkBinding.inflate(layoutInflater)
        setContentView(b.root)
        supportActionBar?.title = "Add Milk Record"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        cattleId = intent.getLongExtra("cattle_id", -1L)
        if (cattleId == -1L) { finish(); return }
        vm = ViewModelProvider(this, CattleViewModelFactory((application as GokulaApp).repository))[CattleViewModel::class.java]
        b.etDate.setText(LocalDate.now().toString())

        b.btnSave.setOnClickListener {
            val date = b.etDate.text.toString().trim()
            val morning = b.etMorning.text.toString().toDoubleOrNull()
            val evening = b.etEvening.text.toString().toDoubleOrNull()
            if (morning == null || evening == null) {
                Toast.makeText(this, "Enter valid yield values", Toast.LENGTH_SHORT).show(); return@setOnClickListener
            }
            lifecycleScope.launch {
                val existing = (application as GokulaApp).repository.getMilkByDate(cattleId, date)
                if (existing != null) {
                    vm.updateMilk(existing.copy(morningYield = morning, eveningYield = evening, notes = b.etNotes.text.toString().trim()))
                    Toast.makeText(this@AddMilkActivity, "Record updated for $date", Toast.LENGTH_SHORT).show()
                } else {
                    vm.insertMilk(MilkRecord(cattleId = cattleId, date = date, morningYield = morning, eveningYield = evening, notes = b.etNotes.text.toString().trim()))
                    Toast.makeText(this@AddMilkActivity, "🥛 Total: ${morning + evening}L saved!", Toast.LENGTH_SHORT).show()
                }
                finish()
            }
        }
    }
    override fun onSupportNavigateUp(): Boolean { finish(); return true }
}
