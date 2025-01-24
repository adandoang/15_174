package com.example.monitoring_hewan.service

import com.example.monitoring_hewan.model.AllPetugasResponse
import com.example.monitoring_hewan.model.Petugas
import com.example.monitoring_hewan.model.PetugasDetailResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PetugasService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @GET("petugas/")
    suspend fun getAllPetugas(): AllPetugasResponse

    @GET("petugas/{id_petugas}")
    suspend fun getPetugasbyId_petugas(@Path("id_petugas")id_petugas: String): PetugasDetailResponse

    @POST("petugas/store")
    suspend fun insertPetugas(@Body petugas: Petugas)

    @PUT("petugas/{id_petugas}")
    suspend fun updatePetugas(@Path("id_petugas")id_petugas: String, @Body petugas: Petugas)

    @DELETE("petugas/{id_petugas}")
    suspend fun deletePetugas(@Path("id_petugas")id_petugas: String):retrofit2.Response<Void>
}