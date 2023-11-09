package com.willy.restaurantsapp.restaurants.presentation.details

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.willy.restaurantsapp.restaurants.presentation.list.RestaurantDetails
import com.willy.restaurantsapp.restaurants.presentation.list.RestaurantIcon

@Composable
fun RestaurantDetailsScreen() {

    val viewModel: RestaurantDetailsViewModel = viewModel()
    val item = viewModel.state.value
 
    val context = LocalContext.current

    if (item != null) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            RestaurantIcon(
                icon = Icons.Filled.Place, modifier = Modifier.padding(
                    top = 32.dp,
                    bottom = 32.dp
                )
            )

            RestaurantDetails(
                title = item.title,
                description = item.description,
                modifier = Modifier.padding(bottom = 32.dp),
                Alignment.CenterHorizontally)

            Text("More Info coming soon..")

        }


    } else {


        Toast.makeText(context,"No Internet Connection", Toast.LENGTH_LONG).show()
    }


}