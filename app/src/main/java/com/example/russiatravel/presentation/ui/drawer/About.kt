package com.example.russiatravel.presentation.ui.drawer

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.russiatravel.R
import com.example.russiatravel.presentation.ui.components.NothingHere
import com.example.russiatravel.ui.theme.ColorBlueDark

@Composable
fun About(){
    Column{
        Text("Дизайнер: Васильев Д.И.", style = MaterialTheme.typography.button)
        Text("Разработчик базы данных: Васильев Д.И.", style = MaterialTheme.typography.button)
        Text("Back-end разработчик: Васильев Д.И.", style = MaterialTheme.typography.button)
        Text("Разработчик мобильного приложения: Васильев Д.И.", style = MaterialTheme.typography.button)
        Text("Ссылки на соц. сети", style = MaterialTheme.typography.button)
        Spacer(Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                modifier = Modifier.size(48.dp),
                painter = painterResource(R.drawable.ic_vk),
                contentDescription =  null
            )
            Spacer(Modifier.width(6.dp))
            Icon(
                modifier = Modifier.size(48.dp),
                painter = painterResource(R.drawable.ic_telegram),
                contentDescription =  null
            )
            Spacer(Modifier.width(6.dp))
            Icon(
                modifier = Modifier.size(48.dp),
                painter = painterResource(R.drawable.ic_discord),
                contentDescription =  null
            )
        }
    }
}