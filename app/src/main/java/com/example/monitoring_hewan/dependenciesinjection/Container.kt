package com.example.monitoring_hewan.dependenciesinjection

import com.example.monitoring_hewan.repository.HewanRepository
import com.example.monitoring_hewan.repository.KandangRepository
import com.example.monitoring_hewan.repository.MonitoringRepository
import com.example.monitoring_hewan.repository.NetworkHewanRepository
import com.example.monitoring_hewan.repository.NetworkKandangRepository
import com.example.monitoring_hewan.repository.NetworkMonitoringRepository
import com.example.monitoring_hewan.repository.NetworkPetugasRepository
import com.example.monitoring_hewan.repository.PetugasRepository
import com.example.monitoring_hewan.service.HewanService
import com.example.monitoring_hewan.service.KandangService
import com.example.monitoring_hewan.service.MonitoringService
import com.example.monitoring_hewan.service.PetugasService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit


interface AppContainer{
    val hewanRepository: HewanRepository
    val kandangRepository: KandangRepository
    val petugasRepository: PetugasRepository
    val monitoringRepository: MonitoringRepository
}

class Container : AppContainer {
    private val baseUrl = "http://10.0.2.2:3000/api/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val hewanService: HewanService by lazy {
        retrofit.create(HewanService::class.java)
    }
    override val hewanRepository: HewanRepository by lazy {
        NetworkHewanRepository(hewanService)
    }
    private val kandangService: KandangService by lazy {
        retrofit.create(KandangService::class.java)
    }
    override val kandangRepository: KandangRepository by lazy {
        NetworkKandangRepository(kandangService)
    }
    private val petugasService: PetugasService by lazy {
        retrofit.create(PetugasService::class.java)
    }
    override val petugasRepository: PetugasRepository by lazy {
        NetworkPetugasRepository(petugasService)
    }
    private val monitoringService: MonitoringService by lazy {
        retrofit.create(MonitoringService::class.java)
    }
    override val monitoringRepository: MonitoringRepository by lazy {
        NetworkMonitoringRepository(monitoringService)
    }
}