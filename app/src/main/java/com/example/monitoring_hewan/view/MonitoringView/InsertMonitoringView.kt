package com.example.monitoring_hewan.view.MonitoringView

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
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
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.monitoring_hewan.customwidget.CostumeTopAppBar
import com.example.monitoring_hewan.model.Kandang
import com.example.monitoring_hewan.model.Petugas
import com.example.monitoring_hewan.navigation.DestinasiNavigasi
import com.example.monitoring_hewan.viewmodel.PenyediaViewModel
import com.example.monitoring_hewan.viewmodel.kandangvm.HomeKandangViewModel
import com.example.monitoring_hewan.viewmodel.monitoringvm.InsertMonitoringViewModel
import com.example.monitoring_hewan.viewmodel.monitoringvm.InsertUiEvent
import com.example.monitoring_hewan.viewmodel.monitoringvm.InsertUiState
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object DestinasiEntryMonitoring: DestinasiNavigasi {
    override val route ="entrymonitoring"
    override val titleRes = "Insert Monitoring"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryScreenMonitoring(
    navigateBack: ()-> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertMonitoringViewModel = viewModel(factory = PenyediaViewModel.Factory),
    kdgviewModel: HomeKandangViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val context = LocalContext.current

    viewModel.getKandang()
    viewModel.getDokterHewanPetugas()
    kdgviewModel.getKdg()

    Scaffold (
        modifier=modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntryMonitoring.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ){ innerPadding ->
        EntryBody(
            insertUiState = viewModel.uiState,
            onMtrValueChange = viewModel::updateInsertMtrState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertMtr()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
            kandangList = viewModel.kdglist,
            dokterHewanList = viewModel.dokterHewanPetugas,
            context = context
        )
    }
}

@Composable
fun EntryBody(
    insertUiState: InsertUiState,
    onMtrValueChange: (InsertUiEvent)->Unit,
    onSaveClick: ()->Unit,
    modifier: Modifier = Modifier,
    kandangList: List<Kandang>,
    dokterHewanList: List<Petugas>,
    context: Context
){

    val isFormValid =
            insertUiState.insertUiEvent.id_monitoring.isNotEmpty() &&
            insertUiState.insertUiEvent.id_petugas.isNotEmpty() &&
            insertUiState.insertUiEvent.id_kandang.isNotEmpty()&&
            insertUiState.insertUiEvent.tanggal_monitoring.isNotEmpty()&&
            insertUiState.insertUiEvent.hewan_sakit.toString().isNotEmpty()&&
            insertUiState.insertUiEvent.hewan_sehat.toString().isNotEmpty()&&
            insertUiState.insertUiEvent.status.toString().isNotEmpty()

    Column (
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ){
        FormInput(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onMtrValueChange,
            modifier = Modifier.fillMaxWidth(),
            kandangList = kandangList,
            dokterHewanList = dokterHewanList,
            context = context
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth(),
            enabled = isFormValid
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
    kandangList: List<Kandang>,
    dokterHewanList: List<Petugas>,
    context: Context

    ) {

    var expanded by remember { mutableStateOf(false) }
    var selectedKandang by remember { mutableStateOf("") }
    var selectedKandangId by remember { mutableStateOf("") }
    var expandedPetugas by remember { mutableStateOf(false) }
    var selectedPetugas by remember { mutableStateOf("") }
    var showDateTimePicker by remember { mutableStateOf(false) }

    LaunchedEffect(insertUiEvent, kandangList, dokterHewanList) {
        val kandang = kandangList.find { it.id_kandang == insertUiEvent.id_kandang }
        val petugas = dokterHewanList.find { it.id_petugas == insertUiEvent.id_petugas }

        selectedKandang = kandang?.let { "${it.id_kandang} - ${it.nama_hewan}" } ?: ""
        selectedPetugas = petugas?.let { "${it.id_petugas} - ${it.nama_petugas}" } ?: ""
    }

    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        OutlinedTextField(
            value = insertUiEvent.id_monitoring,
            onValueChange = {onValueChange(insertUiEvent.copy(id_monitoring = it))},
            label = { Text("ID Monitoring") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        ExposedDropdownMenuBox(
            expanded = expandedPetugas,
            onExpandedChange = { expandedPetugas = !expandedPetugas },
        ) {
            OutlinedTextField(
                value = selectedPetugas,
                onValueChange = {},
                readOnly = true,
                label = { Text("ID Petugas - Nama Petugas") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedPetugas) },
                modifier = Modifier.menuAnchor().fillMaxWidth(),
            )

            ExposedDropdownMenu(
                expanded = expandedPetugas,
                onDismissRequest = { expandedPetugas = false },
            ) {
                dokterHewanList.forEach { petugas ->
                    DropdownMenuItem(
                        text = { Text("${petugas.id_petugas} - ${petugas.nama_petugas}") },
                        onClick = {
                            selectedPetugas = petugas.nama_petugas
                            onValueChange(insertUiEvent.copy(id_petugas = petugas.id_petugas))
                            expandedPetugas = false
                        },
                    )
                }
            }
        }
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
        ) {
            OutlinedTextField(
                value = selectedKandang,
                onValueChange = {},
                readOnly = true,
                label = { Text("ID Kandang - Nama Hewan") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth(),
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                kandangList.forEach { kandang ->
                    DropdownMenuItem(
                        text = { Text("${kandang.id_kandang} - ${kandang.nama_hewan}") },
                        onClick = {
                            selectedKandang = "${kandang.id_kandang} - ${kandang.nama_hewan}"
                            selectedKandangId = kandang.id_kandang
                            onValueChange(insertUiEvent.copy(id_kandang = selectedKandangId))
                            expanded = false
                        },
                    )
                }
            }
        }
        OutlinedTextField(
            value = insertUiEvent.tanggal_monitoring,
            onValueChange = {},
            label = { Text("Tanggal Monitoring") },
            modifier = Modifier.fillMaxWidth(),
            enabled = false, // Non-editable manually
            singleLine = true,
            trailingIcon = {
                TextButton(onClick = { showDateTimePicker = true }) {
                    Text("Pilih")
                }
            }
        )

        // DateTime Picker Dialog
        if (showDateTimePicker) {
            DateTimePickerDialog(context) { selectedDateTime ->
                onValueChange(insertUiEvent.copy(tanggal_monitoring = selectedDateTime))
                showDateTimePicker = false
            }
        }

        OutlinedTextField(
            value = if (insertUiEvent.hewan_sakit == 0) "" else insertUiEvent.hewan_sakit.toString(),
            onValueChange = {
                if (it.isEmpty()) {
                    onValueChange(insertUiEvent.copy(hewan_sakit = 0))
                } else {
                    val hewan_sakit = it.toIntOrNull()
                    if (hewan_sakit != null) {
                        onValueChange(insertUiEvent.copy(hewan_sakit = hewan_sakit))
                    }
                }
            },
            label = { Text("Hewan Sakit") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = if (insertUiEvent.hewan_sehat == 0) "" else insertUiEvent.hewan_sehat.toString(),
            onValueChange = {
                if (it.isEmpty()) {
                    onValueChange(insertUiEvent.copy(hewan_sehat = 0))
                } else {
                    val hewan_sehat = it.toIntOrNull()
                    if (hewan_sehat != null) {
                        onValueChange(insertUiEvent.copy(hewan_sehat = hewan_sehat))
                    }
                }
            },
            label = { Text("Hewan Sehat") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertUiEvent.status,
            onValueChange = {},
            label = { Text("Status") },
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
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

@Composable
fun DateTimePickerDialog(context: Context, onDateTimeSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()

    DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            TimePickerDialog(
                context,
                { _, hourOfDay, minute ->
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    calendar.set(Calendar.MINUTE, minute)
                    val format = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                    onDateTimeSelected(format.format(calendar.time))
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            ).show()
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).show()
}