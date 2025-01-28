package com.example.monitoring_hewan.view.hewanview

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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
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
import com.example.monitoring_hewan.model.Hewan
import com.example.monitoring_hewan.navigation.DestinasiNavigasi
import com.example.monitoring_hewan.viewmodel.PenyediaViewModel
import com.example.monitoring_hewan.viewmodel.hewanvm.HomeHewanViewModel
import com.example.monitoring_hewan.viewmodel.hewanvm.HomeUiState
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

object DestinasiHomeHewan : DestinasiNavigasi {
    override val route = "homehewan"
    override val titleRes = "Hewan"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenHewan(
    navigateBack: ()-> Unit,
    navigateToItemEntry: ()-> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit={},
    viewModel: HomeHewanViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val searchQuery = remember { mutableStateOf(TextFieldValue("")) }
    val coroutineScope = rememberCoroutineScope()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomeHewan.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getHwn()
                }
            )
        },
        floatingActionButton = {
            Button(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Hewan")
                Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                Text("Add Hewan")
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
                    placeholder = { Text(text = "Cari hewan...") },
                    shape = MaterialTheme.shapes.large,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        autoCorrect = false,
                        keyboardType = KeyboardType.Text,
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
                            viewModel.searchHwn(searchQuery.value.text)
                        }
                    },
                    shape = MaterialTheme.shapes.medium
                ) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                }
            }

            HomeStatus(
                homeUiState = viewModel.hwnUIState,
                retryAction = { viewModel.getHwn() },
                modifier = Modifier.fillMaxSize(),
                onDetailClick = onDetailClick,
                onDeleteClick = {
                    viewModel.deleteHwn(it.id_hewan)
                    viewModel.getHwn()
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
    onDeleteClick: (Hewan) -> Unit={},
    onDetailClick: (String) -> Unit
){
    when(homeUiState) {
        is HomeUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is HomeUiState.Success ->
            if (homeUiState.hewan.isEmpty()) {
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Hewan")
                }
            } else {
                HwnLayout(
                    hewan = homeUiState.hewan, modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.id_hewan)
                    },
                    onDeleteClick = {
                        onDeleteClick(it)
                    }
                )
            }
        is HomeUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

//Homescreen menampilkan loading message
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
            painter = painterResource(id = R.drawable.ic_connection_error),
            contentDescription = "",
        )
        Text(text = stringResource(R.string.loading_failed),modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun HwnLayout(
    hewan: List<Hewan>,
    modifier: Modifier = Modifier,
    onDetailClick: (Hewan) -> Unit,
    onDeleteClick: (Hewan) -> Unit = {}
){
    LazyColumn (
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        items(hewan){hewan ->
            HwnCard(
                hewan = hewan,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(hewan) },
            )
        }
    }
}

@Composable
fun HwnCard(
    hewan: Hewan,
    modifier: Modifier = Modifier,
) {
    val backgroundColor = when (hewan.tipe_pakan.lowercase()) {
        "karnivora" -> MaterialTheme.colorScheme.error
        "herbivora" -> Color(0xFF4CAF50)// Hijau
        "omnivora" -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.surface
    }

    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = hewan.nama_hewan,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = hewan.id_hewan,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
            }
            Text(
                text = hewan.tipe_pakan,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
            Text(
                text = hewan.zona_wilayah,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
        }
    }
}

