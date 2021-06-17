package com.example.russiatravel.presentation.ui.filter

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.russiatravel.R
import com.example.russiatravel.cache.SharedPreferences
import com.example.russiatravel.presentation.ui.Route
import com.example.russiatravel.presentation.ui.RussiaTravelApplication
import com.example.russiatravel.ui.MainActivity
import com.example.russiatravel.ui.theme.ColorBlueDark
import com.example.russiatravel.ui.theme.ColorBrown
import com.google.accompanist.coil.rememberCoilPainter
import kotlinx.coroutines.launch

@Composable
fun DrawerContent(
    navController: NavController,
    scaffoldState: ScaffoldState
) {
    val coroutineScope = rememberCoroutineScope()
    Column(
        Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentAlignment = Alignment.BottomStart
        ){
            val user = SharedPreferences.loadUserInfo()
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = if (SharedPreferences.isGuest()) painterResource(id = R.drawable.default_avatar) else rememberCoilPainter(user.avatar),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            if(!SharedPreferences.isGuest()){
                Text(user.name, modifier = Modifier.padding(start = 16.dp, bottom = 16.dp), color = Color.White)
            }

        }
        DrawerRow(text = "Избранное", icon = Icons.Default.Bookmarks) { navController.navigate(Route.Settings.id) }
        DrawerRow(text = "О проекте", icon = Icons.Default.Info) { navController.navigate(Route.About.id) }
        Spacer(Modifier.height(6.dp))
        if (!SharedPreferences.isGuest()){
            Button(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = ColorBlueDark
                ),
                onClick = {
                    Toast.makeText(RussiaTravelApplication.context, "Вы были авторизованы как гость", Toast.LENGTH_LONG).show()
                    coroutineScope.launch {
                        scaffoldState.drawerState.close()
                    }
                    SharedPreferences.removeData()
                }
            ){
                Text("Выйти", color = Color.White)
            }
        }else{
            Button(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(end = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = ColorBlueDark
                ),
                onClick = {
                    navController.navigate(Route.StartScreen.id)
                }
            ){
                Text("Авторизоваться", color = Color.White)
            }
        }
    }
}

@Composable
fun DrawerRow(text: String,icon: ImageVector ,onNavigate: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable { onNavigate() }
            .padding(vertical = 6.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = ColorBlueDark)
        Spacer(Modifier.width(6.dp))
        Text(text, style = MaterialTheme.typography.subtitle2)
    }
}