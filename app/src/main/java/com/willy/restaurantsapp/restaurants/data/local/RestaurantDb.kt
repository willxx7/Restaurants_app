package com.willy.restaurantsapp.restaurants.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [LocalRestaurant::class],
    version = 3,
    exportSchema = false
)
abstract class RestaurantDb : RoomDatabase() {

    abstract val dao: RestaurantDao


    companion object {

        @Volatile
        private var INSTANCE: RestaurantDao? = null

        fun getDaoInstance(context: Context): RestaurantDao {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = buildDatabase(context).dao

                    INSTANCE = instance

                }
                return instance


            }


        }


        private fun buildDatabase(context: Context):
                RestaurantDb = Room.databaseBuilder(

            context.applicationContext,
            RestaurantDb::class.java,
            "restaurants_database"
        )
            .fallbackToDestructiveMigration()
            .build()


//            fun getRestaurantDatabase(context: Context): RestaurantDatabase{
//
//                synchronized(this){
//                    var instance = INSTANCE
//                    if (instance == null){
//                        instance = Room.databaseBuilder(context.applicationContext,
//                            RestaurantDatabase::class.java,
//                            "restaurant_db")
//                            .build()
//
//                        INSTANCE = instance
//
//                    }
//                    return instance
//
//
//                }
//
//
//
//        }


    }


}