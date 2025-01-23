package com.example.monitoring_hewan.view.hewanview

import com.example.monitoring_hewan.navigation.DestinasiNavigasi

object DestinasiDetail: DestinasiNavigasi {
    override val route = "detailhewan"
    override val titleRes = "Detail Hewan"
    const val ID_HEWAN = "id_hewan"
    val routesWithArg = "$route/{$ID_HEWAN}"
}