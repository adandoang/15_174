package com.example.monitoring_hewan.viewmodel.hewanvm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monitoring_hewan.model.Hewan
import com.example.monitoring_hewan.repository.HewanRepository
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed class HomeUiState{
    data class Success(val hewan: List<Hewan>): HomeUiState()
    object Error: HomeUiState()
    object Loading: HomeUiState()
}

class HomeHewanViewModel (val hwn: HewanRepository):ViewModel() {
    var hwnUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)


    init {
        getHwn()
    }

    fun searchHwn(query: String) {
        viewModelScope.launch {
            hwnUIState = HomeUiState.Loading
            hwnUIState = try {
                val filteredHewan = hwn.getHewan().data.filter {
                    it.nama_hewan.contains(query, ignoreCase = true)
                }
                HomeUiState.Success(filteredHewan)
            } catch (e: java.io.IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }

    fun getHwn(){
        viewModelScope.launch {
            hwnUIState= HomeUiState.Loading
            hwnUIState=try {
                HomeUiState.Success(hwn.getHewan().data)
            }catch (e:IOException){
                HomeUiState.Error
            }catch (e: HttpException){
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