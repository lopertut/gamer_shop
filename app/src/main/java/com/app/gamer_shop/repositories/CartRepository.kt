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

    suspend fun addCartItem(cartId: Int, productId: Int, quantity: Int): Boolean {
        val response = api.addCartItem(AddRequest(cartId, productId, quantity))

        return response.status != ""
    }

    suspend fun deleteCartIem(id: Int): Boolean {
        val response = api.deleteCartItem(id)

        return response.status != ""
    }
}