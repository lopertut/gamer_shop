package com.app.gamer_shop.models

import kotlinx.serialization.Serializable

@Serializable
data class Product(val name: String, val price: String, val type: String)