package com.willy.restaurantsapp.restaurants.domain

import com.willy.restaurantsapp.restaurants.data.RestaurantRepository

class GetInitialRestaurantsUseCase {
    private val repository: RestaurantRepository = RestaurantRepository()
    private val getSortedRestaurantUseCase = GetSortedRestaurantUseCase()
     suspend operator fun invoke(): List<Restaurant>{

         repository.loadRestaurants()
          return getSortedRestaurantUseCase()
    }






}