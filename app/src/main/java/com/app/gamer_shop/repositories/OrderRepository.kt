package com.app.gamer_shop.repositories

import com.app.gamer_shop.api.ApiService
import com.app.gamer_shop.api.OrderRequest
import com.app.gamer_shop.models.Order
import com.app.gamer_shop.models.OrderItem
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class OrderRepository @Inject constructor(private val api: ApiService) {
    suspend fun createOrder(request: OrderRequest): Boolean {
        val response = api.createOrder(request)
        return response.status != ""
    }

    suspend fun fetchOrders(): List<Order> {
        return api.getOrders()
    }

    suspend fun fetchOrderItems(orderId: Int): List<OrderItem> {
        return api.getOrderItems(orderId)
    }
}
