package com.example.navtest.presentation.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    // Estado para monitorizar o login
    private val _loginState = mutableStateOf<LoginState>(LoginState.Idle)
    val loginState: State<LoginState> get() = _loginState

    private val auth = FirebaseAuth.getInstance()

    // Função de login
    fun loginUser(email: String, password: String) {
        _loginState.value = LoginState.Loading

        // Adiciona um log para ver os dados de login
        Log.d("AuthDebug", "Tentando fazer login com o email: $email e senha: $password")

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    // Adiciona um log quando o login for bem-sucedido
                    Log.d("AuthDebug", "Login bem-sucedido! User ID: ${user?.uid}")
                    _loginState.value = LoginState.Success(user)
                } else {
                    // Adiciona um log quando o login falhar
                    Log.e("AuthDebug", "Falha no login: ${task.exception?.message ?: "Erro desconhecido"}")
                    _loginState.value = LoginState.Error(task.exception?.message ?: "Erro desconhecido")
                }
            }
    }

}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val user: FirebaseUser?) : LoginState()
    data class Error(val message: String) : LoginState()
}
