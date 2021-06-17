package com.example.russiatravel.presentation.ui.sight

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.russiatravel.cache.SharedPreferences
import com.example.russiatravel.presentation.ui.RussiaTravelApplication
import com.example.russiatravel.ui.theme.ColorBlueDark
import com.example.russiatravel.ui.theme.ColorPurple
import com.example.russiatravel.ui.theme.ColorRed
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.imageloading.ImageLoadState
import com.google.accompanist.imageloading.LoadPainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImageSlider(
    modifier: Modifier,
    images: List<String>,
    isBookmarkAdded: Boolean,
    onBackIconClick: () -> Unit,
    onBookmarkIconClick: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = images.size, initialOffscreenLimit = 2)

    val loadedImages: MutableList<LoadPainter<Any>> = mutableListOf()
    images.forEach {
        loadedImages.add(rememberCoilPainter(it))
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomCenter
    ) {
        HorizontalPager(pagerState) { page ->
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = loadedImages[page],
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            when (loadedImages[page].loadState) {
                is ImageLoadState.Loading -> {
                    CircularProgressIndicator(
                        Modifier.align(Alignment.Center),
                        color = ColorBlueDark
                    )
                }
                is ImageLoadState.Error -> {
                }
            }
        }
        HorizontalPagerIndicator(
            pagerState = pagerState,
            indicatorWidth = 10.dp,
            modifier = Modifier.padding(bottom = 20.dp),
            activeColor = ColorRed
        )
        IconsRow(
            Modifier.align(Alignment.TopCenter),
            onBackIconClick,
            isBookmarkAdded,
            onBookmarkIconClick
        )
    }
}

@Composable
private fun IconsRow(
    modifier: Modifier,
    onBackIconClick: () -> Unit,
    isBookmarkAdded: Boolean,
    onBookmarkIconClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 25.dp)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(36.dp)
                .clickable { onBackIconClick() }
        )
        Icon(
            imageVector = if (isBookmarkAdded) Icons.Default.BookmarkAdded else Icons.Default.BookmarkAdd,
            contentDescription = null,
            tint = if (isBookmarkAdded && !SharedPreferences.isGuest()) ColorRed else ColorBlueDark,
            modifier = Modifier
                .size(36.dp)
                .clickable {
                    if (SharedPreferences.isGuest()) {
                        Toast
                            .makeText(
                                RussiaTravelApplication.context,
                                "Для использования этой опции необходимо авторизироваться!",
                                Toast.LENGTH_LONG
                            )
                            .show()
                    } else {
                        onBookmarkIconClick()
                    }
                }
        )
    }
}
