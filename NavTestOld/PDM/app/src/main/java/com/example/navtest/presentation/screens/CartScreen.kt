package com.example.navtest.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.navtest.presentation.viewmodel.CartViewModel
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import coil.compose.AsyncImage
import com.example.navtest.domain.model.CartProduct
import com.example.navtest.domain.model.Product


@Composable
fun CartScreen(navController: NavController, cartViewModel: CartViewModel) {
    val cartItems by remember { mutableStateOf(cartViewModel.cartItems)}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Carrinho",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (cartItems.isEmpty()) {
            Text("O carrinho está vazio.")
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(cartItems) { product ->
                    CartItem(product, cartViewModel)
                }
            }
        }

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .padding(top = 8.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text("Voltar")
        }
    }
}

@Composable
fun CartItem(cartProduct: CartProduct, cartViewModel: CartViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = cartProduct.product.image,
            contentDescription = "Imagem do produto",
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .padding(end = 8.dp)
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = cartProduct.product.name,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "Preço: ${cartProduct.product.price} €",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Quantidade: ${cartProduct.quantity}",
                style = MaterialTheme.typography.bodySmall
            )
        }

        Row {
            Button(
                onClick = { cartViewModel.decreaseQuantity(cartProduct) },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("-")
            }

            Button(
                onClick = { cartViewModel.increaseQuantity(cartProduct) },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("+")
            }
        }
    }
}