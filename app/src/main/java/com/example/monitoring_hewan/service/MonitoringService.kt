package com.example.monitoring_hewan.service

import com.example.monitoring_hewan.model.AllMonitoringResponse
import com.example.monitoring_hewan.model.Monitoring
import com.example.monitoring_hewan.model.MonitoringDetailResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MonitoringService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @GET("monitoring/")
    suspend fun getAllMonitoring(): AllMonitoringResponse

    @GET("monitoring/{id_monitoring}")
    suspend fun getMonitoringbyId_monitoring(@Path("id_monitoring")id_monitoring: String): MonitoringDetailResponse

    @POST("monitoring/store")
    suspend fun insertMonitoring(@Body monitoring: Monitoring)

    @PUT("monitoring/{id_monitoring}")
    suspend fun updateMonitoring(@Path("id_monitoring")id_monitoring: String, @Body monitoring: Monitoring)

    @DELETE("monitoring/{id_monitoring}")
    suspend fun deleteMonitoring(@Path("id_monitoring")id_monitoring: String):retrofit2.Response<Void>
}