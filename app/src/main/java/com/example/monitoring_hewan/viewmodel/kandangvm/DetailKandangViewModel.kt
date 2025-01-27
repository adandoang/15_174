package com.example.monitoring_hewan.viewmodel.kandangvm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monitoring_hewan.model.Hewan
import com.example.monitoring_hewan.model.Kandang
import com.example.monitoring_hewan.repository.HewanRepository
import com.example.monitoring_hewan.repository.KandangRepository
import com.example.monitoring_hewan.view.kandangview.DestinasiDetailKandang
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class DetailUiState {
    data class Success(val kandang: Kandang) : DetailUiState()
    object Error : DetailUiState()
    object Loading : DetailUiState()
}

class DetailKandangViewModel(
    savedStateHandle: SavedStateHandle,
    private val kdg: KandangRepository,
    private val hwn: HewanRepository
) : ViewModel() {

    var kandangDetailState: DetailUiState by mutableStateOf(DetailUiState.Loading)
        private set

    private val _id_kandang: String = checkNotNull(savedStateHandle[DestinasiDetailKandang.ID_KANDANG])

    init {
        getKandangbyId_kandang()
    }

    fun getKandangbyId_kandang() {
        viewModelScope.launch {
            kandangDetailState = DetailUiState.Loading
            kandangDetailState = try {
                val kandang = kdg.getKandangByid_kandang(_id_kandang)

                val hewan = hwn.getHewanByid_hewan(kandang.id_hewan)
                kandang.nama_hewan = hewan.nama_hewan
                DetailUiState.Success(kandang)
            } catch (e: IOException) {
                DetailUiState.Error
            } catch (e: HttpException) {
                DetailUiState.Error
            }
        }
    }


    fun deleteKdg(id_kandang:String) {
        viewModelScope.launch {
            try {
                kdg.deleteKandang(id_kandang)
            }catch (e:IOException){
                HomeUiState.Error
            }catch (e: HttpException){
                HomeUiState.Error
            }
        }
    }
}