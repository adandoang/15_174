package com.example.monitoring_hewan.view.kandangview

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.monitoring_hewan.R
import com.example.monitoring_hewan.customwidget.CostumeTopAppBar
import com.example.monitoring_hewan.model.Kandang
import com.example.monitoring_hewan.navigation.DestinasiNavigasi
import com.example.monitoring_hewan.viewmodel.PenyediaViewModel
import com.example.monitoring_hewan.viewmodel.hewanvm.HomeHewanViewModel
import com.example.monitoring_hewan.viewmodel.kandangvm.HomeKandangViewModel
import com.example.monitoring_hewan.viewmodel.kandangvm.HomeUiState
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

object DestinasiHomeKandang : DestinasiNavigasi {
    override val route = "homekandang"
    override val titleRes = "Kandang"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenKandang(
    navigateBack: ()-> Unit,
    navigateToItemEntry: ()-> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit={},
    viewModel: HomeKandangViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val searchQuery = remember { mutableStateOf(TextFieldValue("")) }
    val coroutineScope = rememberCoroutineScope()
    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomeKandang.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getKdg()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Kandang")
            }
        }
    ){ innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            // Search Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = searchQuery.value,
                    onValueChange = { searchQuery.value = it },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    placeholder = { Text(text = "Cari kandang...") },
                    shape = MaterialTheme.shapes.large,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        autoCorrect = false,
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Search
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    singleLine = true
                )
                Button(
                    onClick = {
                        coroutineScope.launch {
                            viewModel.searchKdg(searchQuery.value.text)
                        }
                    },
                    shape = MaterialTheme.shapes.medium
                ) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                }
            }

            HomeStatus(
                homeUiState = viewModel.kdgUIState,
                retryAction = { viewModel.getKdg() },
                modifier = Modifier.fillMaxSize(),
                onDetailClick = onDetailClick,
                onDeleteClick = {
                    viewModel.deleteKdg(it.id_hewan)
                    viewModel.getKdg()
                }
            )
        }
    }
}

@Composable
fun HomeStatus(
    homeUiState: HomeUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Kandang) -> Unit={},
    onDetailClick: (String) -> Unit
){
    when(homeUiState) {
        is HomeUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is HomeUiState.Success ->
            if (homeUiState.kandang.isEmpty()) {
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Kandang")
                }
            } else {
                KdgLayout(
                    kandang = homeUiState.kandang, modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.id_kandang)
                    },
                    onDeleteClick = {
                        onDeleteClick(it)
                    }
                )
            }
        is HomeUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier){
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun OnError(retryAction: ()->Unit, modifier: Modifier = Modifier){
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed),modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun KdgLayout(
    kandang: List<Kandang>,
    modifier: Modifier = Modifier,
    onDetailClick: (Kandang) -> Unit,
    onDeleteClick: (Kandang) -> Unit = {}
){
    LazyColumn (
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        items(kandang){kandang ->
            KdgCard(
                kandang = kandang,
                modifier = Modifier
                    .fillMaxWidth(),
                onDeleteClick = {
                    onDeleteClick(kandang)
                },
                onDetailClick =
                    onDetailClick
            )
        }
    }
}

@Composable
fun KdgCard(
    kandang: Kandang,
    modifier: Modifier = Modifier,
    onDeleteClick: (Kandang) -> Unit ={},
    onDetailClick: (Kandang) -> Unit ={}
){
    Card (
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ){
        Column (
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = kandang.id_kandang,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = kandang.kapasitas.toString(),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text(
                text = "${kandang.id_hewan} - ${kandang.nama_hewan}",
                style = MaterialTheme.typography.titleMedium
            )
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = kandang.lokasi,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDetailClick(kandang) }) {
                    Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "Lihat Detail")
                }
            }
        }
    }
}

