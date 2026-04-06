package com.app.gamer_shop.views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.gamer_shop.models.Product
import com.app.gamer_shop.repositories.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    fun loadProducts() {
        viewModelScope.launch {
            val result = ProductRepository.fetchProducts()
            _products.value = result
        }
    }
}