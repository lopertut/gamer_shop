package com.app.gamer_shop.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.gamer_shop.models.Review
import com.app.gamer_shop.repositories.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository
) : ViewModel() {

    private val _reviews = MutableStateFlow<List<Review>>(emptyList())
    val reviews: StateFlow<List<Review>> = _reviews

    private val _addReviewResult = MutableSharedFlow<Boolean>()
    val addReviewResult = _addReviewResult.asSharedFlow()

    fun loadReviews(productId: Int) {
        viewModelScope.launch {
            try {
                val result = reviewRepository.getReviews(productId)
                _reviews.value = result
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun addReview(productId: Int, rating: Int, comment: String) {
        viewModelScope.launch {
            try {
                val success = reviewRepository.addReview(productId, rating, comment)
                _addReviewResult.emit(success)
                if (success) {
                    loadReviews(productId)
                }
            } catch (e: Exception) {
                _addReviewResult.emit(false)
            }
        }
    }
}
