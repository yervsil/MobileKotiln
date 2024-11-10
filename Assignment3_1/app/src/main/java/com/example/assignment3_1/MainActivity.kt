package com.example.assignment3_1

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            FragmentLifecycleApp()
//        }
//    }
//}

// Fragment-like Composable with lifecycle logging
@Composable
fun FragmentOneLifecycle(owner: LifecycleOwner) {
    // Observing lifecycle
    DisposableEffect(owner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> {
                    // Analogous to onCreateView
                    Log.d("FragmentOne", "onCreateView")
                }
                Lifecycle.Event.ON_START -> {
                    Log.d("FragmentOne", "onStart")
                }
                Lifecycle.Event.ON_RESUME -> {
                    Log.d("FragmentOne", "onResume")
                }
                Lifecycle.Event.ON_PAUSE -> {
                    Log.d("FragmentOne", "onPause")
                }
                Lifecycle.Event.ON_STOP -> {
                    Log.d("FragmentOne", "onStop")
                }
                Lifecycle.Event.ON_DESTROY -> {
                    // Analogous to onDestroyView
                    Log.d("FragmentOne", "onDestroyView")
                }
                else -> Unit
            }
        }
        owner.lifecycle.addObserver(observer)

        onDispose {
            owner.lifecycle.removeObserver(observer)
        }
    }
    // UI content
    Text(text = "Hello from Fragment!")
}

@Composable
fun FragmentOne() {
    LogLifecycle("com.example.assignment3_1.FragmentOne") // Logs the lifecycle events
    Text(text = "Hello from Fragment!")
}


@Composable
fun LogLifecycle(tag: String) {
    DisposableEffect(Unit) {
        Log.d(tag, "onCreateView")
        onDispose {
            Log.d(tag, "onDestroyView")
        }
    }
}

class SharedViewModel : ViewModel() {
    var inputText by mutableStateOf("")
}

@Composable
fun FragmentInput(viewModel: SharedViewModel) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Enter text:")
        BasicTextField(
            value = viewModel.inputText,
            onValueChange = { viewModel.inputText = it },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun FragmentOutput(viewModel: SharedViewModel) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Output text:")
        Text(text = viewModel.inputText)
    }
}

@Composable
fun FragmentLifecycleApp() {
    val viewModel: SharedViewModel = viewModel()
    var currentFragment by remember { mutableStateOf(1) }

    Column {
        when (currentFragment) {
            1 -> FragmentOne()
            2 -> FragmentInput(viewModel)
            3 -> FragmentOutput(viewModel)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { currentFragment = 1 }) { Text("Fragment One") }
            Button(onClick = { currentFragment = 2 }) { Text("Input Fragment") }
            Button(onClick = { currentFragment = 3 }) { Text("Output Fragment") }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFragmentLifecycleApp() {
    FragmentLifecycleApp()
}
