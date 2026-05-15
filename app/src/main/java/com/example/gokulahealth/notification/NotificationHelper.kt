package com.example.gokulahealth.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.gokulahealth.R

object NotificationHelper {
    const val CHANNEL_VACCINATION = "vaccination_reminders"

    fun createChannels(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val nm = context.getSystemService(NotificationManager::class.java)
            nm.createNotificationChannel(
                NotificationChannel(CHANNEL_VACCINATION, "Vaccination Reminders", NotificationManager.IMPORTANCE_HIGH)
                    .apply { description = "Alerts for upcoming cattle vaccinations" }
            )
        }
    }

    fun showVaccinationReminder(context: Context, notificationId: Int, vaccineName: String, scheduledDate: String) {
        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(context, CHANNEL_VACCINATION)
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setContentTitle("💉 Vaccination Due Tomorrow!")
            .setContentText("$vaccineName is scheduled for $scheduledDate")
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("Your cattle's $vaccineName vaccination is scheduled for $scheduledDate. Please prepare accordingly."))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()
        nm.notify(notificationId, notification)
    }
}
