package com.example.monitoring_hewan.repository

import com.example.monitoring_hewan.model.AllMonitoringResponse
import com.example.monitoring_hewan.model.Monitoring
import com.example.monitoring_hewan.service.MonitoringService
import okio.IOException

interface MonitoringRepository {
    suspend fun getMonitoring(): AllMonitoringResponse
    suspend fun insertMonitoring(monitoring: Monitoring)
    suspend fun updateMonitoring(id_monitoring: String, monitoring: Monitoring)
    suspend fun deleteMonitoring(id_monitoring: String)
    suspend fun getMonitoringByid_monitoring(id_monitoring: String): Monitoring
}

class NetworkMonitoringRepository(
    private val monitoringApiService: MonitoringService
) : MonitoringRepository {

    override suspend fun insertMonitoring(monitoring: Monitoring) {
        monitoringApiService.insertMonitoring(monitoring)
    }

    override suspend fun updateMonitoring(id_monitoring: String, monitoring: Monitoring) {
        monitoringApiService.updateMonitoring(id_monitoring, monitoring)
    }

    override suspend fun deleteMonitoring(id_monitoring: String) {
        try {
            val response = monitoringApiService.deleteMonitoring(id_monitoring)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete Monitoring. HTTP Status code: " +
                        "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e:Exception) {
            throw e
        }
    }

    override suspend fun getMonitoring(): AllMonitoringResponse =
        monitoringApiService.getAllMonitoring()

    override suspend fun getMonitoringByid_monitoring(id_monitoring: String): Monitoring{
        return monitoringApiService.getMonitoringbyId_monitoring(id_monitoring).data
    }

}