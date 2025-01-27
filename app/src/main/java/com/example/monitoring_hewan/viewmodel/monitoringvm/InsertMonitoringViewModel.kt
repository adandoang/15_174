package com.example.monitoring_hewan.viewmodel.monitoringvm

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monitoring_hewan.model.Hewan
import com.example.monitoring_hewan.model.Kandang
import com.example.monitoring_hewan.model.Monitoring
import com.example.monitoring_hewan.model.Petugas
import com.example.monitoring_hewan.repository.HewanRepository
import com.example.monitoring_hewan.repository.KandangRepository
import com.example.monitoring_hewan.repository.MonitoringRepository
import com.example.monitoring_hewan.repository.PetugasRepository
import com.example.monitoring_hewan.viewmodel.kandangvm.HomeKandangViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import kotlin.math.tan

class InsertMonitoringViewModel(
    private val mtr: MonitoringRepository,
    private val kdg: KandangRepository,
    private val ptgs: PetugasRepository,
    private val hwn: HewanRepository

): ViewModel() {
    var uiState by mutableStateOf(InsertUiState())
        private set
    var kdglist by mutableStateOf<List<Kandang>>(listOf())
    var dokterHewanPetugas by mutableStateOf<List<Petugas>>(listOf())


    fun updateInsertMtrState(insertUiEvent: InsertUiEvent) {
        val totalHewan = insertUiEvent.hewan_sakit + insertUiEvent.hewan_sehat
        val status = when {
            totalHewan == 0 -> ""
            insertUiEvent.hewan_sakit.toDouble() / totalHewan < 0.1 -> "Aman"
            insertUiEvent.hewan_sakit.toDouble() / totalHewan < 0.5 -> "Waspada"
            else -> "Kritis"
        }

        uiState = InsertUiState(insertUiEvent.copy(status = status))
    }

    fun getDokterHewanPetugas() {
        viewModelScope.launch {
            try {
                val petugasData = ptgs.getPetugas()
                dokterHewanPetugas = petugasData.data.filter { it.jabatan == "Dokter Hewan" }
            } catch (e: Exception) {
                e.printStackTrace()
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
            } catch (e: HttpException) {
            }
        }
    }

    suspend fun insertMtr() {
        viewModelScope.launch {
            try {
                mtr.insertMonitoring(uiState.insertUiEvent.toMtr())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}


data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent()
)

data class InsertUiEvent(
    val id_monitoring: String="",
    val id_petugas: String="",
    val id_kandang: String="",
    val tanggal_monitoring: String="",
    val hewan_sakit: Int=0,
    val hewan_sehat: Int=0,
    val status: String="",
)

fun InsertUiEvent.toMtr(): Monitoring = Monitoring(
    id_monitoring = id_monitoring,
    id_petugas = id_petugas,
    id_kandang = id_kandang,
    tanggal_monitoring = tanggal_monitoring,
    hewan_sakit = hewan_sakit,
    hewan_sehat = hewan_sehat,
    status = status,
)

fun Monitoring.toUiStateMtr(): InsertUiState = InsertUiState(
    insertUiEvent = toInsertUiEvent()
)

fun Monitoring.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    id_monitoring = id_monitoring,
    id_petugas = id_petugas,
    id_kandang = id_kandang,
    tanggal_monitoring = tanggal_monitoring,
    hewan_sakit = hewan_sakit,
    hewan_sehat = hewan_sehat,
    status = status,
)