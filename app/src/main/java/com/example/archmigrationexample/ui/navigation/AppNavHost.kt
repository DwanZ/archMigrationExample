package com.example.archmigrationexample.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.archmigrationexample.ui.detail.DetailRoute
import com.example.archmigrationexample.ui.home.HomeRoute

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {
        composable(Routes.HOME) {
            HomeRoute(
                onOpenDetail = { name ->
                    navController.navigate(Routes.detail(name))
                }
            )
        }
        composable(
            route = Routes.DETAIL,
            arguments = listOf(navArgument("name") { type = NavType.StringType })
        ) { entry ->
            val name = entry.arguments?.getString("name").orEmpty()
            DetailRoute(
                pokemonName = name,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
