package com.example.navtest.presentation.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.navtest.presentation.navigation.Destinations
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProductScreen(navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser?.uid

    LaunchedEffect(Unit) {
        if(currentUser == null){
            Toast.makeText(navController.context, "Por favor, faça login", Toast.LENGTH_SHORT).show()
            navController.navigate(Destinations.Login.route)
        }
    }
    if(currentUser != null) {
        Log.d("TesteErro", "Userid: $currentUser")
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = "Página de Produtos", style = MaterialTheme.typography.headlineMedium)

            Button(
                onClick = {  },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Produto 1 - Adicionar ao Carrinho")
            }
            Button(
                onClick = {  },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Produto 2 - Adicionar ao Carrinho")
            }

            Button(
                onClick = { navController.navigate(Destinations.Login.route) },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Voltar para o Login")
            }
        }
    }
}