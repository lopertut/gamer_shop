package com.app.gamer_shop.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.gamer_shop.api.OrderRequest
import com.app.gamer_shop.repositories.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val orderRepository: OrderRepository
) : ViewModel() {

    private val _orderResult = MutableSharedFlow<Boolean>()
    val orderResult = _orderResult.asSharedFlow()

    fun createOrder(
        country: String,
        address: String,
        postalCode: String,
        firstname: String,
        lastname: String,
        email: String,
        totalPrice: Float
    ) {
        viewModelScope.launch {
            try {
                val request = OrderRequest(
                    country = country,
                    address = address,
                    postalCode = postalCode,
                    firstname = firstname,
                    lastname = lastname,
                    email = email,
                    totalPrice = totalPrice
                )
                val success = orderRepository.createOrder(request)
                _orderResult.emit(success)
            } catch (e: Exception) {
                _orderResult.emit(false)
            }
        }
    }
}
