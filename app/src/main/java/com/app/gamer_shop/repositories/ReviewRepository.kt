package com.app.gamer_shop.repositories

import com.app.gamer_shop.api.ApiService
import com.app.gamer_shop.api.ReviewRequest
import com.app.gamer_shop.models.Review
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class ReviewRepository @Inject constructor(private val api: ApiService) {
    suspend fun getReviews(productId: Int): List<Review> {
        return api.getReviews(productId)
    }

    suspend fun addReview(productId: Int, rating: Int, comment: String): Boolean {
        val response = api.addReview(ReviewRequest(productId, rating, comment))
        return response.status != ""
    }
}
