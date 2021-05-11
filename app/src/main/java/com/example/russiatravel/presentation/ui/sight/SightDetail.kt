package com.example.russiatravel.presentation.ui.sight

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavController
import com.example.russiatravel.viewModel.SightViewModel

@Composable
fun SightDetail (
    navController: NavController,
    sightId: Int,
    changeTitle: (String) -> Unit,
    viewModel: SightViewModel = hiltNavGraphViewModel(),
){
    viewModel.getSightDetail(sightId)
    val sight = viewModel.sight.observeAsState()
    sight.value?.let {
        Log.d("Sight", it.title)
        changeTitle (it.title)
    }
    Column {
        LazyColumn() {
            item {
                Text("Описание")
            }
            item {
                Text("Фотографии")
            }
            item {
                Text("Видео")
            }
            item {
                Text("Карта")
            }
            item {
                Text("Отзывы")
            }
        }
    }
}