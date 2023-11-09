package com.willy.restaurantsapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch


class RestaurantsViewModel : ViewModel() {


    private val getRestaurantsUseCase = GetInitialRestaurantsUseCase()
    private val toggleRestaurantsUseCase = ToggleRestaurantUseCase()


    private val _state = mutableStateOf(
        RestaurantScreenState(
            restaurants = listOf(),
            isLoading = true
        )

    )

    val state: State<RestaurantScreenState>
        get() = _state

    private val errorHandler = CoroutineExceptionHandler { _, exception ->

        exception.printStackTrace()
        _state.value = _state.value.copy(
            error = exception.message,
            isLoading = false


        )


    }


    init {
        getRestaurants()
    }

    private fun getRestaurants() {

        viewModelScope.launch(errorHandler) {

            val restaurants = getRestaurantsUseCase()

            _state.value = _state.value.copy(
                restaurants = restaurants,
                isLoading = false


            )


        }


    }


    fun toggleFavorite(id: Int, oldValue: Boolean) {
        viewModelScope.launch(errorHandler) {
            val updatedRestaurants = toggleRestaurantsUseCase(id, oldValue)
            _state.value = _state.value.copy(restaurants = updatedRestaurants)
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





