package com.example.navtest.presentation.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.navtest.domain.model.Product

class CartViewModel : ViewModel() {
    private val _cartItems = mutableStateListOf<Product>()
    val cartItems: List<Product> get() = _cartItems

    fun addToCart(product: Product) {
        _cartItems.add(product)
    }
    fun removeFromCart(product: Product) {
        _cartItems.remove(product)
    }
}