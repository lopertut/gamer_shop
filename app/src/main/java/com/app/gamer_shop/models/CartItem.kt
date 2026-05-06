package com.app.gamer_shop.models

import kotlinx.serialization.Serializable

@Serializable
data class CartItem(val id: Int, val cartId: Int, val productId: Int, val quantity: Int, val name: String?, val price: Double, val images: List<String>? = emptyList(), val rating: Double)