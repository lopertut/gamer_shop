package com.app.gamer_shop.models

data class Review(
    val id: Int = 0,
    val productId: Int,
    val userId: Int = 0,
    val rating: Int,
    val comment: String?,
    val username: String? = null
)
