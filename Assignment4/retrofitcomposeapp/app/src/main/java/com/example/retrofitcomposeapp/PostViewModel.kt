package com.example.retrofitcomposeapp


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitcomposeapp.data.model.Post
import com.example.retrofitcomposeapp.data.repository.PostRepository
import com.example.retrofitcomposeapp.data.repository.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PostViewModel(
    private val repository: PostRepository = PostRepository()
) : ViewModel() {
    private val _postsState = MutableStateFlow<Result<List<Post>>>(Result.Loading)
    val postsState: StateFlow<Result<List<Post>>> = _postsState.asStateFlow()

    private val _selectedPostState = MutableStateFlow<Result<Post>>(Result.Loading)
    val selectedPostState: StateFlow<Result<Post>> = _selectedPostState.asStateFlow()

    init {
        fetchPosts()
    }

    fun fetchPosts() {
        viewModelScope.launch {
            repository.getPosts().collect { result ->
                _postsState.value = result
            }
        }
    }

    fun fetchPostById(id: Int) {
        viewModelScope.launch {
            repository.getPostById(id).collect { result ->
                _selectedPostState.value = result
            }
        }
    }
}