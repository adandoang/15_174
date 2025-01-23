package com.example.monitoring_hewan.viewmodel.hewanvm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.monitoring_hewan.model.Hewan
import com.example.monitoring_hewan.repository.HewanRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class HomeUiState{
    data class Success(val hewan: List<Hewan>): HomeUiState()
    object Error: HomeUiState()
    object Loading: HomeUiState()
}

class HomeHewanViewModel (private val hwn: HewanRepository):ViewModel() {
    var hwnUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getHwn()
    }

    fun getHwn(){
        viewModelScope.launch {
            hwnUIState= HomeUiState.Loading
            hwnUIState=try {
                HomeUiState.Success(hwn.getHewan().data)
            }catch (e:IOException){
                HomeUiState.Error
            }catch (e:HttpException){
                HomeUiState.Error
            }
        }
    }

    fun deleteHwn(id_hewan:String) {
        viewModelScope.launch {
            try {
                hwn.deleteHewan(id_hewan)
            }catch (e:IOException){
                HomeUiState.Error
            }catch (e:HttpException){
                HomeUiState.Error
            }
        }
    }
}