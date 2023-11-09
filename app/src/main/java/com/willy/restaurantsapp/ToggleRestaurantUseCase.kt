package com.willy.restaurantsapp

class ToggleRestaurantUseCase {
    private val repository: RestaurantRepository = RestaurantRepository()
    private val getSortedRestaurantsUseCase: GetSortedRestaurantUseCase = GetSortedRestaurantUseCase()


    suspend operator fun invoke(
        id: Int,
        oldValue: Boolean,
    ): List<Restaurant> {

        val newFav = oldValue.not()
         repository.toggleFavoriteRestaurant(id, newFav)
        return getSortedRestaurantsUseCase()


    }
}