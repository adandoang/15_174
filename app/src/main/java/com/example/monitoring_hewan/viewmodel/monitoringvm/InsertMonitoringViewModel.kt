package com.example.monitoring_hewan.viewmodel.monitoringvm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monitoring_hewan.model.Monitoring
import com.example.monitoring_hewan.repository.MonitoringRepository
import kotlinx.coroutines.launch
import kotlin.math.tan

class InsertMonitoringViewModel (private val mtr: MonitoringRepository): ViewModel() {
    var uiState by mutableStateOf(InsertUiState())
        private set

    fun updateInsertMtrState(insertUiEvent:InsertUiEvent) {
        uiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun insertMtr() {
        viewModelScope.launch{
            try {
                mtr.insertMonitoring(uiState.insertUiEvent.toMtr())
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
    val id_monitoring: String="",
    val id_petugas: String="",
    val id_kandang: String="",
    val tanggal_monitoring: String="",
    val hewan_sakit: Int=0,
    val hewan_sehat: Int=0,
    val status: String="",
)

fun InsertUiEvent.toMtr(): Monitoring = Monitoring(
    id_monitoring = id_monitoring,
    id_petugas = id_petugas,
    id_kandang = id_kandang,
    tanggal_monitoring = tanggal_monitoring,
    hewan_sakit = hewan_sakit,
    hewan_sehat = hewan_sehat,
    status = status,
)

fun Monitoring.toUiStateMtr(): InsertUiState = InsertUiState(
    insertUiEvent = toInsertUiEvent()
)

fun Monitoring.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    id_monitoring = id_monitoring,
    id_petugas = id_petugas,
    id_kandang = id_kandang,
    tanggal_monitoring = tanggal_monitoring,
    hewan_sakit = hewan_sakit,
    hewan_sehat = hewan_sehat,
    status = status,
)