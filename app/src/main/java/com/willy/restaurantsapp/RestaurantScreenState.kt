package com.willy.restaurantsapp

data class RestaurantScreenState(
    val restaurants: List<Restaurant>,
    val isLoading: Boolean,
    val error: String? = null
)
