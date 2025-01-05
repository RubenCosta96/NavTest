package com.example.navtest.domain.model

data class CartItemData(
    val productId: String,  // ID do produto
    val quantity: Int       // Quantidade do produto no carrinho
){
    constructor() : this("", 0)
}