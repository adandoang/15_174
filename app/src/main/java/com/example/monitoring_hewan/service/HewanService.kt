package com.example.monitoring_hewan.service

import com.example.monitoring_hewan.model.AllHewanResponse
import com.example.monitoring_hewan.model.Hewan
import com.example.monitoring_hewan.model.HewanDetailResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface HewanService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @GET(".")
    suspend fun getAllHewan(): AllHewanResponse

    @GET("{id_hewan}")
    suspend fun getHewanbyId(@Path("id_hewan")id_hewan: String): HewanDetailResponse

    @POST("store")
    suspend fun insertHewan(@Body hewan: Hewan)

    @PUT("{id_hewan}")
    suspend fun updateHewan(@Path("id_hewan")id_hewan: String, @Body hewan: Hewan)

    @DELETE("{id_hewan}")
    suspend fun deleteHewan(@Path("id_hewan")id_hewan: String):retrofit2.Response<Void>
}