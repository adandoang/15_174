package com.example.monitoring_hewan.viewmodel.petugasvm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monitoring_hewan.model.Petugas
import com.example.monitoring_hewan.repository.PetugasRepository
import com.example.monitoring_hewan.view.petugasview.DestinasiDetailPetugas
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class DetailUiState {
    data class Success(val petugas: Petugas) : DetailUiState()
    object Error : DetailUiState()
    object Loading : DetailUiState()
}

class DetailPetugasViewModel(
    savedStateHandle: SavedStateHandle,
    private val ptgs: PetugasRepository
) : ViewModel() {

    var petugasDetailState: DetailUiState by mutableStateOf(DetailUiState.Loading)
        private set

    private val _id_petugas: String = checkNotNull(savedStateHandle[DestinasiDetailPetugas.ID_PETUGAS])

    init {
        getPetugasbyId_petugas()
    }

    fun getPetugasbyId_petugas() {
        viewModelScope.launch {
            petugasDetailState = DetailUiState.Loading
            petugasDetailState = try {
                val petugas = ptgs.getPetugasByid_petugas(_id_petugas)
                DetailUiState.Success(petugas)
            } catch (e: IOException) {
                DetailUiState.Error
            } catch (e: HttpException) {
                DetailUiState.Error
            }
        }
    }

    fun deletePtgs(id_petugas:String) {
        viewModelScope.launch {
            try {
                ptgs.deletePetugas(id_petugas)
            }catch (e:IOException){
                HomeUiState.Error
            }catch (e: HttpException){
                HomeUiState.Error
            }
        }
    }
}