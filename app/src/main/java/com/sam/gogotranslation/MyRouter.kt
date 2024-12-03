package com.sam.gogotranslation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.sam.gogotranslation.ui.view.history.HistoryScreen
import com.sam.gogotranslation.ui.view.home.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
data object Home

@Serializable
data object History

@Composable
fun MyRouter(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Home,
    ) {
        composable<Home> { backStackEntry ->
            HomeScreen(
                navController = navController,
            )
        }
        dialog<History> {
            HistoryScreen(
                navController = navController,
            )
        }
    }
}
