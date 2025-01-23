package com.example.monitoring_hewan.repository

import com.example.monitoring_hewan.model.AllHewanResponse
import com.example.monitoring_hewan.model.Hewan
import com.example.monitoring_hewan.service.HewanService
import okio.IOException

interface HewanRepository {
    suspend fun getHewan(): AllHewanResponse
    suspend fun insertHewan(hewan: Hewan)
    suspend fun updateHewan(id_hewan: String, hewan: Hewan)
    suspend fun deleteHewan(id_hewan: String)
    suspend fun getHewanByid_hewan(id_hewan: String): Hewan
}

class NetworkHewanRepository(
    private val hewanApiService: HewanService
) : HewanRepository {


    override suspend fun insertHewan(hewan: Hewan) {
        hewanApiService.insertHewan(hewan)
    }

    override suspend fun updateHewan(id_hewan: String, hewan: Hewan) {
        hewanApiService.updateHewan(id_hewan, hewan)
    }

    override suspend fun deleteHewan(id_hewan: String) {
        try {
            val response = hewanApiService.deleteHewan(id_hewan)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete Hewan. HTTP Status code: " +
                        "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e:Exception) {
            throw e
        }
    }

    override suspend fun getHewan(): AllHewanResponse =
        hewanApiService.getAllHewan()

    override suspend fun getHewanByid_hewan(id_hewan: String): Hewan{
        return hewanApiService.getHewanbyId(id_hewan).data
    }

}