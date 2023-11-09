package com.willy.restaurantsapp

class GetSortedRestaurantUseCase {

    private val repository: RestaurantRepository = RestaurantRepository()
    suspend operator fun invoke(): List<Restaurant>{

        return repository.getRestaurants()
            .sortedBy { it.title }
    }
}