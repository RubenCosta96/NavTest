package com.example.navtest.presentation.navigation

sealed class Destinations(val route: String) {
    object Login : Destinations("login")
    object Register : Destinations("register")
    object Products : Destinations("products")
    object Cart : Destinations("cart/{userId}") { // Carrinho com ID do utilizador
        fun createRoute(userId: String) = "cart/$userId"
    }
}