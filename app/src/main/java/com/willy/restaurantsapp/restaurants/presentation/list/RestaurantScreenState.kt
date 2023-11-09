package com.willy.restaurantsapp.restaurants.presentation.list

import com.willy.restaurantsapp.restaurants.domain.Restaurant

data class RestaurantScreenState(
    val restaurants: List<Restaurant>,
    val isLoading: Boolean,
    val error: String? = null
)
