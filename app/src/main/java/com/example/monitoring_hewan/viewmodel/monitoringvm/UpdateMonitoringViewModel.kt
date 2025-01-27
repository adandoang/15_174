package com.example.monitoring_hewan.viewmodel.monitoringvm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monitoring_hewan.model.Hewan
import com.example.monitoring_hewan.model.Kandang
import com.example.monitoring_hewan.model.Petugas
import com.example.monitoring_hewan.repository.KandangRepository
import com.example.monitoring_hewan.repository.MonitoringRepository
import com.example.monitoring_hewan.repository.PetugasRepository
import com.example.monitoring_hewan.view.MonitoringView.DestinasiUpdateMonitoring
import kotlinx.coroutines.launch

class UpdateMonitoringViewModel (
    savedStateHandle: SavedStateHandle,
    private val mtr: MonitoringRepository,
    private val kdg: KandangRepository,
    private val ptgs: PetugasRepository
): ViewModel(){
    var UpdateUiState by mutableStateOf(InsertUiState())
        private set
    var kdglist by mutableStateOf<List<Kandang>>(listOf())
    var petugasList by mutableStateOf<List<Petugas>>(listOf())

    var dokterHewanPetugas by mutableStateOf<List<Petugas>>(listOf())

    fun getDokterHewanPetugas() {
        dokterHewanPetugas = petugasList.filter { it.jabatan == "Dokter Hewan" }
    }

    private val _id_monitoring: String = checkNotNull(savedStateHandle[DestinasiUpdateMonitoring.ID_MONITORING])

    init {
        viewModelScope.launch {
            UpdateUiState = mtr.getMonitoringByid_monitoring(_id_monitoring)
                .toUiStateMtr()
        }
    }

    fun getKandang() {
        viewModelScope.launch {
            try {
                val kdgdata = kdg.getKandang()
                kdglist = kdgdata.data
            }catch (e: Exception) {

            }
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