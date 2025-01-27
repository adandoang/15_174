package com.example.monitoring_hewan.viewmodel.kandangvm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monitoring_hewan.model.Kandang
import com.example.monitoring_hewan.repository.HewanRepository
import com.example.monitoring_hewan.repository.KandangRepository
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed class HomeUiState{
    data class Success(val kandang: List<Kandang>): HomeUiState()
    object Error: HomeUiState()
    object Loading: HomeUiState()
}

class HomeKandangViewModel (
    private val
    kdg: KandangRepository,
    private val
    hwn: HewanRepository
):ViewModel() {
    var kdgUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getKdg()
    }

    fun getKdg() {
        viewModelScope.launch {
            kdgUIState = HomeUiState.Loading
            try {
                // Ambil data dari KandangRepository
                val kandangList = kdg.getKandang().data

                // Ambil data dari HewanRepository
                val hewanList = hwn.getHewan().data

                // Gabungkan data Kandang dengan nama_hewan
                val kandangWithNamaHewan = kandangList.map { kandang ->
                    val namaHewan = hewanList.find { it.id_hewan == kandang.id_hewan }?.nama_hewan ?: "Unknown"
                    kandang.copy(nama_hewan = namaHewan) // Menambahkan nama_hewan ke objek Kandang
                }

                // Update state dengan data gabungan
                kdgUIState = HomeUiState.Success(kandangWithNamaHewan)
            } catch (e: IOException) {
                kdgUIState = HomeUiState.Error
            } catch (e: HttpException) {
                kdgUIState = HomeUiState.Error
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