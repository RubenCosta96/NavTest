package com.example.navtest.domain.model

data class CartData(
    val products: List<CartItemData> = emptyList()  // Lista de itens no carrinho
){
    constructor() : this(emptyList())
}