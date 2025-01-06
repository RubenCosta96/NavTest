package com.example.navtest.domain.model

data class CartData(
    val products: List<CartItemData> = emptyList(),
    val isShared: Boolean = false,
    val sharedByUserId: String? = null
){
    constructor() : this(emptyList())
}