package com.example.monitoring_hewan.view.petugasview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.monitoring_hewan.customwidget.CostumeTopAppBar
import com.example.monitoring_hewan.model.Petugas
import com.example.monitoring_hewan.navigation.DestinasiNavigasi
import com.example.monitoring_hewan.viewmodel.PenyediaViewModel
import com.example.monitoring_hewan.viewmodel.petugasvm.DetailPetugasViewModel
import com.example.monitoring_hewan.viewmodel.petugasvm.DetailUiState

object DestinasiDetailPetugas: DestinasiNavigasi {
    override val route = "detailpetugas"
    override val titleRes = "Detail Petugas"
    const val ID_PETUGAS = "id_petugas"
    val routesWithArg = "$route/{$ID_PETUGAS}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenPetugas(
    navigateBack: () -> Unit,
    navigateToItemUpdate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailPetugasViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailPetugas.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getPetugasbyId_petugas()
                }
            )
        },
    ) { innerPadding ->
        DetailStatus(
            modifier = Modifier.padding(innerPadding),
            detailUiState = viewModel.petugasDetailState,
            retryAction = { viewModel.getPetugasbyId_petugas() },
            onDeleteClick = {
                viewModel.deletePtgs(viewModel.petugasDetailState.let { state ->
                    if (state is DetailUiState.Success) state.petugas.id_petugas else ""
                })
                navigateBack()
            },
            navigateToItemUpdate = navigateToItemUpdate
        )
    }
}


@Composable
fun DetailStatus(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailUiState: DetailUiState,
    onDeleteClick: () -> Unit,
    navigateToItemUpdate: () -> Unit
) {
    when (detailUiState) {
        is DetailUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is DetailUiState.Success -> {
            if (detailUiState.petugas.id_petugas.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) { Text("Data tidak ditemukan") }
            } else {
                ItemDetailPtgs(
                    petugas = detailUiState.petugas,
                    modifier = modifier.fillMaxWidth(),
                    onDeleteClick = onDeleteClick,
                    navigateToItemUpdate = navigateToItemUpdate
                )
            }
        }
        is DetailUiState.Error -> com.example.monitoring_hewan.view.hewanview.OnError(
            retryAction,
            modifier = modifier.fillMaxSize()
        )
    }
}


@Composable
fun ItemDetailPtgs(
    modifier: Modifier = Modifier,
    petugas: Petugas,
    onDeleteClick: () -> Unit,
    navigateToItemUpdate: () -> Unit
) {
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
    Card(
        modifier = modifier.padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailPtgs(judul = "ID Petugas", isinya = petugas.id_petugas)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailPtgs(judul = "Nama Petugas", isinya = petugas.nama_petugas)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailPtgs(judul = "Jabatan", isinya = petugas.jabatan)

            Spacer(modifier = Modifier.padding(8.dp))

            Row {
                IconButton(
                    onClick = {
                        deleteConfirmationRequired = true
                    },
                ) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Lihat Detail")
                }
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { navigateToItemUpdate() }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Lihat Detail")
                }
            }

            if (deleteConfirmationRequired) {
                DeleteConfirmationDialog(
                    onDeleteConfirm = {
                        deleteConfirmationRequired = false
                        onDeleteClick()
                    },
                    onDeleteCancel = { deleteConfirmationRequired = false },
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}


@Composable
fun ComponentDetailPtgs(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = judul,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = isinya,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit, onDeleteCancel: () -> Unit, modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = {},
        title = { Text("Delete Data") },
        text = { Text("Apakah anda yakin ingin menghapus data?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = "Yes")
            }
        })
}