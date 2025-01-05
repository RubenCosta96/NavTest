package com.example.navtest.presentation.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.navtest.domain.model.CartProduct
import com.example.navtest.domain.model.Product

class CartViewModel : ViewModel() {
    private val _cartItems = mutableStateListOf<CartProduct>()
    val cartItems: List<CartProduct> get() = _cartItems

    fun addToCart(product: Product) {
        val existingProduct = _cartItems.find { it.product.name == product.name }

        if (existingProduct != null) {
            increaseQuantity(existingProduct)
        } else {
            _cartItems.add(CartProduct(product, quantity = 1))
        }
    }

    fun increaseQuantity(cartProduct: CartProduct) {
        val updatedProduct = cartProduct.copy(quantity = cartProduct.quantity + 1)
        _cartItems[_cartItems.indexOf(cartProduct)] = updatedProduct
    }

    fun decreaseQuantity(cartProduct: CartProduct) {
        if (cartProduct.quantity > 1) {
            val updatedProduct = cartProduct.copy(quantity = cartProduct.quantity - 1)
            _cartItems[_cartItems.indexOf(cartProduct)] = updatedProduct
        } else {
            // Se a quantidade for 1, remove o produto
            _cartItems.remove(cartProduct)
        }
    }


    fun removeFromCart(cartProduct: CartProduct) {
        _cartItems.remove(cartProduct)
    }
}