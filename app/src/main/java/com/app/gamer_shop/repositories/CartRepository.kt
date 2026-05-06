package com.app.gamer_shop.repositories

import com.app.gamer_shop.api.AddRequest
import com.app.gamer_shop.api.ApiService
import com.app.gamer_shop.models.CartItem
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class CartRepository @Inject constructor(private val api: ApiService) {
    suspend fun getCart(): List<CartItem> {
        return api.getCart()
    }

    suspend fun addCartItem(productId: Int, quantity: Int): Boolean {
        println(productId)
        val response = api.addCartItem(AddRequest(productId, quantity))

        return response.status != ""
    }

    suspend fun deleteCartItem(id: Int): Boolean {
        val response = api.deleteCartItem(id)

        return response.status != ""
    }

    suspend fun increaseCartItem(id: Int): Boolean {
        val response = api.increaseCartItem(id)

        return response.status != ""
    }

    suspend fun decreaseCartItem(id: Int): Boolean {
        val response = api.decreaseCartItem(id)

        return response.status != ""
    }
}