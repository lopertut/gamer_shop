package com.app.gamer_shop.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.gamer_shop.models.CartItem
import com.app.gamer_shop.repositories.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    fun loadCart() {
        viewModelScope.launch {
            try {
                val items = cartRepository.getCart()
                _cartItems.value = items
            } catch (e: Exception) {
                // Handle error (e.g., log it or update an error state)
            }
        }
    }

    fun increaseCartItem(item: CartItem) {
        viewModelScope.launch {
            try {
                val success = cartRepository.increaseCartItem(item.id)
                if (success) loadCart()
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun decreaseCartItem(item: CartItem) {
            viewModelScope.launch {
                try {
                    val success = cartRepository.decreaseCartItem(item.id)
                    if (success) loadCart()
                } catch (e: Exception) {
                    // Handle error
                }
            }
        }

    fun addToCart(productId: Int) {
        println(productId)
        viewModelScope.launch {
            try {
                cartRepository.addCartItem(productId = productId, quantity = 1)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun deleteCartItem(item: CartItem) {
        viewModelScope.launch {
            try {
                val success = cartRepository.deleteCartItem(item.id)
                if (success) loadCart()
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
