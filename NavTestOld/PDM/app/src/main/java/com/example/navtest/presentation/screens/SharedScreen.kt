package com.example.navtest.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.navtest.domain.model.CartData
import com.example.navtest.presentation.viewmodel.CartViewModel
import com.example.navtest.ui.components.TopBarGlobal

@Composable
fun SharedCartScreen(navController: NavController, cartViewModel: CartViewModel) {
    val sharedCarts by remember { mutableStateOf(cartViewModel.sharedCarts) }
    val cartItems by remember { mutableStateOf(cartViewModel.cartItems) }

    Scaffold(
        topBar = {
            TopBarGlobal(
                title = "Carrinhos Partilhados",
                navController = navController,
                cartItems = cartItems,
                showNavigationButton = true
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                if (sharedCarts.isEmpty()) {
                    Text("Não há carrinhos partilhados.")
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(sharedCarts) { cart ->
                            SharedCartItem(cart,navController)
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun SharedCartItem(cart: CartData, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable{
                navController.navigate("shared_cart_details/${cart.sharedByUserId}")
            }
    )
    {
        Text(text = "Carrinho Partilhado", style = MaterialTheme.typography.headlineSmall)
        Text("Produtos: ${cart.products.size}")
        Text("User Id: ${cart.sharedByUserId}")
    }
}
