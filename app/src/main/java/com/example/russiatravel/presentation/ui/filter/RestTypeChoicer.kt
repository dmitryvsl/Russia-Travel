package com.example.russiatravel.presentation.ui.filter

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TabPosition
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.russiatravel.ui.theme.ColorBlueDark
import com.example.russiatravel.ui.theme.ColorRed


enum class TabPage {
    Wild, Civilly
}

@Composable
fun TabBar(
    tabPage: TabPage,
    onTabSelected: (tabPage: TabPage) -> Unit
) {
    Box(
        modifier = Modifier
            .background(color = Color.White, shape = RoundedCornerShape(10.dp))
    ){
        TabRow(
            backgroundColor = Color.Transparent,
            selectedTabIndex = tabPage.ordinal,
            indicator = { tabPositions ->
                TabIndicator(tabPositions, tabPage)
            }
        ){
            Tab(
                title = "Дикий",
                onClick = {onTabSelected(TabPage.Wild)}
            )
            Tab(
                title = "Цивилизованный",
                onClick = {onTabSelected(TabPage.Civilly)}
            )
        }
    }
}

@Composable
fun TabIndicator(
    tabPositions: List<TabPosition>,
    tabPage: TabPage
) {
    val transition = updateTransition(tabPage, "Tab indicator")

    val indicatorLeft by transition.animateDp(
        transitionSpec = {
            if (TabPage.Wild isTransitioningTo TabPage.Civilly) {
                spring(stiffness = Spring.StiffnessVeryLow)
            } else {
                spring(stiffness = Spring.StiffnessMedium)
            }
        },
        label = "Indicator left"
    ) { page ->
        tabPositions[page.ordinal].left
    }

    val indicatorRight by transition.animateDp(
        transitionSpec = {
            if (TabPage.Wild isTransitioningTo TabPage.Civilly) {
                spring(stiffness = Spring.StiffnessMedium)
            } else {
                spring(stiffness = Spring.StiffnessVeryLow)
            }
        },
        label = "Indicator right"
    ) { page ->
        tabPositions[page.ordinal].right
    }

    Box(
        Modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.BottomStart)
            .offset(x = indicatorLeft)
            .width(indicatorRight - indicatorLeft)
            .padding(4.dp)
            .fillMaxSize()
            .border(
                BorderStroke(2.dp, ColorBlueDark),
                RoundedCornerShape(10.dp)
            )
    )
}

@Composable
fun Tab(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(text = title, style = MaterialTheme.typography.button)
    }
}

