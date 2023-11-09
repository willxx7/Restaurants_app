package com.willy.restaurantsapp.restaurants.data

import com.willy.restaurantsapp.RestaurantApplication
import com.willy.restaurantsapp.restaurants.data.local.LocalRestaurant
import com.willy.restaurantsapp.restaurants.data.local.PartialLocalRestaurant
import com.willy.restaurantsapp.restaurants.data.local.RestaurantDb
import com.willy.restaurantsapp.restaurants.data.remote.RestaurantsApiService
import com.willy.restaurantsapp.restaurants.domain.Restaurant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ConnectException
import java.net.UnknownHostException

class RestaurantRepository {

    private var restInterface: RestaurantsApiService = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://restaurants-db-default-rtdb.firebaseio.com/")
        .build()
        .create(RestaurantsApiService::class.java)

    private var restaurantsDao =
        RestaurantDb.getDaoInstance(RestaurantApplication.getAppContext())

    suspend fun loadRestaurants() {
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

        }
    }


    private suspend fun refreshCache() {
        val remoteRestaurants = restInterface.getRestaurants()

        val favoriteRestaurants = restaurantsDao.getAllFavorited()

        restaurantsDao.addAll(remoteRestaurants.map{
            LocalRestaurant(
                it.id,
                it.title,
                it.description,
                false

            )

        })

        restaurantsDao.updateAll(
            favoriteRestaurants.map {

                PartialLocalRestaurant(it.id, true)
            }
        )


    }


    suspend fun toggleFavoriteRestaurant(
        id: Int,
        value: Boolean,
    ) = withContext(Dispatchers.IO) {

        restaurantsDao.update(PartialLocalRestaurant(id = id, isFavorite = value))



    }
    suspend fun getRestaurants(): List<Restaurant>{

        return withContext(Dispatchers.IO){

           return@withContext restaurantsDao.getAll().map{

               Restaurant(
                   it.id,
                   it.title,
                   it.description

               )
           }
        }

    }






}