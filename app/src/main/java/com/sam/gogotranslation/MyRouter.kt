package com.sam.gogotranslation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.ai.client.generativeai.GenerativeModel
import com.sam.gogotranslation.ui.view.home.HomeScreen

const val ROUTER_HOME = "home"

@Composable
fun MyRouter(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = ROUTER_HOME,
    ) {
        composable(ROUTER_HOME) {
            HomeScreen(
                navController = navController,
            )
        }
//        composable(ROUTER_CREATE_IDEAS) {
//            CreateIdeasScreen(
//                navController = navController,
//            )
//        }
    }
}
