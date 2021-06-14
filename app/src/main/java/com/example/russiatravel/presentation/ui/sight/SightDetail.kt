package com.example.russiatravel.presentation.ui.sight

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavController
import com.example.russiatravel.cache.SharedPreferences
import com.example.russiatravel.network.model.Feedback
import com.example.russiatravel.network.model.FeedbackItem
import com.example.russiatravel.presentation.ui.Route
import com.example.russiatravel.presentation.ui.RussiaTravelApplication
import com.example.russiatravel.ui.theme.ColorBlueDark
import com.example.russiatravel.ui.theme.ColorGray
import com.example.russiatravel.ui.theme.ColorPurple
import com.example.russiatravel.utils.rememberMapViewWithLifecycle
import com.example.russiatravel.utils.setZoom
import com.example.russiatravel.viewModel.SightViewModel
import com.google.accompanist.coil.rememberCoilPainter
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.MapView
import com.google.android.libraries.maps.model.LatLng
import com.google.maps.android.ktx.addMarker
import com.google.maps.android.ktx.awaitMap
import kotlinx.coroutines.launch

enum class Tab {
    Map, Feedback
}

sealed class Tabs(val id: Tab, val name: String) {
    object Map : Tabs(Tab.Map, "Карта")
    object Feedback : Tabs(Tab.Feedback, "Отзывы")
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SightDetail(
    navController: NavController,
    sightId: Int,
    viewModel: SightViewModel = hiltNavGraphViewModel()
) {
    SideEffect {
        viewModel.getSightDetail(sightId)
        viewModel.getFeedbacks(sightId)
        viewModel.checkInBookmark(sightId)
    }

    var isBookmarkAdded = viewModel.checkInBookmark

    val sight = viewModel.sight.observeAsState()
    val feedbacks = viewModel.feedback.observeAsState()

    sight.value?.let {

        var selectedRating by remember { mutableStateOf(0) }

        var selectedTab: Tabs by remember { mutableStateOf(Tabs.Map) }
        val selectedTabIndex = when (selectedTab) {
            Tabs.Map -> 0
            Tabs.Feedback -> 1
        }
        val columnState = rememberLazyListState()
        val scaffoldState = rememberBackdropScaffoldState(initialValue = BackdropValue.Revealed)

        val modifier = Modifier.padding(horizontal = 16.dp)

        BackdropScaffold(
            appBar = {},
            scaffoldState = scaffoldState,
            backLayerBackgroundColor = Color.White,
            frontLayerScrimColor = Color.Transparent,
            backLayerContent = {
                ImageSlider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp),
                    images = it.images,
                    isBookmarkAdded = isBookmarkAdded.value,
                    onBackIconClick = {  navController.navigateUp()},
                    onBookmarkIconClick = {
                        viewModel.checkInBookmark.value = !isBookmarkAdded.value
                        viewModel.addOrRemoveBookmark(it.id)
                    }
                )
            },
            frontLayerContent = {
                LazyColumn(
                    state = columnState
                ) {
                    item { Spacer(Modifier.height(8.dp)) }
                    item {
                        Text(
                            it.title,
                            modifier.fillMaxWidth(),
                            style = MaterialTheme.typography.subtitle2
                        )
                    }

                    item { Spacer(Modifier.height(8.dp)) }

                    item { RatingBar(modifier = modifier, rating = feedbacks.value?.totalRating ?: 0) }

                    item { Spacer(Modifier.height(8.dp)) }

                    item {
                        Row(
                            modifier = modifier.offset((-4).dp, 0.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Place,
                                null,
                                tint = ColorGray
                            )
                            Text(it.location.toString(), style = MaterialTheme.typography.body1)

                        }
                    }

                    item { Spacer(Modifier.height(8.dp)) }
                    item {
                        Text(
                            modifier = modifier,
                            text = it.description,
                            style = MaterialTheme.typography.body1.copy(color = Color.Black),
                            lineHeight = 1.37.em,
                            letterSpacing = (-0.0241176).em,
                            textAlign = TextAlign.Justify
                        )
                    }

                    item { Spacer(Modifier.height(8.dp)) }

                    item {
                        TabRow(
                            modifier = modifier.fillMaxWidth(),
                            backgroundColor = Color.Transparent,
                            selectedTabIndex = selectedTabIndex,
                            divider = {},
                            indicator = { tabPositions ->
                                TabRowDefaults.Indicator(
                                    Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                                    color = ColorPurple
                                )
                            },
                            tabs = {
                                CustomTab(
                                    tab = Tabs.Map,
                                    selectedTab = selectedTab,
                                    onTabClick = { selectedTab = it }
                                )
                                CustomTab(
                                    tab = Tabs.Feedback,
                                    selectedTab = selectedTab,
                                    onTabClick = { selectedTab = it }
                                )

                            }
                        )
                    }

                    item {
                        when (selectedTab.id) {
                            Tab.Map -> Box(
                                Modifier
                                    .fillMaxWidth()
                                    .height(500.dp)
                            ) {
                                MapView(it.latitude.toString(), it.longitude.toString())
                            }
                            Tab.Feedback -> FeedbackTab(
                                selectedRating = selectedRating,
                                feedback = feedbacks.value,
                                onIconClick = {
                                    selectedRating = it
                                    if (!SharedPreferences.isGuest()) {
                                        navController.navigate(Route.Feedback.id + "/$sightId/$selectedRating")
                                    } else {
                                        Toast.makeText(
                                            RussiaTravelApplication.context,
                                            "Необходимо авторизоваться, чтобы оставлять отзывы",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                            )
                        }
                    }
                }
            },
        )
    } ?: Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        CircularProgressIndicator(color = ColorBlueDark)
    }
}

@Composable
fun FeedbackTab(selectedRating: Int, onIconClick: (Int) -> Unit, feedback: Feedback?) {
    feedback?.let {
        Column(
            Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ClickableRatingBar(selectedRating, onIconClick)
            Text("Были здесь? Напишите отзыв")
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 20.dp),
            ) {
                Row {
                    Text(it.totalRating.toString(), color = Color.Black)
                    Spacer(Modifier.width(8.dp))
                    RatingBar(modifier = Modifier, feedback.totalRating)
                }
                Text(
                    it.totalCount.toString() + " " + wordEndSuffix(it.totalCount),
                    color = Color.Black
                )
            }

            for (feedback in feedback.feedbacks) {
                FeedbackRow(feedback)
            }

            Spacer(Modifier.height(24.dp))

        }
    }
}

fun wordEndSuffix(count: Int): String {
    when {
        count % 10 == 0 || count % 10 in 6..9 -> return "отзывов"
        count % 10 == 1 -> return "отзыв"
        count % 10 in 2..4 -> return "отзыва"
        count % 100 in 11..19 -> return "отзывов"
    }
    return "отзыв"
}

@Composable
fun FeedbackRow(feedback: FeedbackItem) {
    Column(
        Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row {
                Image(
                    painter = rememberCoilPainter(feedback.photo),
                    contentDescription = "avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                )
                Text(
                    feedback.name,
                    modifier = Modifier
                        .align(Alignment.Top)
                        .padding(start = 8.dp, top = 2.dp), color = Color.Black
                )
            }
            RatingBar(modifier = Modifier, feedback.rating)
        }

        Text(
            feedback.feedback,
            modifier = Modifier.padding(vertical = 8.dp),
            color = Color.Black
        )
    }

}


@Composable
fun MapView(
    latitude: String,
    longitude: String
) {
    val mapView = rememberMapViewWithLifecycle()
    MapViewContainer(mapView, latitude, longitude)
}


@Composable
private fun MapViewContainer(
    map: MapView,
    latitude: String,
    longitude: String
) {
    var mapInitialized by remember(map) { mutableStateOf(false) }

    LaunchedEffect(map, mapInitialized) {
        if (!mapInitialized) {
            val googleMap = map.awaitMap()
            val position = LatLng(latitude.toDouble(), longitude.toDouble())
            googleMap.addMarker {
                position(position)
            }
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(position))
            mapInitialized = true
        }
    }

    var zoom by rememberSaveable(map) { mutableStateOf(InitialZoom) }

    Column{
        ZoomControls(zoom) {
            zoom = it.coerceIn(MinZoom, MaxZoom)
        }

        val coroutineScope = rememberCoroutineScope()
        AndroidView({ map }) {
            val mapZoom = zoom
            coroutineScope.launch {
                val googleMap = map.awaitMap()
                googleMap.setZoom(mapZoom)
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(latitude.toDouble(), longitude.toDouble())))
            }
        }
    }

}


@Composable
private fun ZoomControls(
    zoom: Float,
    onZoomChanged: (Float) -> Unit
) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        ZoomButton("-", onClick = { onZoomChanged(zoom * 0.8f) })
        ZoomButton("+", onClick = { onZoomChanged(zoom * 1.2f) })
    }
}

@Composable
private fun ZoomButton(text: String, onClick: () -> Unit) {
    Button(
        modifier = Modifier.padding(8.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.onPrimary,
            contentColor = MaterialTheme.colors.primary
        ),
        onClick = onClick
    ) {
        Text(text = text, style = MaterialTheme.typography.h5)
    }
}

private const val InitialZoom = 16f
const val MinZoom = 2f
const val MaxZoom = 20f

@Composable
fun RatingBar(
    modifier: Modifier,
    rating: Int = 0,
    iconSize: Dp = 22.dp,
) {
    Row(modifier.offset(x = (-4).dp, 0.dp)) {
        for (i in 1..5) {
            if (rating >= i) {
                Icon(
                    Icons.Default.StarRate,
                    null,
                    modifier = Modifier.size(iconSize),
                    tint = Color.Yellow
                )
            } else {
                Icon(
                    Icons.Default.StarOutline,
                    null,
                    modifier = Modifier.size(iconSize),
                    tint = Color.Yellow
                )
            }
        }
    }
}

@Composable
fun ClickableRatingBar(rating: Int = 0, onIconClick: (Int) -> Unit) {
    Row {
        for (i in 1..5) {
            Icon(
                if (rating >= i) Icons.Default.Star else Icons.Default.StarOutline,
                null,
                modifier = Modifier
                    .size(48.dp)
                    .clickable {
                        onIconClick(i)
                    },
                tint = ColorBlueDark
            )
        }
    }
}

@Composable
fun CustomTab(
    tab: Tabs,
    selectedTab: Tabs,
    onTabClick: (Tabs) -> Unit
) {
    Tab(
        selected = selectedTab == tab,
        selectedContentColor = ColorPurple,
        unselectedContentColor = Color.Black,
        onClick = { onTabClick(tab) }
    ) {
        Text(
            text = tab.name,
            modifier = Modifier
                .padding(4.dp),
            color = if (tab == selectedTab) ColorPurple else Color.Black,
            style = MaterialTheme.typography.body1.copy(fontSize = 18.sp)

        )
    }
}