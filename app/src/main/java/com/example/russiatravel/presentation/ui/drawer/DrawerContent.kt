package com.example.russiatravel.presentation.ui.filter

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.example.russiatravel.R
import com.example.russiatravel.cache.SharedPreferences
import com.example.russiatravel.presentation.ui.Route
import com.example.russiatravel.ui.MainActivity
import com.example.russiatravel.ui.theme.ColorBrown
import com.google.accompanist.coil.rememberCoilPainter

@Composable
fun DrawerContent(navController: NavController) {
    Column(
        Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentAlignment = Alignment.BottomStart
        ){
            val user = SharedPreferences.loadUserInfo()
            Log.d("user drawer", user.toString())
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = rememberCoilPainter(user.avatar),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Text(user.name, modifier = Modifier.padding(start = 16.dp, bottom = 16.dp))

        }
        DrawerRow(text = "Избранное", icon = Icons.Default.Favorite) { navController.navigate(Route.Settings.id) }
        DrawerRow(text = "О программе", icon = Icons.Default.Info) { navController.navigate(Route.About.id) }
        Button(onClick = {SharedPreferences.removeData()}){
            Text("Dыйти")
        }
    }
}

@Composable
fun DrawerRow(text: String,icon: ImageVector ,onNavigate: () -> Unit) {
    Row(
        Modifier
            .clickable { onNavigate() }
            .padding(vertical = 6.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null)
        Text(text, style = MaterialTheme.typography.subtitle2)
    }
}