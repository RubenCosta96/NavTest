package com.example.navtest.presentation.navigation

import AuthViewModel
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.navtest.presentation.screens.CartScreen
import com.example.navtest.presentation.screens.LoginScreen
import com.example.navtest.presentation.screens.ProductScreen
import com.example.navtest.presentation.screens.RegisterScreen


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()
    val userId by authViewModel.userId.observeAsState()
    Log.d("AppNavigationDebug", "userId obtido: $userId")

    if(userId !=null){
        NavHost(navController = navController, startDestination = Destinations.Login.route) {
            composable(Destinations.Login.route) { LoginScreen(navController) }
            composable(Destinations.Register.route) { RegisterScreen(navController) }
            composable(Destinations.Products.route) { ProductScreen(navController, userId) }
            composable(Destinations.Cart.route) { CartScreen(userId) }
        }
    }
    else{
        LoginScreen(navController)
    }
}