package com.example.navtest.domain.model

data class CartProduct(
    val product: Product,
    var quantity: Int = 1
) {
    // Construtor sem argumentos para o Firebase
    constructor() : this(Product(), 0)
}