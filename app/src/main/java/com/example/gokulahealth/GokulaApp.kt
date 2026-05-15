package com.example.gokulahealth

import android.app.Application
import com.example.gokulahealth.data.AppDatabase
import com.example.gokulahealth.notification.NotificationHelper
import com.example.gokulahealth.repository.CattleRepository

class GokulaApp : Application() {
    val database by lazy { AppDatabase.getInstance(this) }
    val repository by lazy {
        CattleRepository(database.cattleDao(), database.milkDao(), database.vaccinationDao())
    }

    override fun onCreate() {
        super.onCreate()
        NotificationHelper.createChannels(this)
    }
}
