package com.example.monitoring_hewan.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.monitoring_hewan.view.hewanview.DestinasiDetailHewan
import com.example.monitoring_hewan.view.hewanview.DestinasiEntryHewan
import com.example.monitoring_hewan.view.hewanview.DestinasiHomeHewan
import com.example.monitoring_hewan.view.hewanview.DestinasiUpdateHewan
import com.example.monitoring_hewan.view.hewanview.DetailScreen
import com.example.monitoring_hewan.view.hewanview.EntryHwnScreen
import com.example.monitoring_hewan.view.hewanview.HomeScreen
import com.example.monitoring_hewan.view.hewanview.UpdateScreen

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()){
    NavHost(
        navController=navController,
        startDestination = DestinasiHomeHewan.route,
        modifier = Modifier,
    ){
        composable(DestinasiHomeHewan.route){
            HomeScreen(
                navigateToItemEntry = {navController.navigate(DestinasiEntryHewan.route)},
                onDetailClick = { id_hewan ->
                    navController.navigate("${DestinasiDetailHewan.route}/$id_hewan")
                }
            )
        }
        composable(DestinasiEntryHewan.route){
            EntryHwnScreen(navigateBack = {
                navController.navigate(DestinasiHomeHewan.route){
                    popUpTo(DestinasiHomeHewan.route){
                        inclusive = true
                    }
                }
            })
        }
        composable(DestinasiDetailHewan.routesWithArg, arguments = listOf(navArgument(DestinasiDetailHewan.ID_HEWAN) {
            type = NavType.StringType })
        ){
            val id_hewan = it.arguments?.getString(DestinasiDetailHewan.ID_HEWAN)
            id_hewan?.let { id_hewan ->
                DetailScreen(
                    navigateToItemUpdate = { navController.navigate("${DestinasiUpdateHewan.route}/$id_hewan") },
                    navigateBack = { navController.navigate(DestinasiHomeHewan.route) {
                        popUpTo(DestinasiHomeHewan.route) { inclusive = true }
                    }
                    }
                )
            }
        }
        composable(DestinasiUpdateHewan.routesWithArg, arguments = listOf(navArgument(DestinasiDetailHewan.ID_HEWAN){
            type = NavType.StringType })
        ){
            val id_hewan = it.arguments?.getString(DestinasiUpdateHewan.ID_HEWAN)
            id_hewan?.let { id_hewan ->
                UpdateScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }
    }
}