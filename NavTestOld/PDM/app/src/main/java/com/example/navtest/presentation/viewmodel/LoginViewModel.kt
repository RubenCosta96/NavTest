package com.example.navtest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    fun loginUser(email:String, password: String, onResult: (Boolean,String) -> Unit){
        viewModelScope.launch {
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    onResult(true, "Login bem-sucedido")
                }else{
                    onResult(false,"Falha no login: ${task.exception?.message.orEmpty()}")
                }
            }
        }
    }
}