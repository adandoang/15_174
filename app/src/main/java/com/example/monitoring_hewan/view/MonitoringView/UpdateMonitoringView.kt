package com.example.monitoring_hewan.view.MonitoringView

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.monitoring_hewan.customwidget.CostumeTopAppBar
import com.example.monitoring_hewan.navigation.DestinasiNavigasi
import com.example.monitoring_hewan.viewmodel.PenyediaViewModel
import com.example.monitoring_hewan.viewmodel.kandangvm.HomeKandangViewModel
import com.example.monitoring_hewan.viewmodel.monitoringvm.UpdateMonitoringViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiUpdateMonitoring: DestinasiNavigasi {
    override val route = "updatemonitoring"
    override val titleRes = "Update Monitoring"
    const val ID_MONITORING = "id_monitoring"
    val routesWithArg = "$route/{$ID_MONITORING}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateScreenMonitoring(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate:()-> Unit,
    viewModel: UpdateMonitoringViewModel = viewModel(factory = PenyediaViewModel.Factory),
    kdgviewModel: HomeKandangViewModel = viewModel(factory = PenyediaViewModel.Factory),
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        kdgviewModel.getKdg()
        viewModel.getKandang()
        viewModel.getDokterHewanPetugas()

        println("Kandang List: ${viewModel.kdglist}")
        println("Dokter Hewan List: ${viewModel.dokterHewanPetugas}")
    }


    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateMonitoring.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onBack,
            )
        }
    ){ padding ->
        EntryBody(
            modifier = Modifier.padding(padding),
            insertUiState = viewModel.UpdateUiState,
            onMtrValueChange = viewModel::updateInsertMtrState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateMtr()
                    delay(600)
                    withContext(Dispatchers.Main) {
                        onNavigate()
                    }
                }
            },
            kandangList = viewModel.kdglist,
            dokterHewanList = viewModel.dokterHewanPetugas,
            context = context,
        )
    }
}