package com.example.navtest.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.navtest.domain.model.Product
import com.google.firebase.firestore.FirebaseFirestore

class ProductViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    val productsLiveData = MutableLiveData<List<Product>>()

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
            productsLiveData.value = productList
        }
    }
}