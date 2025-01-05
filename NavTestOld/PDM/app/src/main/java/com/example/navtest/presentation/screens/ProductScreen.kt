package com.example.navtest.presentation.screens

import android.widget.Toast
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.navtest.domain.model.Product
import com.example.navtest.presentation.navigation.Destinations
import com.example.navtest.presentation.viewmodel.CartViewModel
import com.example.navtest.presentation.viewmodel.ProductViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProductScreen(navController: NavController, cartViewModel: CartViewModel = viewModel(), productViewModel: ProductViewModel = viewModel()) {
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser

    LaunchedEffect(Unit) {
        if(currentUser == null){
            Toast.makeText(navController.context, "Por favor, faça login", Toast.LENGTH_SHORT).show()
            navController.navigate(Destinations.Login.route)
        }
    }

    if(currentUser != null) {
        val products by productViewModel.productsFlow.collectAsState()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item {
                Text(text = "Página de Produtos", style = MaterialTheme.typography.headlineSmall)
            }

            if (products.isEmpty()) {
                item {
                    Text("Carregando produtos...")
                }
            } else {
                items(products) { product ->
                    ProductItem(product, cartViewModel)
                }
            }
            item {
                Button(
                    onClick = {
                        navController.navigate(Destinations.Cart.route)
                    },
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text("Ver Carrinho")
                }
            }

            item {
                Button(
                    onClick = {
                        auth.signOut()
                        navController.navigate(Destinations.Login.route)
                    },
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text("Logout")
                }
            }
        }
    }
}

@Composable
fun ProductItem(product: Product, cartViewModel: CartViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = product.image,
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
                text = product.name,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "Preço: ${product.price} €",
                style = MaterialTheme.typography.bodyMedium
            )

        }
        Button(
            onClick = { cartViewModel.addToCart(product) },
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text("Adicionar")
        }
    }
}