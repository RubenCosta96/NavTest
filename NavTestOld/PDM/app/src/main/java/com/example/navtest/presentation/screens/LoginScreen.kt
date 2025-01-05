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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.navtest.presentation.navigation.Destinations
import com.example.navtest.presentation.viewmodel.LoginViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(navController: NavController, loginViewModel: LoginViewModel = viewModel()) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    val loginResult = remember { mutableStateOf<Pair<Boolean,String>>(false to "")}

    fun onLoginResult(success:Boolean, message:String){
        loginResult.value = success to message
        if(success){
            navController.navigate(Destinations.Products.route)
        }else{
            Toast.makeText(navController.context,message,Toast.LENGTH_SHORT).show()
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Iniciar Sessão")

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
            modifier = Modifier.padding(bottom = 16.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            visualTransformation = PasswordVisualTransformation()
        )

        // Botão de Login
        Button(onClick = { loginViewModel.loginUser(email.value,password.value, ::onLoginResult)

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