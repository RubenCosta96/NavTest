package com.example.navtest.data.repository

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class CartRepository @Inject constructor() {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()


    fun addItemToCart(userId: String, item: Map<String, Any>, onResult: (Boolean) -> Unit) {
        db.collection("carts").document(userId).collection("items")
            .add(item)
            .addOnCompleteListener { task ->
                onResult(task.isSuccessful)
            }
    }

    fun getCartItems(userId: String, onResult: (List<DocumentSnapshot>) -> Unit) {
        db.collection("carts").document(userId).collection("items")
            .get()
            .addOnSuccessListener { querySnapshot ->
                onResult(querySnapshot.documents)
            }
    }

    fun shareCart(userId: String, sharedWithUserId: String, onResult: (Boolean) -> Unit) {
        db.collection("carts").document(sharedWithUserId)
            .set(mapOf("sharedFrom" to userId))
            .addOnCompleteListener { task ->
                onResult(task.isSuccessful)
            }
    }

}
