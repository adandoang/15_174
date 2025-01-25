package com.example.monitoring_hewan.viewmodel.hewanvm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monitoring_hewan.model.Hewan
import com.example.monitoring_hewan.repository.HewanRepository
import kotlinx.coroutines.launch

class InsertHewanViewModel (private val hwn: HewanRepository): ViewModel() {
    var uiState by mutableStateOf(InsertUiState())
        private set

    fun updateInsertHwnState(insertUiEvent:InsertUiEvent) {
        uiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun insertHwn() {
        viewModelScope.launch{
            try {
                hwn.insertHewan(uiState.insertUiEvent.toHwn())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}

data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent()
)

data class InsertUiEvent(
    val id_hewan: String="",
    val nama_hewan: String="",
    val tipe_pakan: String="",
    val populasi: Int=0,
    val zona_wilayah: String="",
)

fun InsertUiEvent.toHwn(): Hewan = Hewan(
    id_hewan = id_hewan,
    nama_hewan = nama_hewan,
    tipe_pakan = tipe_pakan,
    populasi = populasi,
    zona_wilayah = zona_wilayah,
)

fun Hewan.toUiStateHwn(): InsertUiState = InsertUiState(
    insertUiEvent = toInsertUiEvent()
)

fun Hewan.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    id_hewan = id_hewan,
    nama_hewan = nama_hewan,
    tipe_pakan = tipe_pakan,
    populasi = populasi,
    zona_wilayah = zona_wilayah,
)