package com.app.gamer_shop.repositories

import com.app.gamer_shop.api.ApiService
import com.app.gamer_shop.models.Product
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(private val api: ApiService) {
    suspend fun fetchProducts(): List<Product> {
        return api.getProducts()
    }

    suspend fun fetchProductById(id: Int): Product {
        return api.getProductById(id)
    }
}