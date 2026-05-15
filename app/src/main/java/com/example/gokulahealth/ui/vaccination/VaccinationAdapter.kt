package com.example.gokulahealth.ui.vaccination

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gokulahealth.R
import com.example.gokulahealth.data.Vaccination
import com.example.gokulahealth.databinding.ItemVaccinationBinding
import java.time.LocalDate

class VaccinationAdapter(
    private val onCompleteClick: (Vaccination) -> Unit,
    private val onDeleteClick: (Vaccination) -> Unit
) : ListAdapter<Vaccination, VaccinationAdapter.VH>(DIFF) {

    inner class VH(val b: ItemVaccinationBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ItemVaccinationBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: VH, pos: Int) {
        val v = getItem(pos)
        with(holder.b) {
            tvVaccineName.text = "💉 ${v.vaccineName}"
            tvScheduledDate.text = "📅 ${v.scheduledDate}"
            tvNotes.text = v.notes
            val today = LocalDate.now()
            val due = LocalDate.parse(v.scheduledDate)
            when {
                v.isCompleted -> { tvStatus.text = "✅ Completed"; tvStatus.setTextColor(ContextCompat.getColor(root.context, R.color.green_primary)); btnComplete.isEnabled = false }
                due.isBefore(today) -> { tvStatus.text = "⚠️ Overdue!"; tvStatus.setTextColor(ContextCompat.getColor(root.context, R.color.red_error)) }
                due.isBefore(today.plusDays(3)) -> { tvStatus.text = "🔔 Due Soon"; tvStatus.setTextColor(ContextCompat.getColor(root.context, R.color.orange_warn)) }
                else -> { tvStatus.text = "🗓️ Upcoming"; tvStatus.setTextColor(ContextCompat.getColor(root.context, R.color.text_secondary)) }
            }
            btnComplete.setOnClickListener { onCompleteClick(v) }
            btnDelete.setOnClickListener { onDeleteClick(v) }
        }
    }

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<Vaccination>() {
            override fun areItemsTheSame(a: Vaccination, b: Vaccination) = a.id == b.id
            override fun areContentsTheSame(a: Vaccination, b: Vaccination) = a == b
        }
    }
}
