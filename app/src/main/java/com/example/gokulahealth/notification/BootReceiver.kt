package com.example.gokulahealth.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.gokulahealth.GokulaApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Intent.ACTION_BOOT_COMPLETED) return
        val repo = (context.applicationContext as GokulaApp).repository
        CoroutineScope(Dispatchers.IO).launch {
            val today = LocalDate.now().toString()
            val upcoming = repo.getDueBetween(today, LocalDate.now().plusDays(60).toString())
            upcoming.forEach { VaccinationScheduler.schedule(context, it) }
        }
    }
}
