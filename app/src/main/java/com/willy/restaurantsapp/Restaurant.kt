package com.willy.restaurantsapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "restaurants")
data class Restaurant(


    @PrimaryKey
    @ColumnInfo(name = "r_id")
    @SerializedName("r_id")
    val id: Int,

    @ColumnInfo(name = "r_title")
    @SerializedName("r_title")
    val title: String,


    @ColumnInfo(name = "r_description")
    @SerializedName("r_description")
    val description: String,

    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean = false
)


//val dummyRestaurants = listOf(
//
//    Restaurant(0,"Alfredo's Dishes","At Alfredo's we provide the best seafood dishes."),
//    Restaurant(1, "Jamie's Burgers","At Jamie's ,we serve the best vegan and meat burgers."),
//    Restaurant(2,"Pizza John","Get the best pizzas in town. We also serve vegan burgers."),
//    Restaurant(3,"Dinner in the clouds","At Ditc, you can experience the full immersive dining experience."),
//    Restaurant(4,"Eterniy Lunch", "At Eternity lunch, you'll get the best american dishes."),
//    Restaurant(5,"Eternity Lunch", "At Eternity lunch, you'll get the best american dishes."),
//    Restaurant(6,"Etrnity Lunch", "At Eternity lunch, you'll get the best american dishes."),
//    Restaurant(7,"Eternity Lunch", "At Eternity lunch, you'll get the best american dishes."),
//    Restaurant(8,"Etenity Lunch", "At Eternity lunch, you'll get the best american dishes."),
//    Restaurant(9,"Eterity Lunch", "At Eternity lunch, you'll get the best american dishes."),
//    Restaurant(10,"Eternit Lunch", "At Eternity lunch, you'll get the best american dishes.")
//
//
//
//)