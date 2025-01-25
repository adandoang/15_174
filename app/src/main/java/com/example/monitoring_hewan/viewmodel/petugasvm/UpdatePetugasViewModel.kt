package com.example.monitoring_hewan.viewmodel.petugasvm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monitoring_hewan.repository.PetugasRepository
import com.example.monitoring_hewan.view.petugasview.DestinasiUpdatePetugas
import kotlinx.coroutines.launch

class UpdatePetugasViewModel (
    savedStateHandle: SavedStateHandle,
    private val ptgs: PetugasRepository
): ViewModel(){
    var UpdateUiState by mutableStateOf(InsertUiState())
        private set

    private val _id_petugas: String = checkNotNull(savedStateHandle[DestinasiUpdatePetugas.ID_PETUGAS])

    init {
        viewModelScope.launch {
            UpdateUiState = ptgs.getPetugasByid_petugas(_id_petugas)
                .toUiStatePtgs()
        }
    }

    fun updateInsertPtgsState(insertUiEvent: InsertUiEvent){
        UpdateUiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun updatePtgs(){
        viewModelScope.launch {
            try {
                ptgs.updatePetugas(_id_petugas, UpdateUiState.insertUiEvent.toPtgs())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}