package com.example.russiatravel.presentation.ui.filter

import android.Manifest
import android.content.pm.PackageManager
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.example.russiatravel.R
import com.example.russiatravel.network.model.LocationResponse
import com.example.russiatravel.presentation.ui.RussiaTravelApplication
import com.example.russiatravel.presentation.ui.Route
import com.example.russiatravel.presentation.ui.RussiaTravelApplication.Companion.context
import com.example.russiatravel.ui.components.FilledButton
import com.example.russiatravel.ui.theme.ColorBlueDark
import com.example.russiatravel.ui.theme.ColorWhiteDark
import com.example.russiatravel.viewModel.LocationViewModel
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import java.security.Permission

@Composable
fun FilterScreen(
    navController: NavController,
    viewModel: LocationViewModel = hiltNavGraphViewModel()
) {

    viewModel.fetchRegions() // Загрузить список регионов
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission Accepted: Do something
            val fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(RussiaTravelApplication.context)
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    Log.d("Location", "${location.latitude}   ${location.longitude}")
                    navController.navigate(Route.SightList.id + "/${location.latitude}/${location.longitude}")
                }
            }
        }
    }
    // Строковые ресурсы
    val region = stringResource(id = R.string.region)
    val locality = stringResource(id = R.string.locality)
    // Подсказки для спинеров выбора расположения
    var regionValue by remember { mutableStateOf(region) }
    var localityValue by remember { mutableStateOf(locality) }
    // массивы для спинеров
    val itemsRegions = viewModel.regions.observeAsState()
    val itemsLocalities = viewModel.localities.observeAsState()
    // показать элементы спинера
    var expandedRegion: Boolean by remember { mutableStateOf(false) }
    var expandedLocality: Boolean by remember { mutableStateOf(false) }
    // выбранный таб
    var tabPage by remember { mutableStateOf(TabPage.Wild) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(50.dp))
        Text(
            "Показать достопримечательности, которые находятся рядом с вами. Необходимо дать разрешение на определение местоположения",
            style = MaterialTheme.typography.subtitle2.copy(color = Color.Black, fontSize = 16.sp),
            modifier = Modifier.padding(vertical = 8.dp)
        )
        FilledButton(
            text = "Определить мое местоположение",
            colors = ButtonDefaults.buttonColors(
                backgroundColor = ColorBlueDark,
                contentColor = Color.White
            ),
            onClick = {
                when (PackageManager.PERMISSION_GRANTED) {
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) -> {
                        // Some works that require permission
                        Log.d("ExampleScreen", "Code requires permission")
                        val fusedLocationClient =
                            LocationServices.getFusedLocationProviderClient(RussiaTravelApplication.context)
                        fusedLocationClient.lastLocation
                            .addOnSuccessListener { location ->
                                Log.d("Location", "success")
                                location?.let {
                                    Log.d("Location","${location.latitude}  ${location.longitude}")
                                    navController.navigate(Route.SightList.id + "/${location.latitude}/${location.longitude}")
                                }?:
                                Toast.makeText(RussiaTravelApplication.context, "Не удалось определить местоположение", Toast.LENGTH_LONG).show()
                            }
                            .addOnFailureListener {
                                Toast.makeText(RussiaTravelApplication.context, "Не удалось определить местоположение", Toast.LENGTH_LONG).show()
                            }
                    }
                    else -> {
                        // Asking for permission
                        launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                    }
                }
            }
        )
        Spacer(Modifier.height(100.dp))
        Text(
            "Либо ввести вручную параметры фильтрации",
            style = MaterialTheme.typography.subtitle2.copy(color = Color.Black, fontSize = 16.sp),
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        TabBar(
            tabPage = tabPage,
            onTabSelected = {
                tabPage = it
                viewModel.clearLocalities()
                localityValue = locality
                // если регион уже выбран
                if (regionValue != region) {
                    viewModel.fetchLocalities(
                        itemsRegions.value!!.filter { item -> regionValue == item.name }[0].id,
                        getRestTypeId(tabPage)
                    )
                }
            }
        )

        Spacer(Modifier.height(20.dp))

        Spinner(
            value = regionValue,
            expanded = expandedRegion,
            textcolor = if (regionValue != region) Color.Black else Color.LightGray,
            items = itemsRegions.value,
            onExpanded = { expandedRegion = true },
            onCollapsed = { expandedRegion = false },
            onItemClick = {
                regionValue = it
                viewModel.clearLocalities()
                localityValue = locality
                viewModel.fetchLocalities(
                    itemsRegions.value!!.filter { item -> item.name == it }[0].id,
                    getRestTypeId(tabPage)
                )
            }
        )
        Spacer(Modifier.height(20.dp))

        Spinner(
            value = localityValue,
            expanded = expandedLocality,
            textcolor = if (localityValue != locality) Color.Black else Color.LightGray,
            items = itemsLocalities.value,
            enabled = regionValue != region,
            onExpanded = { expandedLocality = true },
            onCollapsed = { expandedLocality = false },
            onItemClick = {
                localityValue = it
            }
        )
        Spacer(Modifier.height(20.dp))

        FilledButton(
            text = "Показать достопримечательности",
            colors = ButtonDefaults.buttonColors(
                backgroundColor = ColorBlueDark,
                contentColor = Color.White
            )
        ) {
            if (localityValue == locality) {
                Toast
                    .makeText(
                        RussiaTravelApplication.context,
                        "Выберите все параметры!",
                        Toast.LENGTH_SHORT
                    )
                    .show()
            } else {
                navController.navigate(
                    Route.SightList.id +
                            "/${
                                itemsLocalities
                                    .value!!
                                    .filter { it.name == localityValue }[0]
                                    .id
                            }"
                )
            }
        }
    }
}

fun getRestTypeId(tabPage: TabPage): Int {
    return when (tabPage) {
        TabPage.Wild -> 1
        TabPage.Civilly -> 2
    }
}

@Composable
fun Spinner(
    value: String,
    expanded: Boolean,
    items: List<LocationResponse>?,
    textcolor: Color,
    enabled: Boolean = true,
    onExpanded: () -> Unit,
    onCollapsed: () -> Unit,
    onItemClick: (String) -> Unit
) {
    Box {
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            onClick = { onExpanded() },
            enabled = enabled,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.White
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    value,
                    color = textcolor
                )
                Icon(Icons.Default.ArrowDropDown, "")
            }
        }
        DropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            expanded = expanded,
            onDismissRequest = { onCollapsed() }) {
            items?.forEach { item ->
                DropdownMenuItem(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onCollapsed()
                        onItemClick(item.name)
                    }
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = item.name,
                        style = MaterialTheme.typography.button.copy(color = Color.Black)
                    )
                }
            }
            if (items == null) {
                CircularProgressIndicator(color = ColorBlueDark)
            }
        }
    }
}