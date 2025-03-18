package com.fetch.fetch.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fetch.fetch.ui.view.HomeScreen

@Composable
fun FetchNavHost(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    // Set up the NavHost
    NavHost(
        navController = navController,
        startDestination = "homeScreen" // Define the start destination
    ) {
        composable("homeScreen") {
            HomeScreen(modifier) // Your HomeScreen composable
        }
    }
}