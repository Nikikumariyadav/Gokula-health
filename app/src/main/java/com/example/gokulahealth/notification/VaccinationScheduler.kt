package com.example.gokulahealth.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.gokulahealth.data.Vaccination
import java.time.LocalDate
import java.time.ZoneId

object VaccinationScheduler {
    fun schedule(context: Context, vaccination: Vaccination) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val notifyDate = LocalDate.parse(vaccination.scheduledDate).minusDays(1)
        val triggerMs = notifyDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        if (triggerMs < System.currentTimeMillis()) return

        val intent = Intent(context, VaccinationReceiver::class.java).apply {
            putExtra("vaccination_id", vaccination.id)
            putExtra("vaccine_name", vaccination.vaccineName)
            putExtra("scheduled_date", vaccination.scheduledDate)
        }
        val pi = PendingIntent.getBroadcast(
            context, vaccination.id.toInt(), intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerMs, pi)
        } else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerMs, pi)
        }
    }

    fun cancel(context: Context, vaccinationId: Long) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, VaccinationReceiver::class.java)
        val pi = PendingIntent.getBroadcast(
            context, vaccinationId.toInt(), intent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        )
        pi?.let { alarmManager.cancel(it) }
    }
}
