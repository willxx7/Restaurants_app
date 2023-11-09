package com.willy.restaurantsapp.restaurants.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.willy.restaurantsapp.restaurants.presentation.details.RestaurantDetailsScreen
import com.willy.restaurantsapp.restaurants.presentation.list.RestaurantsScreen
import com.willy.restaurantsapp.restaurants.presentation.list.RestaurantsViewModel
import com.willy.restaurantsapp.ui.theme.RestaurantsAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // val viewModel by viewModels<RestaurantsViewModel>()
        setContent {
            RestaurantsAppTheme(darkTheme = true) {
                // A surface container using the 'background' color from the theme
                // RestaurantScreen()
                //RestaurantDetailsScreen()

                RestaurantApp()
            }
        }
    }

    @Composable
    private fun RestaurantApp() {

        val navController = rememberNavController()
        NavHost(navController, startDestination = "restaurants") {

            composable(route = "restaurants") {

                val viewModel: RestaurantsViewModel = viewModel()
                RestaurantsScreen(

                    state = viewModel.state.value,
                    onItemClick = { id ->

                        navController.navigate("restaurants/$id")
                    },
                    onFavoriteClick = { id, oldValue ->
                        viewModel.toggleFavorite(id, oldValue)


                    }
                )
                navController.navigate("restaurants/$id")

            }

            composable(
                route = "restaurants/{restaurant_id}",

                arguments = listOf(navArgument("restaurant_id") {
                    type = NavType.IntType
                }),

                deepLinks = listOf(navDeepLink {
                    uriPattern = "www.restaurantsapp.details.com/{restaurant_id}"


                })


            ) { RestaurantDetailsScreen() }


        }


    }


}


//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    RestaurantsAppTheme {
//        Column {
//            MyFloatingActionButton()
//
//        }
//    }
//}