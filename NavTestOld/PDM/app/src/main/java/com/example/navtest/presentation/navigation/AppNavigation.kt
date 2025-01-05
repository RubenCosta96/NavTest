package com.example.navtest.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.navtest.presentation.screens.LoginScreen
import com.example.navtest.presentation.screens.ProductScreen
import com.example.navtest.presentation.screens.RegisterScreen


@Composable
fun AppNavigation(startDestination: String) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Destinations.Login.route) { LoginScreen(navController) }
        composable(Destinations.Register.route) { RegisterScreen(navController) }
        composable(Destinations.Products.route) { ProductScreen(navController)}
    }
}