package com.example.russiatravel.presentation.ui.sight

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
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
import com.example.russiatravel.network.model.Sight
import com.example.russiatravel.presentation.ui.Route
import com.example.russiatravel.presentation.ui.filter.FilterParameters
import com.example.russiatravel.ui.theme.ColorBlueDark
import com.example.russiatravel.viewModel.SightViewModel
import com.google.accompanist.coil.rememberCoilPainter
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SightList(
    navController: NavController,
    viewModel: SightViewModel = hiltNavGraphViewModel(),
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    localityId: Int? = null,
    latitude: Float? = null,
    longitude: Float? = null
) {
    val coroutine = rememberCoroutineScope()
    BottomSheetScaffold(
        modifier = Modifier
            .fillMaxWidth()
            .border(border = BorderStroke(1.dp, ColorBlueDark)),
        scaffoldState = bottomSheetScaffoldState,
        sheetPeekHeight = 0.dp,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetContent = {
            SheetContent{
                viewModel.removeSights()
                viewModel.fetchSights(it)
                coroutine.launch {
                    bottomSheetScaffoldState.bottomSheetState.collapse()
                }
            }
        }
    ) {
        SightListContent(
            navController,
            viewModel,
            localityId,
            latitude,
            longitude
        )
    }


}

@Composable
fun SheetContent(onApplyClick: (Int) -> Unit) {
    Column(
        Modifier.padding(top = 50.dp, bottom = 100.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FilterParameters{
            onApplyClick(it)
        }
    }
}

@Composable
fun SightListContent(
    navController: NavController,
    viewModel: SightViewModel = hiltNavGraphViewModel(),
    localityId: Int? = null,
    latitude: Float? = null,
    longitude: Float? = null
) {

    localityId?.let {
        LaunchedEffect(key1 = it){
            viewModel.fetchSights(it)
        }
    }
    latitude?.let {
        LaunchedEffect(key1 = it){
            viewModel.fetchSights(latitude, longitude!!)
        }
    }

    val sights = viewModel.sights.observeAsState()
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
                    SightCard(navController, item)
                }
            }

        }
    }
}

@Composable
fun SightCard(
    navController: NavController,
    sight: Sight
){
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
                    navController.navigate(Route.SightDetail.id + "/${sight.id}")
                }
        ) {
            Image(
                painter = rememberCoilPainter(
                    request = sight.images[0],
                    fadeIn = true,
                ),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            if (sight.distance != null) {
                Surface(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(top = 12.dp, start = 12.dp),
                    color = ColorBlueDark,
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(4.dp),
                        text = if (sight.distance > 0) "${sight.distance} км" else "${sight.distance / 1000} м",
                        style = MaterialTheme.typography.button.copy(
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
            Text(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(bottom = 16.dp, start = 12.dp),
                text = sight.title,
                style = MaterialTheme.typography.button.copy(
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

