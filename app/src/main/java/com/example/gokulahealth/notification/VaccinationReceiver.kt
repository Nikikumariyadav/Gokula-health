package com.example.gokulahealth.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class VaccinationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val vaccineName = intent.getStringExtra("vaccine_name") ?: "Vaccination"
        val scheduledDate = intent.getStringExtra("scheduled_date") ?: ""
        val id = intent.getLongExtra("vaccination_id", 0L)
        NotificationHelper.showVaccinationReminder(context, id.toInt(), vaccineName, scheduledDate)
    }
}
