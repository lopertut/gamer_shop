package com.app.gamer_shop.models

data class Order(
    val id: Int,
    val userId: Int,
    val totalPrice: Float,
    val status: String?,
    val createdAt: String?,
    val country: String?,
    val address: String?,
    val postalCode: String?,
    val firstname: String?,
    val lastname: String?,
    val email: String?
)
