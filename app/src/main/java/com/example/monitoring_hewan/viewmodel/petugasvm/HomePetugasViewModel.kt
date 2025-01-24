package com.example.monitoring_hewan.viewmodel.petugasvm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monitoring_hewan.model.Petugas
import com.example.monitoring_hewan.repository.PetugasRepository
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed class HomeUiState{
    data class Success(val petugas: List<Petugas>): HomeUiState()
    object Error: HomeUiState()
    object Loading: HomeUiState()
}

class HomePetugasViewModel (private val ptgs: PetugasRepository):ViewModel() {
    var ptgsUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getPtgs()
    }

    fun getPtgs(){
        viewModelScope.launch {
            ptgsUIState= HomeUiState.Loading
            ptgsUIState=try {
                HomeUiState.Success(ptgs.getPetugas().data)
            }catch (e:IOException){
                HomeUiState.Error
            }catch (e: HttpException){
                HomeUiState.Error
            }
        }
    }

    fun deletePtgs(id_petugas:String) {
        viewModelScope.launch {
            try {
                ptgs.deletePetugas(id_petugas)
            }catch (e:IOException){
                HomeUiState.Error
            }catch (e:HttpException){
                HomeUiState.Error
            }
        }
    }
}