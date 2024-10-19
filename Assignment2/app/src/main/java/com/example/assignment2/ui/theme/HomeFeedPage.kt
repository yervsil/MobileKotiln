package com.example.assignment2.ui.theme

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.assignment2.Post


@Composable
fun HomeFeedPage(navController: NavController) {
    val posts = listOf(
        Post(1, "Yerasil", "https://i.postimg.cc/FHqsXhyH/spong-patrick.png", "Hello Yerasil!", 1240),
        Post(2, "Azamat", "https://i.postimg.cc/SKMQyHd7/squid.jpg", "Hello Azamat!", 9000)
    )

    LazyColumn {
        items(posts) { post ->
            Card(modifier = Modifier.padding(8.dp)) {
                Column {
                    Text(text = post.username, fontWeight = FontWeight.Bold)
                    Image(
                        painter = rememberImagePainter(post.imageUrl),
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth().height(200.dp)
                    )
                    Text(text = post.caption)
                    Text(text = "${post.likes} likes", fontWeight = FontWeight.Light)
                }
            }
        }
    }
}
