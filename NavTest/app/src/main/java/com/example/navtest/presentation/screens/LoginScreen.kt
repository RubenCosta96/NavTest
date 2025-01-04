package com.example.navtest.presentation.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.navtest.R
import com.example.navtest.presentation.navigation.Destinations
import com.example.navtest.presentation.viewmodel.LoginState
import com.example.navtest.presentation.viewmodel.LoginViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel = viewModel()) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    val loginState = viewModel.loginState.value

    fun onLoginClick(){
        Log.d("AuthDebug", "Tentando fazer login com email: ${email.value} e senha: ${password.value}")
        viewModel.loginUser(email.value, password.value)
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment =  Alignment.CenterHorizontally) {

        Image(painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logotipo",
            modifier = Modifier
                .size(width = 600.dp, height = 350.dp)
                .padding(top = 60.dp, bottom = 80.dp))

        Text(text = "Iniciar Sessão",
            modifier = Modifier.padding(bottom = 16.dp),
            fontSize = 32.sp,
            fontWeight = FontWeight.W400
        )
        TextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") },
            modifier = Modifier.padding(top = 34.dp,bottom = 8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            )
        )
        TextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Senha") },
            modifier = Modifier.padding(bottom = 50.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            visualTransformation = PasswordVisualTransformation()
        )

        Button(
            onClick = {onLoginClick()},
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD8E4))
        ) {
            Text("Iniciar Sessão", color = Color(0xFF633B48))
        }

        when(loginState){
            is LoginState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
            }
            is LoginState.Success ->{
                LaunchedEffect(Unit){
                    navController.navigate(Destinations.Products.route)
                }
            }
            is LoginState.Error ->{
                Text(text = loginState.message, color = Color.Red, modifier = Modifier.padding(top = 16.dp))
            }
            else -> {}
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { navController.navigate(Destinations.Register.route) },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
        ) {
            Text("Ainda não tens conta? Regista-te")
        }
    }
}