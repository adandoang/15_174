package com.example.monitoring_hewan.repository

import com.example.monitoring_hewan.model.AllPetugasResponse
import com.example.monitoring_hewan.model.Petugas
import com.example.monitoring_hewan.service.PetugasService
import okio.IOException

interface PetugasRepository {
    suspend fun getPetugas(): AllPetugasResponse
    suspend fun insertPetugas(petugas: Petugas)
    suspend fun updatePetugas(id_petugas: String, petugas: Petugas)
    suspend fun deletePetugas(id_petugas: String)
    suspend fun getPetugasByid_petugas(id_petugas: String): Petugas
}

class NetworkPetugasRepository(
    private val petugasApiService: PetugasService
) : PetugasRepository {

    override suspend fun insertPetugas(petugas: Petugas) {
        petugasApiService.insertPetugas(petugas)
    }

    override suspend fun updatePetugas(id_petugas: String, petugas: Petugas) {
        petugasApiService.updatePetugas(id_petugas, petugas)
    }

    override suspend fun deletePetugas(id_petugas: String) {
        try {
            val response = petugasApiService.deletePetugas(id_petugas)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete Petugas. HTTP Status code: " +
                        "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e:Exception) {
            throw e
        }
    }

    override suspend fun getPetugas(): AllPetugasResponse =
        petugasApiService.getAllPetugas()

    override suspend fun getPetugasByid_petugas(id_petugas: String): Petugas{
        return petugasApiService.getPetugasbyId_petugas(id_petugas).data
    }

}