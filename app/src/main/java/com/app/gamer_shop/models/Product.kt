package com.app.gamer_shop.models

import kotlinx.serialization.Serializable

@Serializable
data class Product(val id: Int, val name: String, val price: Double, val specs: Map<String, Any>,
                   val images: List<String>? = emptyList(),
                   val category: String, val brand: String, val rating: Double)