package com.example.monitoring_hewan.viewmodel.kandangvm

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monitoring_hewan.model.Hewan
import com.example.monitoring_hewan.model.Kandang
import com.example.monitoring_hewan.repository.HewanRepository
import com.example.monitoring_hewan.repository.KandangRepository
import kotlinx.coroutines.launch

class InsertKandangViewModel(
    private val kdg: KandangRepository,
    private val hwn: HewanRepository
) : ViewModel() {

    var uiState by mutableStateOf(InsertUiState())
        private set
    var hwnlist by mutableStateOf<List<Hewan>>(listOf())

    fun updateInsertKdgState(insertUiEvent: InsertUiEvent) {
        uiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    fun insertKdg() {
        viewModelScope.launch {
            try {
                kdg.insertKandang(uiState.insertUiEvent.toKdg())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun getHewan() {
        viewModelScope.launch {
            try {
                val hwndata = hwn.getHewan()
                hwnlist = hwndata.data

                // Tambahkan log untuk debugging
                Log.d("InsertKandangViewModel", "Hewan List Loaded: $hwnlist")
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("InsertKandangViewModel", "Error fetching Hewan: ${e.message}")
            }
        }
    }
}

data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent()
)

data class InsertUiEvent(
    val id_kandang: String="",
    val id_hewan: String="",
    val kapasitas: Int=0,
    val lokasi: String="",
)

fun InsertUiEvent.toKdg(): Kandang = Kandang(
    id_kandang = id_kandang,
    id_hewan = id_hewan,
    kapasitas = kapasitas,
    lokasi = lokasi,
)

fun Kandang.toUiStateKdg(): InsertUiState = InsertUiState(
    insertUiEvent = toInsertUiEvent()
)

fun Kandang.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    id_kandang = id_kandang,
    id_hewan = id_hewan,
    kapasitas = kapasitas,
    lokasi = lokasi,
)