package com.example.russiatravel.presentation.ui.sight

import android.net.Uri
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavController
import com.example.russiatravel.R
import com.example.russiatravel.ui.theme.*
import com.example.russiatravel.utils.rememberMapViewWithLifecycle
import com.example.russiatravel.utils.setZoom
import com.example.russiatravel.viewModel.SightViewModel
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.toPaddingValues
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Log
import com.google.android.exoplayer2.util.Util
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.MapView
import com.google.android.libraries.maps.model.LatLng
import com.google.maps.android.ktx.addMarker
import com.google.maps.android.ktx.awaitMap
import kotlinx.coroutines.launch
import me.onebone.toolbar.AppBarContainer
import me.onebone.toolbar.CollapsingToolbar
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarState
import kotlin.math.roundToInt

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
    /*var isRequestSent by remember { mutableStateOf (false) }
    if (!isRequestSent) {
        isRequestSent = true
        viewModel.getSightDetail(1)
    }
    val sight = viewModel.sight.observeAsState()*/

    val images = listOf(
        R.drawable.sarva,
        R.drawable.sarva2,
        R.drawable.sarva3,
        R.drawable.sarva4
    )

    var currentImage by remember { mutableStateOf(images[0]) }

    val scrollState = rememberScrollState()
    Log.d("scrollState", scrollState.value.toString())
    val text = "Озеро Сарва расположилось в Нуримановском районе, рядом с одноименным поселком." +
            " Это небольшое родниковое озеро, длина и ширина которого составляют 60 и 30 метров соответственно. " +
            "Максимальная глубина достигает 38 метров. Дно озера крутое, практически отвесное (поскольку глубина значительно превышает ширину)."
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
            ImageStack(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
                images = images,
                currentImage = currentImage
            ) {
                currentImage = it
            }
        },
        frontLayerContent = {
            LazyColumn(
                state = columnState
            ) {
                item { Spacer(Modifier.height(8.dp)) }
                item {
                    Text(
                        "Озеро Сарва",
                        modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.subtitle2
                    )
                }

                item { Spacer(Modifier.height(8.dp)) }

                item { RatingBar(modifier = modifier,rating = 3) }

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
                        Text("РБ, Нуримановский район", style = MaterialTheme.typography.body1)

                    }
                }


                item { Spacer(Modifier.height(8.dp))}
                item {
                    Text(
                        modifier = modifier,
                        text = text,
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
                    Box(Modifier.fillMaxWidth().height(500.dp)){
                        MapView()
                    }
                }

            }
        },
    )
}


@Composable
fun ImageStack(
    modifier: Modifier,
    images: List<Int>,
    currentImage: Int,
    onImageClick: (Int) -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomCenter
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(currentImage),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        LazyRow(
            Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp, start = 10.dp, end = 10.dp),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            items(images) { image ->
                Surface(
                    modifier = Modifier
                        .clickable { onImageClick(image) },
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(
                        2.dp,
                        if (image == currentImage) Color.White else ColorPurple
                    )
                ) {
                    Image(
                        modifier = Modifier
                            .size(80.dp),
                        painter = painterResource(image),
                        contentScale = ContentScale.Crop,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Composable
fun MapView(
    latitude: String = "54.740781",
    longitude: String = "55.968402"
){
    val mapView = rememberMapViewWithLifecycle()
    MapViewContainer(mapView, latitude , longitude)
}

@Composable
private fun MapViewContainer(
    map: MapView,
    latitude: String,
    longitude: String
) {
    var mapInitialized by remember(map) { mutableStateOf(false) }
    var zoom by rememberSaveable(map) { mutableStateOf(InitialZoom) }
    Log.d("Zoom", zoom.toString())
    val coroutineScope = rememberCoroutineScope()
    Column {
        ZoomControls(zoom) {
            zoom = it.coerceIn(MinZoom, MaxZoom)
        }
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
        AndroidView({ map }) { mapView ->
            // Reading zoom so that AndroidView recomposes when it changes. The getMapAsync lambda
            // is stored for later, Compose doesn't recognize state reads
            val mapZoom = zoom
            coroutineScope.launch {
                val googleMap = mapView.awaitMap()
                googleMap.setZoom(mapZoom)
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
fun RatingBar(modifier:Modifier,rating: Int) {
    Row(modifier.offset(x = (-4).dp, 0.dp)) {
        for (i in 0..4) {
            if (rating > i) {
                Icon(
                    Icons.Default.StarRate,
                    null,
                    modifier = Modifier.size(22.dp),
                    tint = Color.Yellow
                )
            } else {
                Icon(
                    Icons.Default.StarOutline,
                    null,
                    modifier = Modifier.size(22.dp),
                    tint = Color.Yellow
                )
            }
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