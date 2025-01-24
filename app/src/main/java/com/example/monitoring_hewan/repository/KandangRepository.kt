package com.example.monitoring_hewan.repository

import com.example.monitoring_hewan.model.AllKandangResponse
import com.example.monitoring_hewan.model.Kandang
import com.example.monitoring_hewan.service.KandangService
import okio.IOException

interface KandangRepository {
    suspend fun getKandang(): AllKandangResponse
    suspend fun insertKandang(kandang: Kandang)
    suspend fun updateKandang(id_kandang: String, kandang: Kandang)
    suspend fun deleteKandang(id_kandang: String)
    suspend fun getKandangByid_kandang(id_kandang: String): Kandang
}

class NetworkKandangRepository(
    private val kandangApiService: KandangService
) : KandangRepository {

    override suspend fun insertKandang(kandang: Kandang) {
        kandangApiService.insertKandang(kandang)
    }

    override suspend fun updateKandang(id_kandang: String, kandang: Kandang) {
        kandangApiService.updateKandang(id_kandang, kandang)
    }

    override suspend fun deleteKandang(id_kandang: String) {
        try {
            val response = kandangApiService.deleteKandang(id_kandang)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete Kandang. HTTP Status code: " +
                        "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e:Exception) {
            throw e
        }
    }

    override suspend fun getKandang(): AllKandangResponse =
        kandangApiService.getAllKandang()

    override suspend fun getKandangByid_kandang(id_kandang: String): Kandang{
        return kandangApiService.getKandangbyId_kandang(id_kandang).data
    }

}