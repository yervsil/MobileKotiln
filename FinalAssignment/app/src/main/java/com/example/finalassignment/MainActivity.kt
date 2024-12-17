package com.example.finalassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Modifier

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val cartViewModel: CartViewModel = viewModel()
                var showCartScreen by remember { mutableStateOf(false) }
                var selectedProduct by remember { mutableStateOf<Product?>(null) }

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("Shopping App") },
                            actions = {
                                IconButton(onClick = { showCartScreen = true }) {
                                    Icon(Icons.Default.ShoppingCart, contentDescription = "Cart")
                                }
                            }
                        )
                    }
                ) { paddingValues ->
                    Box(modifier = Modifier.padding(paddingValues)) {
                        when {
                            showCartScreen -> {

                                CartScreen(cartViewModel) {
                                    showCartScreen = false
                                }
                            }

                            selectedProduct != null -> {
                                // Детальный экран товара
                                ProductDetailScreen(
                                    product = selectedProduct!!,
                                    onBack = { selectedProduct = null }
                                )
                            }

                            else -> {
                                // Экран списка товаров
                                ProductListScreen(
                                    products = sampleProducts(),
                                    onAddToCart = { product ->
                                        cartViewModel.addToCart(product)
                                    },
                                    onProductClick = { product ->
                                        selectedProduct = product
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }


    private fun sampleProducts(): List<Product> {
        return listOf(
            Product(1, "Laptop", "A powerful laptop for professionals.", 1500.0, category = "Electronics"),
            Product(2, "Smartphone", "Latest Android smartphone with 5G support.", 800.0, category = "Electronics"),
            Product(3, "Headphones", "Noise-cancelling wireless headphones.", 200.0, category = "Accessories"),
            Product(4, "Smartwatch", "Track your fitness and stay connected.", 150.0, category = "Wearables"),
            Product(5, "Gaming Console", "Next-gen gaming console with 4K support.", 500.0, category = "Gaming"),
            Product(6, "Wireless Mouse", "Ergonomic wireless mouse with fast response.", 40.0, category = "Accessories"),
            Product(7, "Keyboard", "Mechanical keyboard with RGB lighting.", 70.0, category = "Accessories"),
            Product(8, "Monitor", "27-inch 4K Ultra HD monitor for sharp visuals.", 300.0, category = "Electronics"),
            Product(9, "Tablet", "Portable tablet for work and entertainment.", 600.0, category = "Electronics"),
            Product(10, "Bluetooth Speaker", "Portable speaker with deep bass.", 120.0, category = "Accessories"),
            Product(11, "Power Bank", "10,000mAh power bank for on-the-go charging.", 25.0, category = "Accessories"),
            Product(12, "External Hard Drive", "1TB external hard drive for backups.", 80.0, category = "Accessories"),
            Product(13, "Camera", "DSLR camera with 24MP lens.", 1000.0, category = "Electronics"),
            Product(14, "Printer", "Wireless printer for home and office.", 150.0, category = "Electronics"),
            Product(15, "Router", "High-speed router for seamless connectivity.", 90.0, category = "Electronics"),
            Product(16, "Coffee Maker", "Brew delicious coffee in minutes.", 70.0, category = "Home Appliances"),
            Product(17, "Vacuum Cleaner", "Robotic vacuum cleaner for effortless cleaning.", 350.0, category = "Home Appliances"),
            Product(18, "Air Fryer", "Healthy cooking with less oil.", 120.0, category = "Home Appliances"),
            Product(19, "Electric Toothbrush", "Smart electric toothbrush for better hygiene.", 60.0, category = "Personal Care"),
            Product(20, "Fitness Band", "Track steps, heart rate, and sleep.", 50.0, category = "Wearables"))
    }
}


