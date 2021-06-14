package com.example.russiatravel.presentation.ui.drawer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavController
import com.example.russiatravel.presentation.ui.components.NothingHere
import com.example.russiatravel.presentation.ui.sight.SightCard
import com.example.russiatravel.ui.theme.ColorBlueDark
import com.example.russiatravel.viewModel.SightViewModel

@Composable
fun Settings(
    navController: NavController,
    viewModel: SightViewModel = hiltNavGraphViewModel()
){
    val request = remember (LocalContext.current){
        viewModel.getBookmarks()
        true
    }

    val bookmarks = viewModel.sights.observeAsState()

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
            if (bookmarks.value?.isNotEmpty() == true) {
                items(items = bookmarks.value!!) { item ->
                    SightCard(navController, item)
                }
            }

        }
    }
}