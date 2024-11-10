package com.example.assignment3_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

// Data class to represent a user
data class User(val name: String)

class UserViewModel : ViewModel() {

    private val _users = MutableLiveData<List<User>>(listOf(User("Alim"), User("Karim")))
    val users: LiveData<List<User>> = _users

    private val _userInput = MutableLiveData("")
    val userInput: LiveData<String> = _userInput

    fun onUserInputChange(newInput: String) {
        _userInput.value = newInput
    }

    fun addUser() {
        val currentList = _users.value ?: listOf()
        val newUser = User(_userInput.value ?: "")
        if (newUser.name.isNotEmpty()) {
            _users.value = currentList + newUser
            _userInput.value = "" // Reset input after adding
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UserApp()
        }
    }
}

@Composable
fun UserApp(viewModel: UserViewModel = viewModel()) {
    Column(modifier = Modifier.padding(16.dp)) {
        // Exercise 8: Input field to add a new user
        val userInput by viewModel.userInput.observeAsState("")
        BasicTextField(
            value = userInput,
            onValueChange = viewModel::onUserInputChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 18.sp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { viewModel.addUser() }) {
            Text(text = "Add User")
        }

        Spacer(modifier = Modifier.height(16.dp))

        val users by viewModel.users.observeAsState(emptyList())
        Text(text = "User List:", fontSize = 20.sp)
        users.forEach { user ->
            Text(text = user.name, fontSize = 18.sp, modifier = Modifier.padding(4.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUserApp() {
    UserApp()
}
