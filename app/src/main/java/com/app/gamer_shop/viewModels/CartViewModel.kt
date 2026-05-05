package com.app.gamer_shop.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.gamer_shop.models.CartItem
import com.app.gamer_shop.models.Product
import com.app.gamer_shop.repositories.CartRepository
import com.app.gamer_shop.repositories.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CartUiItem(
    val cartItem: CartItem,
    val product: Product
)

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartUiItem>>(emptyList())
    val cartItems: StateFlow<List<CartUiItem>> = _cartItems

    fun loadCart() {
        viewModelScope.launch {
            val items = cartRepository.getCart()
            val uiItems = items.mapNotNull { item ->
                try {
                    val product = productRepository.fetchProductById(item.productId)
                    CartUiItem(item, product)
                } catch (e: Exception) {
                    null
                }
            }
            _cartItems.value = uiItems
        }
    }

    fun incrementQuantity(item: CartUiItem) {
        viewModelScope.launch {
            val success = cartRepository.addCartItem(item.cartItem.cartId, item.cartItem.productId, item.cartItem.quantity + 1)
            if (success) loadCart()
        }
    }

    fun decrementQuantity(item: CartUiItem) {
        if (item.cartItem.quantity > 1) {
            viewModelScope.launch {
                val success = cartRepository.addCartItem(item.cartItem.cartId, item.cartItem.productId, item.cartItem.quantity - 1)
                if (success) loadCart()
            }
        } else {
            viewModelScope.launch {
                val success = cartRepository.deleteCartIem(item.cartItem.id)
                if (success) loadCart()
            }
        }
    }

    fun addToCart(productId: Int) {
        viewModelScope.launch {
            // Assuming cartId 1 for now or fetching it from a user session
            cartRepository.addCartItem(1, productId, 1)
        }
    }
}
