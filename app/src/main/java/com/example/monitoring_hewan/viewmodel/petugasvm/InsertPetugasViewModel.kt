package com.example.monitoring_hewan.viewmodel.petugasvm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monitoring_hewan.model.Petugas
import com.example.monitoring_hewan.repository.PetugasRepository
import kotlinx.coroutines.launch

class InsertPetugasViewModel (private val ptgs: PetugasRepository): ViewModel() {
    var uiState by mutableStateOf(InsertUiState())
        private set

    fun updateInsertPtgsState(insertUiEvent:InsertUiEvent) {
        uiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun insertPtgs() {
        viewModelScope.launch{
            try {
                ptgs.insertPetugas(uiState.insertUiEvent.toPtgs())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}

data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent()
)

data class InsertUiEvent(
    val id_petugas: String="",
    val nama_petugas: String="",
    val jabatan: String="",
)

fun InsertUiEvent.toPtgs(): Petugas = Petugas(
    id_petugas = id_petugas,
    nama_petugas = nama_petugas,
    jabatan = jabatan,
)

fun Petugas.toUiStatePtgs(): InsertUiState = InsertUiState(
    insertUiEvent = toInsertUiEvent()
)

fun Petugas.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    id_petugas = id_petugas,
    nama_petugas = nama_petugas,
    jabatan = jabatan
)