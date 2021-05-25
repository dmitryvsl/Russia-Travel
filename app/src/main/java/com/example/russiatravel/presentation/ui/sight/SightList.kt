package com.example.russiatravel.presentation.ui.sight

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
    localityId: Int
) {
    val sights = viewModel.sights.observeAsState()

    if (sights.value.isNullOrEmpty()) {
        viewModel.fetchSights(localityId)
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
                                .clickable {navController.navigate(Route.SightDetail.id + "/${item.id}") }
                        ) {
                            Image(
                                painter = rememberCoilPainter(
                                    request = item.images[0],
                                    fadeIn = true,
                                ),
                                contentDescription = "",
                                contentScale = ContentScale.Crop
                            )
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