package com.arwani.pokemon.ui.screen.components


import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.arwani.pokemon.ui.navigation.Screen
import kotlin.math.roundToInt

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    currentScreen: String,
    bottomBarHeight: Dp = 50.dp,
    bottomBarOffsetHeightPx: Float = 0f
) {
    val background = MaterialTheme.colorScheme.background
    NavigationBar(
        modifier = modifier
            .height(bottomBarHeight)
            .offset { IntOffset(x = 0, y = -bottomBarOffsetHeightPx.roundToInt()) }
            .drawBehind {
                val borderSize = 1.dp.toPx()
                drawLine(
                    color = background,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = borderSize
                )
            },
        containerColor = MaterialTheme.colorScheme.background,
    ) {
        val items = listOf(
            BottomBarItems(
                icon = Icons.Rounded.Home,
                screen = Screen.Home.route
            ),
            BottomBarItems(
                icon = Icons.Rounded.Favorite,
                screen = Screen.MyPokemon.route
            ),
        )

        items.map { item ->
            NavigationBarItem(
                selected = currentScreen == item.screen,
                onClick = { navController.navigateSingleTopTo(item.screen) },
                icon = {
                    Icon(imageVector = item.icon, contentDescription = item.screen)
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.background.copy(alpha = 0.3f)
                ),
            )
        }
    }
}

data class BottomBarItems(
    val icon: ImageVector,
    val screen: String
)

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
