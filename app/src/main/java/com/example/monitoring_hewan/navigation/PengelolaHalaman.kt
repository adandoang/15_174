package com.example.monitoring_hewan.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.monitoring_hewan.view.DestinasiHOME
import com.example.monitoring_hewan.view.Home
import com.example.monitoring_hewan.view.hewanview.DestinasiDetailHewan
import com.example.monitoring_hewan.view.hewanview.DestinasiEntryHewan
import com.example.monitoring_hewan.view.hewanview.DestinasiHomeHewan
import com.example.monitoring_hewan.view.hewanview.DestinasiUpdateHewan
import com.example.monitoring_hewan.view.hewanview.DetailScreenHewan
import com.example.monitoring_hewan.view.hewanview.EntryScreenHewan
import com.example.monitoring_hewan.view.hewanview.HomeScreenHewan
import com.example.monitoring_hewan.view.hewanview.UpdateScreenHewan
import com.example.monitoring_hewan.view.kandangview.DestinasiDetailKandang
import com.example.monitoring_hewan.view.kandangview.DestinasiEntryKandang
import com.example.monitoring_hewan.view.kandangview.DestinasiHomeKandang
import com.example.monitoring_hewan.view.kandangview.DestinasiUpdateKandang
import com.example.monitoring_hewan.view.kandangview.DetailScreenKandang
import com.example.monitoring_hewan.view.kandangview.EntryScreenKandang
import com.example.monitoring_hewan.view.kandangview.HomeScreenKandang
import com.example.monitoring_hewan.view.kandangview.UpdateScreenKandang
import com.example.monitoring_hewan.view.petugasview.DestinasiDetailPetugas
import com.example.monitoring_hewan.view.petugasview.DestinasiEntryPetugas
import com.example.monitoring_hewan.view.petugasview.DestinasiHomePetugas
import com.example.monitoring_hewan.view.petugasview.DestinasiUpdatePetugas
import com.example.monitoring_hewan.view.petugasview.DetailScreenPetugas
import com.example.monitoring_hewan.view.petugasview.EntryScreenPetugas
import com.example.monitoring_hewan.view.petugasview.HomeScreenPetugas
import com.example.monitoring_hewan.view.petugasview.UpdateScreenPetugas

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()){
    NavHost(
        navController=navController,
        startDestination = DestinasiHOME.route,
        modifier = Modifier,
    ){
        composable(
            route = DestinasiHOME.route
        ) {
            Home (
                onButtonClickKdg = {
                    navController.navigate(DestinasiHomeKandang.route)
            },
                onButtonClickHwn = {
                    navController.navigate(DestinasiHomeHewan.route)
                },
                onButtonClickPtgs = {
                    navController.navigate(DestinasiHomePetugas.route)
                }
            )
        }
        composable(DestinasiHomeHewan.route){
            HomeScreenHewan(
                navigateToItemEntry = {navController.navigate(DestinasiEntryHewan.route)},
                onDetailClick = { id_hewan ->
                    navController.navigate("${DestinasiDetailHewan.route}/$id_hewan")
                },
                navigateBack = {
                    navController.popBackStack()
                },
            )
        }
        composable(DestinasiEntryHewan.route){
            EntryScreenHewan(navigateBack = {
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
                DetailScreenHewan(
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
                UpdateScreenHewan(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }
        composable(DestinasiHomeKandang.route){
            HomeScreenKandang(
                navigateToItemEntry = {navController.navigate(DestinasiEntryKandang.route)},
                onDetailClick = { id_kandang ->
                    navController.navigate("${DestinasiDetailKandang.route}/$id_kandang")
                },
                navigateBack = {
                    navController.popBackStack()
                },
            )
        }
        composable(DestinasiEntryKandang.route){
            EntryScreenKandang(navigateBack = {
                navController.navigate(DestinasiHomeKandang.route){
                    popUpTo(DestinasiHomeKandang.route){
                        inclusive = true
                    }
                }
            })
        }
        composable(DestinasiDetailKandang.routesWithArg, arguments = listOf(navArgument(DestinasiDetailKandang.ID_KANDANG) {
            type = NavType.StringType })
        ){
            val id_kandang = it.arguments?.getString(DestinasiDetailKandang.ID_KANDANG)
            id_kandang?.let { id_kandang ->
                DetailScreenKandang(
                    navigateToItemUpdate = { navController.navigate("${DestinasiUpdateKandang.route}/$id_kandang") },
                    navigateBack = { navController.navigate(DestinasiHomeKandang.route) {
                        popUpTo(DestinasiHomeKandang.route) { inclusive = true }
                    }
                    }
                )
            }
        }
        composable(DestinasiUpdateKandang.routesWithArg, arguments = listOf(navArgument(DestinasiDetailKandang.ID_KANDANG){
            type = NavType.StringType })
        ){
            val id_kandang = it.arguments?.getString(DestinasiUpdateKandang.ID_KANDANG)
            id_kandang?.let { id_kandang ->
                UpdateScreenKandang(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }


        composable(DestinasiHomePetugas.route){
            HomeScreenPetugas(
                navigateToItemEntry = {navController.navigate(DestinasiEntryPetugas.route)},
                onDetailClick = { id_petugas ->
                    navController.navigate("${DestinasiDetailPetugas.route}/$id_petugas")
                },
                navigateBack = {
                    navController.popBackStack()
                },
            )
        }
        composable(DestinasiEntryPetugas.route){
            EntryScreenPetugas(navigateBack = {
                navController.navigate(DestinasiHomePetugas.route){
                    popUpTo(DestinasiHomePetugas.route){
                        inclusive = true
                    }
                }
            })
        }
        composable(DestinasiDetailPetugas.routesWithArg, arguments = listOf(navArgument(DestinasiDetailPetugas.ID_PETUGAS) {
            type = NavType.StringType })
        ){
            val id_petugas = it.arguments?.getString(DestinasiDetailPetugas.ID_PETUGAS)
            id_petugas?.let { id_petugas ->
                DetailScreenPetugas(
                    navigateToItemUpdate = { navController.navigate("${DestinasiUpdatePetugas.route}/$id_petugas") },
                    navigateBack = { navController.navigate(DestinasiHomePetugas.route) {
                        popUpTo(DestinasiHomePetugas.route) { inclusive = true }
                    }
                    }
                )
            }
        }
        composable(DestinasiUpdatePetugas.routesWithArg, arguments = listOf(navArgument(DestinasiDetailPetugas.ID_PETUGAS){
            type = NavType.StringType })
        ){
            val id_petugas = it.arguments?.getString(DestinasiUpdatePetugas.ID_PETUGAS)
            id_petugas?.let { id_petugas ->
                UpdateScreenPetugas(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }
    }
}