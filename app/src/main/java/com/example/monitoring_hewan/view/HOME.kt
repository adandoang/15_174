package com.example.monitoring_hewan.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.monitoring_hewan.R
import com.example.monitoring_hewan.navigation.DestinasiNavigasi

object DestinasiHOME : DestinasiNavigasi {
    override val route = "home"
    override val titleRes = "HOME"
}

@Composable
fun Home(
    onButtonClickHwn: () -> Unit,
    onButtonClickKdg: () -> Unit,
    onButtonClickPtgs: () -> Unit,

    ) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.ic_connection_error),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(26.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .background(Color.Gray, RoundedCornerShape(16.dp))
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Kandang",
                        color = colorResource(id = R.color.purple_700),
                        fontSize = 40.sp,
                        modifier = Modifier.padding(5.dp),
                        fontWeight = FontWeight.Bold
                    )
                    Image(
                        painter = painterResource(R.drawable.ic_launcher_foreground),
                        contentDescription = "",
                        modifier = Modifier.size(100.dp),
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    Button(
                        onClick = onButtonClickKdg,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(5.dp)
                    ) {
                        Text(text = "Go to Kandang Page")
                    }
                }
            }
            Spacer(modifier = Modifier.padding(20.dp))

            Card(
                modifier = Modifier
                    .background(Color.Gray, RoundedCornerShape(16.dp)),
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        color = colorResource(id = R.color.purple_700),
                        text = "Hewan",
                        fontSize = 40.sp,
                        modifier = Modifier.padding(5.dp),
                        fontWeight = FontWeight.Bold
                    )
                    Image(
                        painter = painterResource(R.drawable.loading_img),
                        contentDescription = "",
                        modifier = Modifier.size(100.dp)
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    Button(
                        onClick = onButtonClickHwn,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(5.dp)
                    ) {
                        Text(text = "Go to Hewan Page")
                    }
                }
            }
            Spacer(modifier = Modifier.padding(20.dp))

            Card(
                modifier = Modifier
                    .background(Color.Gray, RoundedCornerShape(16.dp)),
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        color = colorResource(id = R.color.purple_700),
                        text = "Petugas",
                        fontSize = 40.sp,
                        modifier = Modifier.padding(5.dp),
                        fontWeight = FontWeight.Bold
                    )
                    Image(
                        painter = painterResource(R.drawable.loading_img),
                        contentDescription = "",
                        modifier = Modifier.size(100.dp)
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    Button(
                        onClick = onButtonClickPtgs,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(5.dp)
                    ) {
                        Text(text = "Go to Petugas Page")
                    }
                }
            }
        }
    }
}
