package com.example.navtest.presentation.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.navtest.presentation.navigation.Destinations
import com.example.navtest.presentation.viewmodel.CartViewModel

@Composable
fun ProductScreen(navController: NavController, userId: String) {
    val products = listOf("Produto 1", "Produto 2", "Produto 3") // Exemplo de dados
    val cartViewModel: CartViewModel = hiltViewModel() // Aceder ao carrinho

    Log.d("ProductScreenDebug", "userId recebido: $userId")

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        items(products) { product ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable { cartViewModel.addItemToCart(userId, mapOf("name" to product)) },
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = product, fontSize = 20.sp)
                Button(onClick = { cartViewModel.addItemToCart(userId, mapOf("name" to product)) }) {
                    Text("Adicionar ao Carrinho")
                }
            }
        }
    }

    Button(
        onClick = { navController.navigate(Destinations.Cart.createRoute(userId)) }, // Navegar para o carrinho
        modifier = Modifier.padding(16.dp)
    ) {
        Text("Ver Carrinho")
    }
}
