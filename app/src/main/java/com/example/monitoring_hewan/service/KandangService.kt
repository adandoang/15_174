package com.example.monitoring_hewan.service

import com.example.monitoring_hewan.model.AllKandangResponse
import com.example.monitoring_hewan.model.Kandang
import com.example.monitoring_hewan.model.KandangDetailResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface KandangService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @GET("kandang/")
    suspend fun getAllKandang(): AllKandangResponse

    @GET("kandang/{id_kandang}")
    suspend fun getKandangbyId_kandang(@Path("id_kandang") id_kandang: String): KandangDetailResponse

    @POST("kandang/store")
    suspend fun insertKandang(@Body kandang: Kandang)

    @PUT("kandang/{id_kandang}")
    suspend fun updateKandang(@Path("id_kandang") id_kandang: String, @Body kandang: Kandang)

    @DELETE("kandang/{id_kandang}")
    suspend fun deleteKandang(@Path("id_kandang") id_kandang: String): retrofit2.Response<Void>
}
