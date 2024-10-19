package com.example.assignment2.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.assignment2.Post
import com.example.assignment2.UserProfile
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import coil.compose.rememberImagePainter
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.ui.res.painterResource
import coil.compose.rememberAsyncImagePainter

val List = listOf(
    "https://example.com/image1.jpg",
    "https://example.com/image2.jpg",
    "https://example.com/image3.jpg",
    "https://example.com/image4.jpg",
    "https://example.com/image5.jpg",
    "https://example.com/image6.jpg"
)



@Composable
fun ProfilePage(navController: NavController) {
    val profile = UserProfile("user1", "https://profile-pic.jpg", "Bio here", 12)
    val userPosts = listOf(
        Post(1, "user1", "https://i.postimg.cc/FHqsXhyH/spong-patrick.png", "Caption 1", 1200),
        Post(2, "user1", "https://i.postimg.cc/SKMQyHd7/squid.jpg", "Caption 2", 87)
    )


    Column {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberAsyncImagePainter(profile.profilePictureUrl),
                contentDescription = null,
                modifier = Modifier.size(80.dp).clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = profile.username, fontWeight = FontWeight.Bold)
                Text(text = profile.bio)
                Text(text = "Posts: ${profile.postCount}")
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            content = {
                items(List.size) { index ->

                    Image(
                        painter = rememberImagePainter(List[index]),
                        contentDescription = null,
                        modifier = Modifier.size(100.dp)
                    )
                }
            }
        )
    }
}
