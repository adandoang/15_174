package com.example.monitoring_hewan.application

import android.app.Application
import com.example.monitoring_hewan.dependenciesinjection.AppContainer
import com.example.monitoring_hewan.dependenciesinjection.Container

class Applications: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container= Container()
    }
}