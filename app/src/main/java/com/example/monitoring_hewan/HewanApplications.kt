package com.example.monitoring_hewan

import android.app.Application
import com.example.monitoring_hewan.dependenciesinjection.AppContainer
import com.example.monitoring_hewan.dependenciesinjection.HewanContainer

class HewanApplications: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container= HewanContainer()
    }
}