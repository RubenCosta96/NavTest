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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun RegisterScreen(navController: NavController) {
    val auth = FirebaseAuth.getInstance()

    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }

    fun registerUserWithFirebase(email: String, password: String) {
        if (password == confirmPassword.value) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            navController.context,
                            "Registo realizado com sucesso.",
                            Toast.LENGTH_SHORT
                        ).show()
                        navController.navigate("login")
                    } else {
                        Toast.makeText(
                            navController.context,
                            "Erro no registo: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        } else {
            Toast.makeText(
                navController.context,
                "As senhas não coincidem.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Registo")

        TextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") },
            modifier = Modifier.padding(bottom = 8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            )
        )

        TextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Senha") },
            modifier = Modifier.padding(bottom = 8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation()
        )

        TextField(
            value = confirmPassword.value,
            onValueChange = { confirmPassword.value = it },
            label = { Text("Confirmar Senha") },
            modifier = Modifier.padding(bottom = 16.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation()
        )

        Button(onClick = {
            registerUserWithFirebase(email.value,password.value)
        }) {
            Text("Criar Conta")
        }
        Button(
            onClick = { navController.navigate("login") },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Já tem conta? Inicie sessão")
        }
    }
}