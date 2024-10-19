package com.example.assignment2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.assignment2.ui.theme.AppNavigation
import com.example.assignment2.ui.theme.Assignment2Theme
import com.example.assignment2.ui.theme.BottomNavigationBar
import androidx.navigation.compose.rememberNavController
import com.example.assignment2.ui.theme.AddPostPage
import com.example.assignment2.ui.theme.HomeFeedPage
import com.example.assignment2.ui.theme.NotificationsPage
import com.example.assignment2.ui.theme.ProfilePage
import com.example.assignment2.ui.theme.SearchPage

sealed class Screen(val route: String) {
    object HomeFeed : Screen("home_feed")
    object Profile : Screen("profile")
    object Search : Screen("search")
    object AddPost : Screen("add_post")
    object Notifications : Screen("notifications")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Assignment2Theme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.HomeFeed.route
    ) {
        composable(Screen.HomeFeed.route) { HomeFeedPage(navController) }
        composable(Screen.Profile.route) { ProfilePage(navController) }
        composable(Screen.Search.route) { SearchPage(navController) }
        composable(Screen.AddPost.route) { AddPostPage(navController) }
        composable(Screen.Notifications.route) { NotificationsPage(navController) }
    }
}
