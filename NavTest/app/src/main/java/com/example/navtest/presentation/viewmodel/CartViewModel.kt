package com.example.navtest.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.navtest.data.repository.CartRepository
import com.google.firebase.firestore.DocumentSnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: CartRepository
) : ViewModel() {
    private val _cartItems = mutableStateOf<List<DocumentSnapshot>>(emptyList())
    val cartItems: State<List<DocumentSnapshot>> get() = _cartItems

    fun fetchCart(userId: String) {
        repository.getCartItems(userId) { items ->
            _cartItems.value = items
        }
    }

    fun addItemToCart(userId: String, item: Map<String, Any>) {
        repository.addItemToCart(userId, item) { success ->
            if (success) {
                fetchCart(userId)
            } else {
                // Erro
            }
        }
    }
}
