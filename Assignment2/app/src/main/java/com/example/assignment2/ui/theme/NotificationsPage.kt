package com.example.assignment2.ui.theme

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.assignment2.Notification
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.items

@Composable
fun NotificationsPage(navController: NavController) {
    val notifications = listOf(
        Notification(1, "User1 liked your post"),
        Notification(2, "User2 commented: Nice post!")
    )

    LazyColumn {
        items(notifications) { notification ->
            Text(text = notification.content, modifier = Modifier.padding(16.dp))
        }
    }
}
