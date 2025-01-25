package com.example.monitoring_hewan.viewmodel.monitoringvm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monitoring_hewan.model.Monitoring
import com.example.monitoring_hewan.repository.MonitoringRepository
import com.example.monitoring_hewan.view.MonitoringView.DestinasiDetailMonitoring
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class DetailUiState {
    data class Success(val monitoring: Monitoring) : DetailUiState()
    object Error : DetailUiState()
    object Loading : DetailUiState()
}

class DetailMonitoringViewModel(
    savedStateHandle: SavedStateHandle,
    private val mtr: MonitoringRepository
) : ViewModel() {

    var monitoringDetailState: DetailUiState by mutableStateOf(DetailUiState.Loading)
        private set

    private val _id_monitoring: String = checkNotNull(savedStateHandle[DestinasiDetailMonitoring.ID_MONITORING])

    init {
        getMonitoringbyId_monitoring()
    }

    fun getMonitoringbyId_monitoring() {
        viewModelScope.launch {
            monitoringDetailState = DetailUiState.Loading
            monitoringDetailState = try {
                val monitoring = mtr.getMonitoringByid_monitoring(_id_monitoring)
                DetailUiState.Success(monitoring)
            } catch (e: IOException) {
                DetailUiState.Error
            } catch (e: HttpException) {
                DetailUiState.Error
            }
        }
    }

    fun deleteMtr(id_monitoring:String) {
        viewModelScope.launch {
            try {
                mtr.deleteMonitoring(id_monitoring)
            }catch (e:IOException){
                HomeUiState.Error
            }catch (e: HttpException){
                HomeUiState.Error
            }
        }
    }
}