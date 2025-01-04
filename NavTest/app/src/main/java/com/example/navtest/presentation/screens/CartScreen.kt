package com.example.navtest.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.navtest.presentation.viewmodel.CartViewModel


@Composable
fun CartScreen(userId: String, viewModel: CartViewModel = hiltViewModel()) {
    val cartItems = viewModel.cartItems.value

    LaunchedEffect(Unit) {
        viewModel.fetchCart(userId)
    }

    Column {
        Text("Carrinho de Compras", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        LazyColumn {
            items(cartItems) { item ->
                Text(text = item["name"].toString())
            }
        }

        Button(onClick = {
            viewModel.addItemToCart(userId, mapOf("name" to "Novo Produto"))
        }) {
            Text("Adicionar Item")
        }
    }
}
