package com.example.monitoring_hewan.view.hewanview

import com.example.monitoring_hewan.navigation.DestinasiNavigasi

object DestinasiUpdate: DestinasiNavigasi {
    override val route = "updatehewan"
    override val titleRes = "Update Hewan"
    const val ID_HEWAN = "id_hewan"
    val routesWithArg = "$route/{$ID_HEWAN}"
}