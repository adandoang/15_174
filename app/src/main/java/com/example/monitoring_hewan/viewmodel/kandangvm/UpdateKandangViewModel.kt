package com.example.monitoring_hewan.viewmodel.kandangvm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monitoring_hewan.repository.KandangRepository
import com.example.monitoring_hewan.view.kandangview.DestinasiUpdateKandang
import kotlinx.coroutines.launch

class UpdateKandangViewModel (
    savedStateHandle: SavedStateHandle,
    private val kdg: KandangRepository
): ViewModel(){
    var UpdateUiState by mutableStateOf(InsertUiState())
        private set

    private val _id_kandang: String = checkNotNull(savedStateHandle[DestinasiUpdateKandang.ID_KANDANG])

    init {
        viewModelScope.launch {
            UpdateUiState = kdg.getKandangByid_kandang(_id_kandang)
                .toUiStateKdg()
        }
    }

    fun updateInsertKdgState(insertUiEvent: InsertUiEvent){
        UpdateUiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun updateKdg(){
        viewModelScope.launch {
            try {
                kdg.updateKandang(_id_kandang, UpdateUiState.insertUiEvent.toKdg())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}