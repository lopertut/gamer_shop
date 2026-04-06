package com.app.gamer_shop.repositories

import com.app.gamer_shop.api.ApiClient
import com.app.gamer_shop.models.Product

object ProductRepository {
    suspend fun fetchProducts(): List<Product> {
        return ApiClient.api.getProducts()
    }
}