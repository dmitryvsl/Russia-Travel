package com.example.russiatravel.presentation.ui.filter

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.example.russiatravel.cache.SharedPreferences
import com.example.russiatravel.presentation.ui.Route
import com.example.russiatravel.ui.theme.ColorBrown

@Composable
fun DrawerContent(navController: NavController) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(start = 24.dp, top = 48.dp)
    ) {
        DrawerRow(text = "Профиль") { navController.navigate(Route.Profile.id) }
        DrawerRow(text = "Настройки") { navController.navigate(Route.Settings.id) }
        DrawerRow(text = "О программе") { navController.navigate(Route.About.id) }
    }
}

@Composable
fun DrawerRow(text: String, onNavigate: () -> Unit) {
    Row(Modifier.clickable { onNavigate() }.padding(vertical = 6.dp)) {
        Icon(Icons.Default.AccountCircle, "")
        Text(text, style = MaterialTheme.typography.subtitle1.copy(color = ColorBrown))
    }
}