package com.example.finalassignment


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch


@Composable
fun ProductListScreen(
    products: List<Product>,
    onAddToCart: (Product) -> Unit,
    onProductClick: (Product) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") } // Состояние для поиска
    var selectedCategory by remember { mutableStateOf("All") } // Состояние для категории

    // Получаем уникальные категории
    val categories = listOf("All") + products.map { it.category }.distinct()

    // Фильтрация товаров по категории и поиску
    val filteredProducts = products.filter { product ->
        (selectedCategory == "All" || product.category == selectedCategory) &&
                product.name.contains(searchQuery, ignoreCase = true)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Фильтрация по категориям
        CategoryDropdown(
            categories = categories,
            selectedCategory = selectedCategory,
            onCategorySelected = { selectedCategory = it }
        )

        // Поле поиска
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            singleLine = true
        )

        // Список товаров
        LazyColumn(modifier = Modifier.padding(8.dp)) {
            items(filteredProducts) { product ->
                ProductItem(
                    product = product,
                    onAddToCart = { onAddToCart(product) },
                    onClick = { onProductClick(product) }
                )
            }
        }

        // Сообщение, если товары не найдены
        if (filteredProducts.isEmpty()) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp)
            ) {
                Text("No products found", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    product: Product,
    onBack: () -> Unit
) {
    // Состояние для хранения списка отзывов
    var reviews by remember { mutableStateOf<List<Review>>(listOf()) }

    // Состояние для нового отзыва
    var newComment by remember { mutableStateOf("") }
    var newRating by remember { mutableStateOf(5) } // Рейтинг по умолчанию = 5

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(product.name) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            // Информация о продукте
            Text(product.name, style = MaterialTheme.typography.headlineMedium)
            Text("\$${product.price}", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(product.description, style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(16.dp))
            Divider()

            // Секция отзывов
            Text("Reviews", style = MaterialTheme.typography.titleLarge)
            if (reviews.isEmpty()) {
                Text("No reviews yet", style = MaterialTheme.typography.bodyMedium)
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(reviews) { review ->
                        ReviewItem(review)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Divider()

            // Форма добавления отзыва
            Text("Add a Review", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            // Поле ввода комментария
            OutlinedTextField(
                value = newComment,
                onValueChange = { newComment = it },
                label = { Text("Your Comment") },
                modifier = Modifier.fillMaxWidth()
            )

            // Рейтинг через Dropdown
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Rating:")
                DropdownRating(selectedRating = newRating) { selected ->
                    newRating = selected
                }
            }

            Spacer(modifier = Modifier.height(8.dp))


            Button(
                onClick = {
                    if (newComment.isNotBlank()) {

                        val newReview = Review(
                            reviewId = reviews.size + 1,
                            productId = product.productId,
                            userId = 1, // Статический userId для примера
                            rating = newRating,
                            comment = newComment
                        )
                        reviews = reviews + newReview
                        newComment = ""
                        newRating = 5
                    }
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Submit Review")
            }
        }
    }
}

@Composable
fun ReviewItem(review: Review) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text("Rating: ${review.rating} ⭐", style = MaterialTheme.typography.bodyMedium)
            Text(review.comment, style = MaterialTheme.typography.bodyLarge)
        }
    }
}



@Composable
fun DropdownRating(selectedRating: Int, onRatingSelected: (Int) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val ratings = (1..5).toList()

    Box {
        OutlinedButton(onClick = { expanded = true }) {
            Text("⭐ $selectedRating")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            ratings.forEach { rating ->
                DropdownMenuItem(
                    text = { Text("⭐ $rating") },
                    onClick = {
                        onRatingSelected(rating)
                        expanded = false
                    }
                )
            }
        }
    }
}


@Composable
fun CartItemRow(
    item: CartItem,
    onRemove: () -> Unit,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(item.name, style = MaterialTheme.typography.titleMedium)
                Text("Quantity: ${item.quantity}", style = MaterialTheme.typography.bodyMedium)
                Text("Total: \$${String.format("%.2f", item.price * item.quantity)}")
            }
            Row {
                IconButton(onClick = onDecrement) {
                    Icon(Icons.Default.Clear, contentDescription = "Decrease")
                }
                IconButton(onClick = onIncrement) {
                    Icon(Icons.Default.Add, contentDescription = "Increase")
                }
                IconButton(onClick = onRemove) {
                    Icon(Icons.Default.Delete, contentDescription = "Remove")
                }
            }
        }
    }
}

@Composable
fun CategoryDropdown(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.padding(8.dp)) {
        OutlinedButton(onClick = { expanded = true }) {
            Text("Category: $selectedCategory")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            categories.forEach { category ->
                DropdownMenuItem(
                    text = { Text(category) },
                    onClick = {
                        onCategorySelected(category)
                        expanded = false
                    }
                )
            }
        }
    }
}



@Composable
fun ProductItem(
    product: Product,
    onAddToCart: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() }, // Обработка нажатия на карточку
        elevation = CardDefaults.cardElevation(8.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Product Image",
                modifier = Modifier
                    .size(64.dp)
                    .padding(4.dp)
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = product.name, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "\$${String.format("%.2f", product.price)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Button(
                onClick = onAddToCart,
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text("Add")
            }
        }
    }
}


@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = Color(0xFF6200EE),
            secondary = Color(0xFF03DAC6),
            background = Color(0xFF121212),
            surface = Color(0xFF1E1E1E),
            error = Color(0xFFCF6679),
            onPrimary = Color.White,
            onSecondary = Color.Black,
            onBackground = Color.White,
            onSurface = Color.White,
            onError = Color.Black
        ),
        //typography = Typography,
        content = content
    )
}


@Composable
fun CartScreen(cartViewModel: CartViewModel, onBack: () -> Unit) {
    val cartItems = cartViewModel.cartItems.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Your Cart",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(cartItems.value) { item ->
                CartItemRow(
                    item = item,
                    onRemove = { cartViewModel.removeFromCart(item.productId) },
                    onIncrement = { cartViewModel.incrementQuantity(item.productId) },
                    onDecrement = { cartViewModel.decrementQuantity(item.productId) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Total: \$${String.format("%.2f", cartViewModel.getCartTotal())}",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back to Products")
        }
    }
}

@Composable
fun CartItemRow(item: CartItem, onRemove: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(text = item.name, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "Quantity: ${item.quantity}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "\$${String.format("%.2f", item.price * item.quantity)}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            IconButton(
                onClick = onRemove,
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Remove",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}


