package com.example.monitoring_hewan.model

import kotlinx.serialization.Serializable

@Serializable
data class Kandang (
    val id_kandang: String,
    val id_hewan: String,
    val kapasitas: Int,
    val lokasi: String,
)

@Serializable
data class AllKandangResponse(
    val status: Boolean,
    val message: String,
    val data:List<Kandang>
)

@Serializable
data class KandangDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Kandang
)