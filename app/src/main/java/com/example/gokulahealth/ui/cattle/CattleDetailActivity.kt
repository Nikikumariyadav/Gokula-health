package com.example.gokulahealth.ui.cattle

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.gokulahealth.GokulaApp
import com.example.gokulahealth.R
import com.example.gokulahealth.databinding.ActivityCattleDetailBinding
import com.example.gokulahealth.ui.milk.AddMilkActivity
import com.example.gokulahealth.ui.vaccination.AddVaccinationActivity
import com.example.gokulahealth.ui.vaccination.VaccinationAdapter
import com.example.gokulahealth.viewmodel.CattleViewModel
import com.example.gokulahealth.viewmodel.CattleViewModelFactory
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlinx.coroutines.launch

class CattleDetailActivity : AppCompatActivity() {
    private lateinit var b: ActivityCattleDetailBinding
    private lateinit var vm: CattleViewModel
    private var cattleId = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityCattleDetailBinding.inflate(layoutInflater)
        setContentView(b.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        cattleId = intent.getLongExtra("cattle_id", -1L)
        if (cattleId == -1L) { finish(); return }

        vm = ViewModelProvider(this, CattleViewModelFactory((application as GokulaApp).repository))[CattleViewModel::class.java]

        lifecycleScope.launch {
            val cattle = (application as GokulaApp).repository.getCattleById(cattleId) ?: return@launch
            supportActionBar?.title = cattle.name
            b.tvEarTag.text = "🏷️ Ear Tag: ${cattle.earTag}"
            b.tvBreed.text = "🐄 Breed: ${cattle.breed}"
            b.tvDob.text = "📅 DOB: ${cattle.dateOfBirth}"
            Glide.with(this@CattleDetailActivity)
                .load(if (cattle.photoUri.isNotBlank()) cattle.photoUri else R.drawable.ic_cow_placeholder)
                .centerCrop().into(b.ivCattlePhoto)
            val avg = vm.getMonthlyAverage(cattleId)
            b.tvMonthlyAvg.text = String.format("📊 Monthly Avg: %.1f L/day", avg ?: 0.0)
        }

        // Chart
        vm.getMilkForCattle(cattleId).observe(this) { records ->
            if (records.isEmpty()) { b.milkChart.visibility = View.GONE; b.tvNoChartData.visibility = View.VISIBLE; return@observe }
            b.milkChart.visibility = View.VISIBLE; b.tvNoChartData.visibility = View.GONE
            val sorted = records.sortedBy { it.date }.takeLast(30)
            val entries = sorted.mapIndexed { i, r -> BarEntry(i.toFloat(), r.totalYield.toFloat()) }
            val labels = sorted.map { it.date.substring(5) }
            val ds = BarDataSet(entries, "Daily Yield (L)").apply { color = getColor(R.color.green_primary); valueTextColor = getColor(R.color.white) }
            b.milkChart.apply {
                data = BarData(ds).apply { barWidth = 0.7f }
                description.isEnabled = false; legend.textColor = getColor(R.color.white); setFitBars(true)
                xAxis.apply { valueFormatter = IndexAxisValueFormatter(labels); position = XAxis.XAxisPosition.BOTTOM; granularity = 1f; setDrawGridLines(false); textColor = getColor(R.color.white); labelRotationAngle = -45f }
                axisLeft.textColor = getColor(R.color.white); axisRight.isEnabled = false
                setBackgroundColor(getColor(R.color.chart_bg)); animateY(800); invalidate()
            }
        }

        val vaccAdapter = VaccinationAdapter(onCompleteClick = { vm.markCompleted(it.id) }, onDeleteClick = { vm.deleteVaccination(it) })
        b.recyclerVaccinations.layoutManager = LinearLayoutManager(this)
        b.recyclerVaccinations.adapter = vaccAdapter
        vm.getVaccinationsForCattle(cattleId).observe(this) { vaccAdapter.submitList(it) }

        b.fabAddMilk.setOnClickListener { startActivity(Intent(this, AddMilkActivity::class.java).putExtra("cattle_id", cattleId)) }
        b.fabAddVaccination.setOnClickListener { startActivity(Intent(this, AddVaccinationActivity::class.java).putExtra("cattle_id", cattleId)) }
    }

    override fun onSupportNavigateUp(): Boolean { finish(); return true }
}
