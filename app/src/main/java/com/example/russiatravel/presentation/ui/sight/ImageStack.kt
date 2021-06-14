package com.example.russiatravel.presentation.ui.sight

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.russiatravel.cache.SharedPreferences
import com.example.russiatravel.presentation.ui.RussiaTravelApplication
import com.example.russiatravel.ui.theme.ColorPurple
import com.example.russiatravel.ui.theme.ColorRed
import com.google.accompanist.coil.rememberCoilPainter


@Composable
fun ImageSlider(
    modifier: Modifier,
    images: List<String>,
    currentImage: String,
    onImageClick: (String) -> Unit,
    isBookmarkAdded: Boolean,
    onBackIconClick: () -> Unit,
    onBookmarkIconClick: () -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomCenter
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = rememberCoilPainter(request = currentImage),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp)
                .padding(horizontal = 16.dp)
                .align(Alignment.TopCenter),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(28.dp)
                    .clickable { onBackIconClick() }
            )
            Icon(
                imageVector = if (isBookmarkAdded) Icons.Default.BookmarkAdded else Icons.Default.BookmarkAdd,
                contentDescription = null,
                tint = if (isBookmarkAdded && !SharedPreferences.isGuest()) ColorRed else Color.White,
                modifier = Modifier
                    .size(28.dp)
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
                        painter = rememberCoilPainter(request = image),
                        contentScale = ContentScale.Crop,
                        contentDescription = null
                    )
                }
            }
        }
    }
}
