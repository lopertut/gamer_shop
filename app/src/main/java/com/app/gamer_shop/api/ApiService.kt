package com.app.gamer_shop.api

import com.app.gamer_shop.models.Product
import retrofit2.http.GET

interface ApiService {
    @GET("products")
    suspend fun getProducts(): List<Product>
}