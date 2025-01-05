package com.example.navtest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.navtest.domain.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProductViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    private val _productsFlow = MutableStateFlow<List<Product>>(emptyList())
    val productsFlow: StateFlow<List<Product>> = _productsFlow

    init {
        listenForProductChanges()
    }

    private fun listenForProductChanges() {
        val productsRef = db.collection("product")

        productsRef.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                return@addSnapshotListener
            }

            val productList = mutableListOf<Product>()
            snapshot?.documents?.forEach { document ->
                val product = document.toObject(Product::class.java)
                product?.let { productList.add(it) }
            }
            _productsFlow.value = productList
        }
    }
}