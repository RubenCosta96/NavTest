package com.example.navtest.domain.model

data class CartItemData(
    val productId: String,
    val quantity: Int
){
    constructor() : this("", 0)
}