package com.example.monitoring_hewan.viewmodel.monitoringvm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monitoring_hewan.model.Monitoring
import com.example.monitoring_hewan.repository.MonitoringRepository
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed class HomeUiState{
    data class Success(val monitoring: List<Monitoring>): HomeUiState()
    object Error: HomeUiState()
    object Loading: HomeUiState()
}

class HomeMonitoringViewModel (private val mtr: MonitoringRepository):ViewModel() {
    var mtrUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getMtr()
    }

    fun getMtr(){
        viewModelScope.launch {
            mtrUIState= HomeUiState.Loading
            mtrUIState=try {
                HomeUiState.Success(mtr.getMonitoring().data)
            }catch (e:IOException){
                HomeUiState.Error
            }catch (e: HttpException){
                HomeUiState.Error
            }
        }
    }

    fun deleteMtr(id_monitoring:String) {
        viewModelScope.launch {
            try {
                mtr.deleteMonitoring(id_monitoring)
            }catch (e:IOException){
                HomeUiState.Error
            }catch (e:HttpException){
                HomeUiState.Error
            }
        }
    }
}