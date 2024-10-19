package com.example.instgramLayout;

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var postAdapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val postList = listOf(
            Post(R.drawable.bob1, "This is Sponge Bob :)"),
            Post(R.drawable.dvard, "This is Squidward :("),
            Post(R.drawable.patrick_star, "This is Sea Star :/")

        )

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        postAdapter = PostAdapter(postList)
        recyclerView.adapter = postAdapter
    }
}
