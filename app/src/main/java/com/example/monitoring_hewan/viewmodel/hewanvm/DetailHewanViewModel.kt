package com.example.monitoring_hewan.viewmodel.hewanvm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.monitoring_hewan.model.Hewan
import com.example.monitoring_hewan.repository.HewanRepository
import com.example.monitoring_hewan.view.hewanview.DestinasiDetail
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailUiState {
    data class Success(val hewan: Hewan) : DetailUiState()
    object Error : DetailUiState()
    object Loading : DetailUiState()
}

class DetailHewanViewModel(
    savedStateHandle: SavedStateHandle,
    private val hwn: HewanRepository
) : ViewModel() {

    var hewanDetailState: DetailUiState by mutableStateOf(DetailUiState.Loading)
        private set

    private val _id_hewan: String = checkNotNull(savedStateHandle[DestinasiDetail.ID_HEWAN])

    init {
        getHewanbyId_hewan()
    }

    fun getHewanbyId_hewan() {
        viewModelScope.launch {
            hewanDetailState = DetailUiState.Loading
            hewanDetailState = try {
                val hewan = hwn.getHewanByid_hewan(_id_hewan)
                DetailUiState.Success(hewan)
            } catch (e: IOException) {
                DetailUiState.Error
            } catch (e: HttpException) {
                DetailUiState.Error
            }
        }
    }

    fun deleteHwn(id_hewan:String) {
        viewModelScope.launch {
            try {
                hwn.deleteHewan(id_hewan)
            }catch (e:IOException){
                HomeUiState.Error
            }catch (e: HttpException){
                HomeUiState.Error
            }
        }
    }
}