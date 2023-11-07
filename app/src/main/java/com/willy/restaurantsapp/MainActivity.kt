package com.willy.restaurantsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
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
                RestaurantsScreen { id ->
                    navController.navigate("restaurants/$id")
                }
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


@Composable
fun ClickableButton() {

    Button(
        onClick = { /*TODO*/ },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Black,
            contentColor = Color.Magenta
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Text("Press Me!")

    }


}

@Composable
fun MyFloatingActionButton() {

    Box {
        Surface(

            modifier = Modifier.size(50.dp),
            color = Color.Green,
            content = {},
            shape = CircleShape
        )
        Text(
            text = "+",
            modifier = Modifier.align(Alignment.Center)
        )
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