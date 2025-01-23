package com.example.monitoring_hewan.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.monitoring_hewan.HewanApplications
import com.example.monitoring_hewan.viewmodel.hewanvm.DetailHewanViewModel
import com.example.monitoring_hewan.viewmodel.hewanvm.HomeHewanViewModel
import com.example.monitoring_hewan.viewmodel.hewanvm.InsertHewanViewModel
import com.example.monitoring_hewan.viewmodel.hewanvm.UpdateHewanViewModel

object PenyediaViewModel{
    val Factory = viewModelFactory {
        initializer { HomeHewanViewModel(aplikasiHewan().container.hewanRepository) }
        initializer { InsertHewanViewModel(aplikasiHewan().container.hewanRepository) }
        initializer { DetailHewanViewModel(createSavedStateHandle(),aplikasiHewan().container.hewanRepository) }
        initializer { UpdateHewanViewModel(createSavedStateHandle(),aplikasiHewan().container.hewanRepository) }
    }
    fun CreationExtras.aplikasiHewan(): HewanApplications =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as HewanApplications)
}