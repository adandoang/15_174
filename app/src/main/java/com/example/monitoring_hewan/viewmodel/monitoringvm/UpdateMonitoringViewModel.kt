package com.example.monitoring_hewan.viewmodel.monitoringvm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monitoring_hewan.repository.MonitoringRepository
import com.example.monitoring_hewan.view.MonitoringView.DestinasiUpdateMonitoring
import kotlinx.coroutines.launch

class UpdateMonitoringViewModel (
    savedStateHandle: SavedStateHandle,
    private val mtr: MonitoringRepository
): ViewModel(){
    var UpdateUiState by mutableStateOf(InsertUiState())
        private set

    private val _id_monitoring: String = checkNotNull(savedStateHandle[DestinasiUpdateMonitoring.ID_MONITORING])

    init {
        viewModelScope.launch {
            UpdateUiState = mtr.getMonitoringByid_monitoring(_id_monitoring)
                .toUiStateMtr()
        }
    }

    fun updateInsertMtrState(insertUiEvent: InsertUiEvent){
        UpdateUiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun updateMtr(){
        viewModelScope.launch {
            try {
                mtr.updateMonitoring(_id_monitoring, UpdateUiState.insertUiEvent.toMtr())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}