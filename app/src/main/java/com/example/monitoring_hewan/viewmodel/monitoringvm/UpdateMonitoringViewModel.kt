package com.example.monitoring_hewan.viewmodel.monitoringvm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monitoring_hewan.model.Hewan
import com.example.monitoring_hewan.model.Kandang
import com.example.monitoring_hewan.model.Petugas
import com.example.monitoring_hewan.repository.HewanRepository
import com.example.monitoring_hewan.repository.KandangRepository
import com.example.monitoring_hewan.repository.MonitoringRepository
import com.example.monitoring_hewan.repository.PetugasRepository
import com.example.monitoring_hewan.view.MonitoringView.DestinasiUpdateMonitoring
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class UpdateMonitoringViewModel(
    savedStateHandle: SavedStateHandle,
    private val mtr: MonitoringRepository,
    private val kdg: KandangRepository,
    private val ptgs: PetugasRepository,
    private val hwn: HewanRepository
) : ViewModel() {
    // State untuk UI
    var UpdateUiState by mutableStateOf(InsertUiState())
        private set
    var kdglist by mutableStateOf<List<Kandang>>(emptyList())
        private set
    var dokterHewanPetugas by mutableStateOf<List<Petugas>>(emptyList())
        private set

    private val _id_monitoring: String = checkNotNull(savedStateHandle[DestinasiUpdateMonitoring.ID_MONITORING])

    init {
        viewModelScope.launch {
            try {
                // Ambil data monitoring terlebih dahulu
                val monitoring = mtr.getMonitoringByid_monitoring(_id_monitoring).toUiStateMtr()
                UpdateUiState = monitoring

                // Ambil data pendukung (kandang & dokter hewan)
                loadDependencies(monitoring)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private suspend fun loadDependencies(monitoring: InsertUiState) {
        try {
            // Ambil data kandang dan dokter hewan
            getKandang()
            getDokterHewanPetugas()

            // Cocokkan data yang sudah ada
            val selectedKandang = kdglist.find { it.id_kandang == monitoring.insertUiEvent.id_kandang }
            val selectedPetugas = dokterHewanPetugas.find { it.id_petugas == monitoring.insertUiEvent.id_petugas }

            updateInsertMtrState(monitoring.insertUiEvent, selectedKandang, selectedPetugas)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getDokterHewanPetugas() {
        viewModelScope.launch {
            try {
                val petugasData = ptgs.getPetugas()
                dokterHewanPetugas = petugasData.data.filter { it.jabatan == "Dokter Hewan" }
            } catch (e: IOException) {
                println("Failed to fetch dokter hewan: ${e.message}")
            } catch (e: HttpException) {
                println("HTTP error while fetching dokter hewan: ${e.message()}")
            }
        }
    }

    fun getKandang() {
        viewModelScope.launch {
            try {
                val kandangList = kdg.getKandang().data
                val hewanList = hwn.getHewan().data

                val kandangWithNamaHewan = kandangList.map { kandang ->
                    val namaHewan = hewanList.find { it.id_hewan == kandang.id_hewan }?.nama_hewan ?: "Unknown"
                    kandang.copy(nama_hewan = namaHewan)
                }

                kdglist = kandangWithNamaHewan
            } catch (e: IOException) {
                println("Failed to fetch kandang: ${e.message}")
            } catch (e: HttpException) {
                println("HTTP error while fetching kandang: ${e.message()}")
            }
        }
    }

    fun updateInsertMtrState(insertUiEvent: InsertUiEvent, kandang: Kandang? = null, petugas: Petugas? = null) {
        var updatedEvent = insertUiEvent

        kandang?.let {
            updatedEvent = updatedEvent.copy(id_kandang = kandang.id_kandang)
        }

        petugas?.let {
            updatedEvent = updatedEvent.copy(id_petugas = petugas.id_petugas)
        }

        UpdateUiState = InsertUiState(insertUiEvent = updatedEvent)
    }

    suspend fun updateMtr() {
        viewModelScope.launch {
            try {
                mtr.updateMonitoring(_id_monitoring, UpdateUiState.insertUiEvent.toMtr())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
