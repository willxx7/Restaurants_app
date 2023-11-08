package com.willy.restaurantsapp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch


class RestaurantsViewModel : ViewModel() {

    private val repository = RestaurantRepository()





    val state = mutableStateOf(
        RestaurantScreenState(
            restaurants = listOf(),
            isLoading = true
        )

    )

    private val errorHandler = CoroutineExceptionHandler { _, exception ->

        exception.printStackTrace()
        state.value = state.value.copy(
            error = exception.message,
            isLoading = false


        )



    }


    init { getRestaurants() }

    private fun getRestaurants() {

        viewModelScope.launch(errorHandler) {

            val restaurants = repository.getAllRestaurants()

            state.value = state.value.copy(
                restaurants = restaurants,
                isLoading = false


            )


        }


    }





    fun toggleFavorite(id: Int, oldValue: Boolean) {
        viewModelScope.launch(errorHandler) {
            val updatedRestaurants = repository.toggleFavoriteRestaurant(id, oldValue)
            state.value = state.value.copy(restaurants = updatedRestaurants)
        }

    }



//    private fun storeSelection(item: Restaurant) {
//
//        val savedToggled = stateHandle.get<List<Int>?>(FAVORITES)
//            .orEmpty().toMutableList()
//
//        if (item.isFavorite) savedToggled.add(item.id)
//        else savedToggled.remove(item.id)
//        stateHandle[FAVORITES] = savedToggled
//
//
//    }
//
//
//    private fun List<Restaurant>.restoreSelections(): List<Restaurant> {
//        stateHandle.get<List<Int>?>(FAVORITES)?.let { selectedIds ->
//
//            val restaurantsMap = this.associateBy { it.id }
//                .toMutableMap()
//
//
//            selectedIds.forEach { id ->
//
//                val restaurant =
//                    restaurantsMap[id] ?: return@forEach
//
//                restaurantsMap[id] = restaurant.copy(isFavorite = true)
//            }
//            return restaurantsMap.values.toList()
//
//
//        }
//
//        return this
//
//
//    }
//
//    companion object {
//        const val FAVORITES = "favorites"
//
//    }
}

//    private fun getRestaurants() {
//        restaurantsCall = restInterface.getRestaurants()
//        restaurantsCall.enqueue(
//            object : Callback<List<Restaurant>> {
//
//                override fun onResponse(
//                    call: Call<List<Restaurant>>,
//                    response: Response<List<Restaurant>>
//                ) {
//                    response.body()?.let { restaurants ->
//
//                        state.value = restaurants.restoreSelections()
//
//
//                    }
//                }
//
//                override fun onFailure(call: Call<List<Restaurant>>, t: Throwable) {
//                    t.printStackTrace()
//                }
//            }
//
//
//        )
//
//
//    }


/*
*
* Cancelling the network request when the viewmodel is cleared as a result
of activity destruction to avoid memory leaks

 */





