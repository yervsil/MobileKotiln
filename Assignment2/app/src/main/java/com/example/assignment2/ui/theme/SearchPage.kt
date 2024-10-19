package com.example.assignment2.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import androidx.compose.material3.TextField
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.lazy.LazyColumn
import coil.compose.rememberAsyncImagePainter
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun SearchPage(navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }
    val results = listOf("user1", "user2", "user3")

    Column {
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search") },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )
        LazyColumn {
            items(results.filter { it.contains(searchQuery, ignoreCase = true) }) { result ->
                Text(text = result, modifier = Modifier.padding(16.dp))
            }
        }
    }
}
