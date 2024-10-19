package com.example.assignment2.ui.theme

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.Modifier

sealed class Screen(val route: String) {
    object HomeFeed : Screen("home_feed")
    object Profile : Screen("profile")
    object Search : Screen("search")
    object AddPost : Screen("add_post")
    object Notifications : Screen("notifications")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { contentPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.HomeFeed.route,
            modifier = Modifier.padding(contentPadding) 
        ) {
            composable(Screen.HomeFeed.route) { HomeFeedPage(navController) }
            composable(Screen.Search.route) { SearchPage(navController) }
            composable(Screen.AddPost.route) { AddPostPage(navController) }
            composable(Screen.Notifications.route) { NotificationsPage(navController) }
            composable(Screen.Profile.route) { ProfilePage(navController) }
        }
    }
}



@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        Screen.HomeFeed,
        Screen.Search,
        Screen.AddPost,
        Screen.Notifications,
        Screen.Profile
    )

    BottomNavigation {
        items.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    when (screen) {
                        Screen.HomeFeed -> Icon(Icons.Default.Home, contentDescription = "Home")
                        Screen.Search -> Icon(Icons.Default.Search, contentDescription = "Search")
                        Screen.AddPost -> Icon(Icons.Default.Add, contentDescription = "Add Post")
                        Screen.Notifications -> Icon(Icons.Default.Notifications, contentDescription = "Notifications")
                        Screen.Profile -> Icon(Icons.Default.Person, contentDescription = "Profile")
                    }
                },
                selected = false,
                onClick = {
                    navController.navigate(screen.route) {

                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

