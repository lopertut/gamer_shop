package com.app.gamer_shop.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.gamer_shop.models.Product
import com.app.gamer_shop.repositories.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ProductViewModel @Inject constructor(private val productRepository: ProductRepository) : ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private val _product = MutableStateFlow<Product?>(null)
    val product: StateFlow<Product?> = _product

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadProducts() {
        viewModelScope.launch {
            try {
                _error.value = null
                val result = productRepository.fetchProducts()
                _products.value = result
            } catch (e: Exception) {
                _error.value = "Failed to load products: ${e.message}"
            }
        }
    }

    fun loadProductById(id: Int) {
        viewModelScope.launch {
            try {
                _error.value = null
                val result = productRepository.fetchProductById(id)
                _product.value = result
            } catch (e: Exception) {
                _error.value = "Failed to load product: ${e.message}"
            }
        }
    }
}