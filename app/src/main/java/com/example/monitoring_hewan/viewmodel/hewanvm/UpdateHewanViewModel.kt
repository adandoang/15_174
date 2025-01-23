package com.example.monitoring_hewan.viewmodel.hewanvm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monitoring_hewan.repository.HewanRepository
import com.example.monitoring_hewan.view.hewanview.DestinasiUpdate
import kotlinx.coroutines.launch

class UpdateHewanViewModel (
    savedStateHandle: SavedStateHandle,
    private val hwn: HewanRepository
): ViewModel(){
    var UpdateUiState by mutableStateOf(InsertUiState())
        private set

    private val _id_hewan: String = checkNotNull(savedStateHandle[DestinasiUpdate.ID_HEWAN])

    init {
        viewModelScope.launch {
            UpdateUiState = hwn.getHewanByid_hewan(_id_hewan)
                .toUiStateHwn()
        }
    }

    fun updateInsertHwnState(insertUiEvent: InsertUiEvent){
        UpdateUiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun updateHwn(){
        viewModelScope.launch {
            try {
                hwn.updateHewan(_id_hewan, UpdateUiState.insertUiEvent.toHwn())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}