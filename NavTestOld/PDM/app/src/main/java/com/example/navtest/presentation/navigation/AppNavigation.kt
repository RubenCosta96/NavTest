package com.example.navtest.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.navtest.presentation.screens.CartScreen
import com.example.navtest.presentation.screens.LoginScreen
import com.example.navtest.presentation.screens.ProductScreen
import com.example.navtest.presentation.screens.RegisterScreen
import com.example.navtest.presentation.viewmodel.CartViewModel


@Composable
fun AppNavigation(startDestination: String, cartViewModel: CartViewModel = viewModel()) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Destinations.Login.route) { LoginScreen(navController) }
        composable(Destinations.Register.route) { RegisterScreen(navController) }
        composable(Destinations.Products.route) { ProductScreen(navController, cartViewModel = cartViewModel)}
        composable(Destinations.Cart.route) { CartScreen(navController, cartViewModel) }
    }
}