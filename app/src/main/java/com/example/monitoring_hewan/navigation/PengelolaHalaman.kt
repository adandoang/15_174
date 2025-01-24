package com.example.monitoring_hewan.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.monitoring_hewan.view.hewanview.DestinasiDetail
import com.example.monitoring_hewan.view.hewanview.DestinasiEntry
import com.example.monitoring_hewan.view.hewanview.DestinasiHome
import com.example.monitoring_hewan.view.hewanview.DestinasiUpdate
import com.example.monitoring_hewan.view.hewanview.DetailScreen
import com.example.monitoring_hewan.view.hewanview.EntryHwnScreen
import com.example.monitoring_hewan.view.hewanview.HomeScreen
import com.example.monitoring_hewan.view.hewanview.UpdateScreen

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()){
    NavHost(
        navController=navController,
        startDestination = DestinasiHome.route,
        modifier = Modifier,
    ){
        composable(DestinasiHome.route){
            HomeScreen(
                navigateToItemEntry = {navController.navigate(DestinasiEntry.route)},
                onDetailClick = { id_hewan ->
                    navController.navigate("${DestinasiDetail.route}/$id_hewan")
                }
            )
        }
        composable(DestinasiEntry.route){
            EntryHwnScreen(navigateBack = {
                navController.navigate(DestinasiHome.route){
                    popUpTo(DestinasiHome.route){
                        inclusive = true
                    }
                }
            })
        }
        composable(DestinasiDetail.routesWithArg, arguments = listOf(navArgument(DestinasiDetail.ID_HEWAN) {
            type = NavType.StringType })
        ){
            val id_hewan = it.arguments?.getString(DestinasiDetail.ID_HEWAN)
            id_hewan?.let { id_hewan ->
                DetailScreen(
                    navigateToItemUpdate = { navController.navigate("${DestinasiUpdate.route}/$id_hewan") },
                    navigateBack = { navController.navigate(DestinasiHome.route) {
                        popUpTo(DestinasiHome.route) { inclusive = true }
                    }
                    }
                )
            }
        }
        composable(DestinasiUpdate.routesWithArg, arguments = listOf(navArgument(DestinasiDetail.ID_HEWAN){
            type = NavType.StringType })
        ){
            val id_hewan = it.arguments?.getString(DestinasiUpdate.ID_HEWAN)
            id_hewan?.let { id_hewan ->
                UpdateScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }
    }
}