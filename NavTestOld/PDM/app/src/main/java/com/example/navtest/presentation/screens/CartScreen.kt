package com.example.navtest.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.navtest.domain.model.CartProduct
import com.example.navtest.presentation.navigation.Destinations
import com.example.navtest.presentation.viewmodel.CartViewModel
import com.example.navtest.ui.components.TopBarGlobal
import com.example.navtest.ui.theme.poppinsFontFamily


@Composable
fun CartScreen(navController: NavController, cartViewModel: CartViewModel) {
    val cartItems by remember { mutableStateOf(cartViewModel.cartItems) }
    val total = cartViewModel.calculateTotal()

    Scaffold(
        topBar = {
            TopBarGlobal(
                title = "Carrinho",
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
                if (cartItems.isEmpty()) {
                    Text("O carrinho está vazio.")
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(cartItems) { product ->
                            CartItem(product, cartViewModel)
                        }
                        item {
                            Text(
                                text = "Total: ${"%.2f".format(total)} €",
                                style = MaterialTheme.typography.bodyLarge,
                                fontFamily = poppinsFontFamily,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                        item {
                            Button(
                                onClick = {
                                    cartViewModel.shareCart()
                                },
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth()
                            ) {
                                Text("Partilhar Carrinho")
                            }
                        }
                        item {
                            Button(
                                onClick = {
                                    navController.navigate(Destinations.SharedCarts.route)
                                },
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text("Ver Carrinhos Partilhados")
                            }
                        }
                    }
                }
            }
        }
    )
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
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = poppinsFontFamily,
            )
            Text(
                text = "Quantidade: ${cartProduct.quantity}",
                style = MaterialTheme.typography.bodySmall,
                fontFamily = poppinsFontFamily,
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