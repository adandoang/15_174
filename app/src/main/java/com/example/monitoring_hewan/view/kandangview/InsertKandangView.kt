package com.example.monitoring_hewan.view.kandangview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.monitoring_hewan.customwidget.CostumeTopAppBar
import com.example.monitoring_hewan.model.Hewan
import com.example.monitoring_hewan.navigation.DestinasiNavigasi
import com.example.monitoring_hewan.repository.HewanRepository
import com.example.monitoring_hewan.viewmodel.PenyediaViewModel
import com.example.monitoring_hewan.viewmodel.kandangvm.InsertKandangViewModel
import com.example.monitoring_hewan.viewmodel.kandangvm.InsertUiEvent
import com.example.monitoring_hewan.viewmodel.kandangvm.InsertUiState
import kotlinx.coroutines.launch

object DestinasiEntryKandang: DestinasiNavigasi {
    override val route ="entrykandang"
    override val titleRes = "Insert Kandang"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryScreenKandang(
    navigateBack: ()-> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertKandangViewModel= viewModel(factory = PenyediaViewModel.Factory),
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    viewModel.getHewan()


    Scaffold (
        modifier=modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntryKandang.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ){ innerPadding ->
        EntryBody(
            insertUiState = viewModel.uiState,
            onKandangValueChange = viewModel::updateInsertKdgState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertKdg()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
            hewanList = viewModel.hwnlist
        )
    }
}

@Composable
fun EntryBody(
    insertUiState: InsertUiState,
    onKandangValueChange: (InsertUiEvent)->Unit,
    onSaveClick: ()->Unit,
    modifier: Modifier = Modifier,
    hewanList: List<Hewan>


){
    Column (
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ){
        FormInput(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onKandangValueChange,
            modifier = Modifier.fillMaxWidth(),
            hewanList = hewanList
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInput(
    insertUiEvent: InsertUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertUiEvent)->Unit={},
    enabled: Boolean = true,
    hewanList: List<Hewan>,

) {
    var expanded by remember { mutableStateOf(false) }
    var selectedHewanName by remember { mutableStateOf("") }

    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        OutlinedTextField(
            value = insertUiEvent.id_kandang,
            onValueChange = {onValueChange(insertUiEvent.copy(id_kandang = it))},
            label = { Text("ID Kandang") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
        ) {
            OutlinedTextField(
                value = selectedHewanName,
                onValueChange = {},
                readOnly = true,
                label = { Text("Nama Hewan") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth(),
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                hewanList.forEach { hewan ->
                    DropdownMenuItem(
                        text = { Text(hewan.nama_hewan) },
                        onClick = {
                            selectedHewanName = hewan.nama_hewan
                            onValueChange(insertUiEvent.copy(id_hewan = hewan.id_hewan))
                            expanded = false
                        },
                    )
                }
            }
        }

        OutlinedTextField(
            value = if (insertUiEvent.kapasitas == 0) "" else insertUiEvent.kapasitas.toString(),
            onValueChange = {
                if (it.isEmpty()) {
                    onValueChange(insertUiEvent.copy(kapasitas = 0))
                } else {
                    val kapasitas = it.toIntOrNull()
                    if (kapasitas != null) {
                        onValueChange(insertUiEvent.copy(kapasitas = kapasitas))
                    }
                }
            },
            label = { Text("Kapasitas") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertUiEvent.lokasi,
            onValueChange = {onValueChange(insertUiEvent.copy(lokasi = it))},
            label = { Text("Lokasi") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        if (enabled){
            Text(
                text = "Isi Semua Data",
                modifier = Modifier.padding(12.dp)
            )
        }
        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
    }
}