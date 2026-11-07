
package com.example.outletfashion.data.model

data class CartItem(
    val product: Product,
    val size: String,
    val quantity: Int = 1
)
