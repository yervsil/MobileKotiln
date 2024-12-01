package com.example.roomcomposeapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.roomcomposeapp.data.User


@Composable
fun UserListScreen(
    viewModel: UserViewModel = viewModel()
) {
    var showAddUserDialog by remember { mutableStateOf(false) }
    var selectedUser by remember { mutableStateOf<User?>(null) }

    val users by viewModel.allUsers.observeAsState(emptyList())

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddUserDialog = true }) {
                Icon(Icons.Filled.Add, contentDescription = "Add User")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            items(users) { user ->
                UserListItem(
                    user = user,
                    onDelete = { viewModel.delete(user) },
                    onEdit = { selectedUser = user
                                showAddUserDialog = true}
                )
            }
        }

        if (showAddUserDialog) {
            UserInputDialog(
                user = selectedUser,
                onDismiss = {
                    showAddUserDialog = false
                    selectedUser = null
                },
                onSave = { newUser ->
                    if (selectedUser == null) {
                        viewModel.insert(newUser)
                    } else {
                        viewModel.update(newUser)
                    }
                    showAddUserDialog = false
                    selectedUser = null
                }
            )
        }
    }
}

@Composable
fun UserListItem(
    user: User,
    onDelete: () -> Unit,
    onEdit: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onEdit() }
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = "Name: ${user.name}", style = MaterialTheme.typography.bodyLarge)
                Text(text = "Email: ${user.email}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Age: ${user.age}", style = MaterialTheme.typography.bodyMedium)
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Filled.Delete, contentDescription = "Delete User")
            }
        }
    }
}

@Composable
fun UserInputDialog(
    user: User? = null,
    onDismiss: () -> Unit,
    onSave: (User) -> Unit
) {
    var name by remember { mutableStateOf(user?.name ?: "") }
    var email by remember { mutableStateOf(user?.email ?: "") }
    var age by remember { mutableStateOf(user?.age?.toString() ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = if (user == null) "Add User" else "Edit User") },
        text = {
            Column {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = age,
                    onValueChange = { age = it },
                    label = { Text("Age") }
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (name.isNotBlank() && email.isNotBlank() && age.isNotBlank()) {
                        val newUser = User(
                            id = user?.id ?: 0,
                            name = name,
                            email = email,
                            age = age.toIntOrNull() ?: 0
                        )
                        onSave(newUser)
                    }
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
