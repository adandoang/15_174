package com.example.monitoring_hewan.viewmodel.kandangvm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monitoring_hewan.model.Kandang
import com.example.monitoring_hewan.repository.KandangRepository
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed class HomeUiState{
    data class Success(val kandang: List<Kandang>): HomeUiState()
    object Error: HomeUiState()
    object Loading: HomeUiState()
}

class HomeKandangViewModel (private val kdg: KandangRepository):ViewModel() {
    var kdgUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getKdg()
    }

    fun getKdg(){
        viewModelScope.launch {
            kdgUIState= HomeUiState.Loading
            kdgUIState=try {
                HomeUiState.Success(kdg.getKandang().data)
            }catch (e:IOException){
                HomeUiState.Error
            }catch (e: HttpException){
                HomeUiState.Error
            }
        }
    }

    fun deleteKdg(id_kandang:String) {
        viewModelScope.launch {
            try {
                kdg.deleteKandang(id_kandang)
            }catch (e:IOException){
                HomeUiState.Error
            }catch (e:HttpException){
                HomeUiState.Error
            }
        }
    }
}