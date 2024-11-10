package com.example.assignment3_1

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

// Data class for Movie items
data class Movie(val name: String)

// ViewModel to manage the list of movies
class MovieViewModel : ViewModel() {
    val movies = listOf(
        Movie("Inception"),
        Movie("Interstellar"),
        Movie("The Matrix"),
        Movie("The Godfather"),
        Movie("Pulp Fiction")
    )
}

// Main Activity to host the composables
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            MovieListScreen()
//        }
//    }
//}

// Composable function for the entire screen
@Composable
fun MovieListScreen(viewModel: MovieViewModel = viewModel()) {
    val context = LocalContext.current

    // Displaying the list of movies in a LazyColumn (Compose's RecyclerView alternative)
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(viewModel.movies) { movie ->
            MovieItem(
                movie = movie,
                onClick = {
                    Toast.makeText(context, "Clicked: ${movie.name}", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}

// Composable for each item in the list
@Composable
fun MovieItem(movie: Movie, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = movie.name,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMovieListScreen() {
    MovieListScreen()
}
