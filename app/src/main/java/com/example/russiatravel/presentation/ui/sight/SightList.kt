package com.example.russiatravel.presentation.ui.sight

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.example.russiatravel.presentation.ui.Route
import com.example.russiatravel.ui.theme.ColorBlueDark
import com.example.russiatravel.viewModel.SightViewModel
import com.google.accompanist.coil.rememberCoilPainter

@Composable
fun SightList(
    navController: NavController,
    viewModel: SightViewModel = hiltNavGraphViewModel(),
    localityId: Int? = null,
    latitude: Float? = null,
    longitude: Float? = null
) {
    val sights = viewModel.sights.observeAsState()

    val sightRequest = remember (LocalContext.current){
        if (sights.value.isNullOrEmpty()) {
            if (localityId != null){
                viewModel.fetchSights(localityId)
            }else{
                viewModel.fetchSights(latitude!!, longitude!!)
            }
        }
        true
    }

    if (viewModel.isLoading.value) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(color = ColorBlueDark)
        }
    } else {
        LazyColumn {
            if (sights.value?.isNotEmpty() == true) {
                items(items = sights.value!!) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxSize()
                            .height(200.dp)
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                    ) {
                        Box(
                            Modifier
                                .fillMaxSize()
                                .clickable {
                                    navController.navigate(Route.SightDetail.id + "/${item.id}")
                                }
                        ) {
                            Image(
                                painter = rememberCoilPainter(
                                    request = item.images[0],
                                    fadeIn = true,
                                ),
                                contentDescription = "",
                                contentScale = ContentScale.Crop
                            )
                            if (item.distance != null){
                                Surface(
                                    modifier = Modifier
                                        .align(Alignment.TopStart)
                                        .padding(top = 12.dp, start = 12.dp),
                                    color = ColorBlueDark,
                                    shape = RoundedCornerShape(4.dp)
                                ) {
                                    Text(
                                        modifier = Modifier.padding(4.dp),
                                        text = if (item.distance > 0) "${item.distance} км" else "${item.distance/1000} м",
                                        style = MaterialTheme.typography.button.copy(color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                    )
                                }
                            }
                            Text(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(bottom = 16.dp, start = 12.dp),
                                text = item.title,
                                style = MaterialTheme.typography.button.copy(color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                }
            }

        }
    }
}
