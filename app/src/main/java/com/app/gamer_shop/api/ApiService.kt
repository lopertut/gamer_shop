package com.app.gamer_shop.api

import com.app.gamer_shop.models.CartItem
import com.app.gamer_shop.models.Product
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @GET("products")
    suspend fun getProducts(): List<Product>

    @GET("products/{id}")
    suspend fun getProductById(id: Int): Product

    @GET("cart")
    suspend fun getCart(): List<CartItem>

    @POST("cartItem")
    suspend fun addCartItem(@Body request: AddRequest): AddResponse

    @DELETE("cartItem/{id}")
    suspend fun deleteCartItem(id: Int): DeleteResponse

    @POST("login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("registration")
    suspend fun registration(@Body request: RegistrationRequest): RegistrationResponse


}

data class LoginRequest(val email: String, val password: String)
data class RegistrationRequest(val name: String, val email: String, val password: String)
data class LoginResponse(val token: String)
data class RegistrationResponse(val status: Boolean)
data class AddRequest(val cartId: Int, val productId: Int, val quantity: Int)
data class AddResponse(val status: String)
data class DeleteResponse(val status: String)
