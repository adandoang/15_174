package com.example.monitoring_hewan.model

import kotlinx.serialization.Serializable

@Serializable
data class Petugas (
    val id_petugas: String,
    val nama_petugas: String,
    val jabatan: String,
)

@Serializable
data class AllPetugasResponse(
    val status: Boolean,
    val message: String,
    val data:List<Kandang>
)

@Serializable
data class PetugasDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Kandang
)