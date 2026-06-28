package com.example.kycflow.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kycflow.presentation.screen.accounts.AccountsScreen

import com.example.kycflow.presentation.screen.accountdetails.AccountDetailsScreen

import com.example.kycflow.presentation.screen.camera.CameraScreen

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "accounts"
    ) {
        composable("accounts") {
            AccountsScreen(
                onNavigateToDetails = { customerId ->
                    navController.navigate("account_details/$customerId")
                }
            )
        }
        composable("account_details/{customerId}") { backStackEntry ->
            val customerId = backStackEntry.arguments?.getString("customerId")?.toIntOrNull() ?: 0
            AccountDetailsScreen(
                customerId = customerId,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToCamera = { navController.navigate("camera/$customerId") }
            )
        }
        composable("camera/{customerId}") { backStackEntry ->
            val customerId = backStackEntry.arguments?.getString("customerId")?.toIntOrNull() ?: 0
            CameraScreen(
                customerId = customerId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
