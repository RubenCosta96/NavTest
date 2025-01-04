package com.example.navtest.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.navtest.presentation.navigation.Destinations
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(navController: NavController) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    val auth = FirebaseAuth.getInstance()

    fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    Toast.makeText(navController.context, "Login bem-sucedido", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(navController.context, "Falha no login: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Iniciar Sessão")

        // Campo de Email
        TextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") },
            modifier = Modifier.padding(bottom = 8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            )
        )

        // Campo de Senha
        TextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Senha") },
            modifier = Modifier.padding(bottom = 16.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            visualTransformation = PasswordVisualTransformation()
        )

        // Botão de Login
        Button(onClick = { loginUser(email.value,password.value)

        }) {
            Text("Iniciar Sessão")
        }

        // Navegação para a tela de Registo
        Button(
            onClick = { navController.navigate(Destinations.Register.route) },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Ainda não tem conta? Registe-se")
        }
    }
}