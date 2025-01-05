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
        // Chama loadCart quando o utilizador for alterado
        auth.addAuthStateListener { firebaseAuth ->
            val userId = firebaseAuth.currentUser?.uid
            if (userId != null) {
                loadCart(userId)  // Recarregar o carrinho para o novo utilizador
            } else {
                _cartItems.clear()  // Se não estiver autenticado, limpa os itens
            }
        }

        val currentUserId = auth.currentUser?.uid
        currentUserId?.let {
            loadCart(it) // Carregar o carrinho do utilizador atual ao iniciar o ViewModel
        }
    }

    // Carregar o carrinho baseado no userId
    private fun loadCart(userId: String) {
        Log.d("CartViewModel", "A carregar o carrinho para o utilizador: $userId")

        val cartRef = db.collection("carts").document(userId)

        cartRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    Log.d("CartViewModel", "Carrinho encontrado para o utilizador: $userId")

                    val cart = document.toObject(CartData::class.java)
                    cart?.products?.let { cartItemsList ->
                        Log.d("CartViewModel", "Produtos no carrinho: ${cartItemsList.size}")

                        _cartItems.clear()
                        cartItemsList.forEach { cartItem ->
                            Log.d("CartViewModel", "A carregar produto com ID: ${cartItem.productId}")

                            // Agora, buscamos pelo campo 'productId' no documento
                            db.collection("products")
                                .whereEqualTo("productName", cartItem.productId)  // Supondo que o campo que guarda o nome do produto seja 'productName'
                                .get()
                                .addOnSuccessListener { querySnapshot ->
                                    if (!querySnapshot.isEmpty) {
                                        val product = querySnapshot.documents[0].toObject(Product::class.java)
                                        if (product != null) {
                                            Log.d("CartViewModel", "Produto encontrado: ${product.name}")
                                            _cartItems.add(CartProduct(product, cartItem.quantity))
                                        }
                                    } else {
                                        Log.e("CartViewModel", "Produto não encontrado com nome: ${cartItem.productId}")
                                    }
                                }
                                .addOnFailureListener { e ->
                                    Log.e("CartViewModel", "Erro ao carregar produto com ID: ${cartItem.productId}, Erro: ${e.message}")
                                }
                        }
                    } ?: Log.e("CartViewModel", "Carrinho não contém produtos.")
                } else {
                    Log.d("CartViewModel", "Carrinho vazio para o utilizador: $userId")
                    _cartItems.clear()
                }
            }
            .addOnFailureListener { e ->
                Log.e("CartViewModel", "Erro ao carregar o carrinho para o utilizador: $userId, Erro: ${e.message}")
            }
    }



    // Adicionar produto ao carrinho
    fun addToCart(product: Product) {
        val existingProduct = _cartItems.find { it.product.name == product.name }
        if (existingProduct != null) {
            increaseQuantity(existingProduct)
        } else {
            _cartItems.add(CartProduct(product, quantity = 1))
        }
        saveCart()
    }

    // Aumentar a quantidade de um produto
    fun increaseQuantity(cartProduct: CartProduct) {
        val updatedProduct = cartProduct.copy(quantity = cartProduct.quantity + 1)
        _cartItems[_cartItems.indexOf(cartProduct)] = updatedProduct
        saveCart()
    }

    // Diminuir a quantidade de um produto
    fun decreaseQuantity(cartProduct: CartProduct) {
        if (cartProduct.quantity > 1) {
            val updatedProduct = cartProduct.copy(quantity = cartProduct.quantity - 1)
            _cartItems[_cartItems.indexOf(cartProduct)] = updatedProduct
        } else {
            // Se a quantidade for 1, remove o produto
            _cartItems.remove(cartProduct)
        }
        saveCart()
    }


    // Salvar o carrinho no Firestore
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
}
