package com.example.monitoring_hewan.model

import kotlinx.serialization.Serializable

@Serializable
data class Monitoring (
    val id_monitoring: String,
    val id_petugas: String,
    val id_kandang: String,
    val tanggal_monitoring: String,
    val hewan_sakit: Int,
    val hewan_sehat: Int,
    val status: String,
)

@Serializable
data class AllMonitoringResponse(
    val status: Boolean,
    val message: String,
    val data:List<Monitoring>
)

@Serializable
data class MonitoringDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Monitoring
)