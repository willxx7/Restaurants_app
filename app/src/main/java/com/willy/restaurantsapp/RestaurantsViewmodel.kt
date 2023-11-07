package com.willy.restaurantsapp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ConnectException
import java.net.UnknownHostException


class RestaurantsViewModel : ViewModel() {


    private var restInterface: RestaurantsApiService

    private var restaurantsDao =
        RestaurantDb.getDaoInstance(RestaurantApplication.getAppContext())


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


    init {

        val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://restaurants-db-default-rtdb.firebaseio.com/")
            .build()

        restInterface = retrofit.create(RestaurantsApiService::class.java)

        getRestaurants()

    }

    private fun getRestaurants() {

        viewModelScope.launch(errorHandler) {

            val restaurants = getAllRestaurants()

            state.value = state.value.copy(
                restaurants = restaurants,
                isLoading = false


            )


        }


    }

    private suspend fun getAllRestaurants(): List<Restaurant> {
        return withContext(Dispatchers.IO) {
            try {
                refreshCache()

            } catch (e: Exception) {
                when (e) {
                    is UnknownHostException,
                    is ConnectException,
                    is HttpException,
                    -> {
                        if (restaurantsDao.getAll().isEmpty())
                            throw Exception(
                                "Something went wrong. " +
                                        "We have no data"
                            )


                    }

                    else -> throw e

                }
            }

            return@withContext restaurantsDao.getAll()
        }
    }

    private suspend fun refreshCache() {
        val remoteRestaurants = restInterface.getRestaurants()

        val favoriteRestaurants = restaurantsDao.getAllFavorited()

        restaurantsDao.addAll(remoteRestaurants)

        restaurantsDao.updateAll(
            favoriteRestaurants.map {

                PartialRestaurant(it.id, true)
            }
        )


    }


    fun toggleFavorite(id: Int, oldValue: Boolean) {
        viewModelScope.launch(errorHandler) {
            val updatedRestaurants = toggleFavoriteRestaurant(id, oldValue)
            state.value = state.value.copy(restaurants = updatedRestaurants)
        }

    }

    private suspend fun toggleFavoriteRestaurant(
        id: Int,
        oldValue: Boolean,
    ) = withContext(Dispatchers.IO) {

        restaurantsDao.update(PartialRestaurant(id = id, isFavorite = !oldValue))
        restaurantsDao.getAll()


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





