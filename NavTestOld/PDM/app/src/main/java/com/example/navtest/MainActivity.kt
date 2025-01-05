package com.example.navtest

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.navtest.presentation.navigation.AppNavigation
import com.example.navtest.presentation.navigation.Destinations
import com.example.navtest.ui.theme.NavTestTheme
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        FirebaseApp.initializeApp(this)


        db = FirebaseFirestore.getInstance()
        val currentUser = auth.currentUser

        setContent {
            NavTestTheme {
                AppNavigation(startDestination = if (currentUser != null) {
                    Destinations.Products.route
                } else {
                    Destinations.Login.route
                })
            }
        }
    }
}

