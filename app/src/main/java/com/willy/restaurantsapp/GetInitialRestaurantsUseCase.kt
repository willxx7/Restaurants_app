package com.willy.restaurantsapp

class GetInitialRestaurantsUseCase {
    private val repository:RestaurantRepository = RestaurantRepository()
    private val getSortedRestaurantUseCase = GetSortedRestaurantUseCase()
     suspend operator fun invoke(): List<Restaurant>{

         repository.loadRestaurants()
          return getSortedRestaurantUseCase()
    }






}