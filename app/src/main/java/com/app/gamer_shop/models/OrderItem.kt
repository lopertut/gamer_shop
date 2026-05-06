package com.app.gamer_shop.models

data class OrderItem(
    val id: Int,
    val orderId: Int,
    val productId: Int,
    val quantity: Int,
    val price: Float,
    val productName: String? = null
)
