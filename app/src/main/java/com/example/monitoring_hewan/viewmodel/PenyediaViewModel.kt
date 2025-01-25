package com.example.monitoring_hewan.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.monitoring_hewan.application.Applications
import com.example.monitoring_hewan.viewmodel.hewanvm.DetailHewanViewModel
import com.example.monitoring_hewan.viewmodel.hewanvm.HomeHewanViewModel
import com.example.monitoring_hewan.viewmodel.hewanvm.InsertHewanViewModel
import com.example.monitoring_hewan.viewmodel.hewanvm.UpdateHewanViewModel
import com.example.monitoring_hewan.viewmodel.kandangvm.DetailKandangViewModel
import com.example.monitoring_hewan.viewmodel.kandangvm.HomeKandangViewModel
import com.example.monitoring_hewan.viewmodel.kandangvm.InsertKandangViewModel
import com.example.monitoring_hewan.viewmodel.kandangvm.UpdateKandangViewModel
import com.example.monitoring_hewan.viewmodel.monitoringvm.DetailMonitoringViewModel
import com.example.monitoring_hewan.viewmodel.monitoringvm.HomeMonitoringViewModel
import com.example.monitoring_hewan.viewmodel.monitoringvm.InsertMonitoringViewModel
import com.example.monitoring_hewan.viewmodel.monitoringvm.UpdateMonitoringViewModel
import com.example.monitoring_hewan.viewmodel.petugasvm.DetailPetugasViewModel
import com.example.monitoring_hewan.viewmodel.petugasvm.HomePetugasViewModel
import com.example.monitoring_hewan.viewmodel.petugasvm.InsertPetugasViewModel
import com.example.monitoring_hewan.viewmodel.petugasvm.UpdatePetugasViewModel

object PenyediaViewModel{
    val Factory = viewModelFactory {
        initializer { HomeHewanViewModel(aplikasi().container.hewanRepository) }
        initializer { InsertHewanViewModel(aplikasi().container.hewanRepository) }
        initializer { DetailHewanViewModel(createSavedStateHandle(),aplikasi().container.hewanRepository) }
        initializer { UpdateHewanViewModel(createSavedStateHandle(),aplikasi().container.hewanRepository) }

        initializer { HomeKandangViewModel(aplikasi().container.kandangRepository) }
        initializer { InsertKandangViewModel(aplikasi().container.kandangRepository, aplikasi().container.hewanRepository) }
        initializer { DetailKandangViewModel(createSavedStateHandle(),aplikasi().container.kandangRepository) }
        initializer { UpdateKandangViewModel(createSavedStateHandle(),aplikasi().container.kandangRepository) }

        initializer { HomePetugasViewModel(aplikasi().container.petugasRepository) }
        initializer { InsertPetugasViewModel(aplikasi().container.petugasRepository) }
        initializer { DetailPetugasViewModel(createSavedStateHandle(),aplikasi().container.petugasRepository) }
        initializer { UpdatePetugasViewModel(createSavedStateHandle(),aplikasi().container.petugasRepository) }

        initializer { HomeMonitoringViewModel(aplikasi().container.monitoringRepository) }
        initializer { InsertMonitoringViewModel(aplikasi().container.monitoringRepository) }
        initializer { DetailMonitoringViewModel(createSavedStateHandle(),aplikasi().container.monitoringRepository) }
        initializer { UpdateMonitoringViewModel(createSavedStateHandle(),aplikasi().container.monitoringRepository) }
    }
    fun CreationExtras.aplikasi(): Applications =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as Applications)
}