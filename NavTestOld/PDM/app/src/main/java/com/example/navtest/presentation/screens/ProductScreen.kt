package com.example.navtest.presentation.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = "Página de Produtos", style = MaterialTheme.typography.headlineSmall)

            if (products.isEmpty()) {
                Text("Carregando produtos...")
            } else {
                products.forEach { product ->
                    ProductItem(product, cartViewModel)
                }
            }

//            Button(
//                onClick = { navController.navigate(Destinations.Cart.route) },
//                modifier = Modifier.padding(top = 16.dp)
//            ) {
//                Text("Ver Carrinho")
//            }
            Button(
                onClick = { auth.signOut()
                          navController.navigate(Destinations.Login.route)},
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Logout")
            }
        }
    }
}

@Composable
fun ProductItem(product: Product, cartViewModel: CartViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        Text(text = product.name, style = MaterialTheme.typography.headlineSmall)
        Text(text = "Preço: ${product.price} €", style = MaterialTheme.typography.bodyMedium)

        Button(
            onClick = { cartViewModel.addToCart(product) },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Adicionar ao Carrinho")
        }
    }
}