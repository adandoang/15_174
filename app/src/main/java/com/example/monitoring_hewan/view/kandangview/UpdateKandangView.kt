package com.example.monitoring_hewan.view.kandangview

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.monitoring_hewan.customwidget.CostumeTopAppBar
import com.example.monitoring_hewan.model.Hewan
import com.example.monitoring_hewan.navigation.DestinasiNavigasi
import com.example.monitoring_hewan.repository.HewanRepository
import com.example.monitoring_hewan.viewmodel.PenyediaViewModel
import com.example.monitoring_hewan.viewmodel.kandangvm.UpdateKandangViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiUpdateKandang: DestinasiNavigasi {
    override val route = "updatekandang"
    override val titleRes = "Update Kandang"
    const val ID_KANDANG = "id_kandang"
    val routesWithArg = "$route/{$ID_KANDANG}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateScreenKandang(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate:()-> Unit,
    viewModel: UpdateKandangViewModel = viewModel(factory = PenyediaViewModel.Factory),
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    viewModel.getHewan()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateKandang.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onBack,
            )
        }
    ){ padding ->
        EntryBody(
            modifier = Modifier.padding(padding),
            insertUiState = viewModel.UpdateUiState,
            onKandangValueChange = viewModel::updateInsertKdgState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateKdg()
                    delay(600)
                    withContext(Dispatchers.Main) {
                        onNavigate()
                    }
                }
            },
            hewanList = viewModel.hwnlist
        )
    }
}