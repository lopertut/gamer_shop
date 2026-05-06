package com.app.gamer_shop.api

import com.app.gamer_shop.models.CartItem
import com.app.gamer_shop.models.Order
import com.app.gamer_shop.models.OrderItem
import com.app.gamer_shop.models.Product
import com.app.gamer_shop.models.Review
import com.google.gson.annotations.SerializedName
import retrofit2.http.*

interface ApiService {
    @GET("products")
    suspend fun getProducts(): List<Product>

    @GET("products/category/{name}")
    suspend fun getProductsByCategory(@Path("name") categoryName: String): List<Product>

    @GET("products/search/{searchQuery}")
    suspend fun searchProducts(@Path("searchQuery") searchQuery: String): List<Product>

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): Product

    @GET("cart")
    suspend fun getCart(): List<CartItem>

    @POST("cartItem")
    suspend fun addCartItem(@Body request: AddRequest): Response

    @DELETE("cartItem/{id}")
    suspend fun deleteCartItem(@Path("id") id: Int): Response

    @PUT("cartItem/increase/{id}")
    suspend fun increaseCartItem(@Path("id") id: Int): Response

    @PUT("cartItem/decrease/{id}")
    suspend fun decreaseCartItem(@Path("id") id: Int): Response

    @POST("login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("registration")
    suspend fun registration(@Body request: RegistrationRequest): RegistrationResponse

    @POST("order")
    suspend fun createOrder(@Body request: OrderRequest): Response

    @GET("order")
    suspend fun getOrders(): List<Order>

    @GET("orderItems/{id}")
    suspend fun getOrderItems(@Path("id") id: Int): List<OrderItem>

    @GET("reviews/{product_id}")
    suspend fun getReviews(@Path("product_id") productId: Int): List<Review>

    @POST("reviews")
    suspend fun addReview(@Body request: ReviewRequest): Response
}

data class ReviewRequest(val productId: Int, val rating: Int, val comment: String)

data class OrderRequest(
    val country: String,
    val address: String,
    @SerializedName("postal_code") val postalCode: String,
    @SerializedName("firstname") val firstname: String,
    @SerializedName("lastname") val lastname: String,
    val email: String,
    val totalPrice: Float
)

data class LoginRequest(val email: String, val password: String)
data class RegistrationRequest(val name: String, val email: String, val password: String)
data class LoginResponse(val token: String)
data class RegistrationResponse(val status: Boolean)
data class AddRequest(val productId: Int, val quantity: Int)
data class Response(val status: String)
