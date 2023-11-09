package com.willy.restaurantsapp.restaurants.domain

import com.willy.restaurantsapp.restaurants.data.RestaurantRepository

class GetSortedRestaurantUseCase {

    private val repository: RestaurantRepository = RestaurantRepository()
    suspend operator fun invoke(): List<Restaurant>{

        return repository.getRestaurants()
            .sortedBy { it.title }
    }
}