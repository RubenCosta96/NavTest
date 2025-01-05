package com.example.navtest.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.navtest.domain.model.CartData
import com.example.navtest.domain.model.CartItemData
import com.example.navtest.domain.model.CartProduct
import com.example.navtest.domain.model.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CartViewModel : ViewModel() {

    private val _cartItems = mutableStateListOf<CartProduct>()
    val cartItems: List<CartProduct> get() = _cartItems

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    init {
        auth.addAuthStateListener { firebaseAuth ->
            val userId = firebaseAuth.currentUser?.uid
            if (userId != null) {
                loadCart(userId)  // Carregar o carrinho caso o utilizador esteja autenticado
            } else {
                _cartItems.clear()  // Limpar o carrinho caso o utilizador não esteja autenticado
            }
        }
    }

    private fun loadCart(userId: String) {
        val cartRef = db.collection("carts").document(userId)

        cartRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val cart = document.toObject(CartData::class.java)
                    cart?.products?.let { cartItemsList ->

                        _cartItems.clear()
                        cartItemsList.forEach { cartItem ->

                            db.collection("product")
                                .whereEqualTo("name", cartItem.productId)
                                .get()
                                .addOnSuccessListener { querySnapshot ->
                                    if (!querySnapshot.isEmpty) {
                                        val product = querySnapshot.documents[0].toObject(Product::class.java)
                                        if (product != null) {
                                            _cartItems.add(CartProduct(product, cartItem.quantity))
                                        }
                                    } else {
                                        Log.w("CartViewModel", "Produto não encontrado com nome: ${cartItem.productId}")
                                    }
                                }
                                .addOnFailureListener { e ->
                                    Log.e("CartViewModel", "Erro ao carregar produto com nome: ${cartItem.productId}, Erro: ${e.message}")
                                }
                        }
                    }
                } else {
                    _cartItems.clear()
                }
            }
            .addOnFailureListener { e ->
                Log.e("CartViewModel", "Erro ao carregar o carrinho para o utilizador: $userId, Erro: ${e.message}")
            }
    }

    fun addToCart(product: Product) {
        val existingProduct = _cartItems.find { it.product.name == product.name }
        if (existingProduct != null) {
            increaseQuantity(existingProduct)
        } else {
            _cartItems.add(CartProduct(product, quantity = 1))
        }
        saveCart()
    }

    fun increaseQuantity(cartProduct: CartProduct) {
        val updatedProduct = cartProduct.copy(quantity = cartProduct.quantity + 1)
        _cartItems[_cartItems.indexOf(cartProduct)] = updatedProduct
        saveCart()
    }

    fun decreaseQuantity(cartProduct: CartProduct) {
        if (cartProduct.quantity > 1) {
            val updatedProduct = cartProduct.copy(quantity = cartProduct.quantity - 1)
            _cartItems[_cartItems.indexOf(cartProduct)] = updatedProduct
        } else {
            _cartItems.remove(cartProduct)
        }
        saveCart()
    }

    private fun saveCart() {
        val userId = auth.currentUser?.uid ?: return
        val cartRef = db.collection("carts").document(userId)

        val cartData = CartData(_cartItems.map { CartItemData(it.product.name, it.quantity) })
        cartRef.set(cartData)
            .addOnSuccessListener {
                Log.d("CartViewModel", "Carrinho salvo com sucesso!")
            }
            .addOnFailureListener { e ->
                Log.w("CartViewModel", "Erro ao salvar carrinho", e)
            }
    }

    fun calculateTotal(): Double {
        return _cartItems.sumOf { it.product.price * it.quantity }
    }

}
