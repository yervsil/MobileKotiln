package com.example.finalassignment


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    fun addToCart(product: Product) {
        val updatedCart = _cartItems.value.toMutableList()
        val existingItem = updatedCart.find { it.productId == product.productId }

        if (existingItem != null) {
            val updatedItem = existingItem.copy(quantity = existingItem.quantity + 1)
            updatedCart[updatedCart.indexOf(existingItem)] = updatedItem
        } else {
            updatedCart.add(
                CartItem(
                    productId = product.productId,
                    name = product.name,
                    quantity = 1,
                    price = product.price
                )
            )
        }

        _cartItems.value = updatedCart
    }

    fun incrementQuantity(productId: Int) {
        _cartItems.value = _cartItems.value.map { item ->
            if (item.productId == productId) item.copy(quantity = item.quantity + 1) else item
        }
    }

    fun decrementQuantity(productId: Int) {
        _cartItems.value = _cartItems.value.mapNotNull { item ->
            when {
                item.productId == productId && item.quantity > 1 -> item.copy(quantity = item.quantity - 1)
                item.productId == productId -> null // Удаляем товар, если количество = 0
                else -> item
            }
        }
    }

    fun removeFromCart(productId: Int) {
        _cartItems.value = _cartItems.value.filter { it.productId != productId }
    }



    fun getCartTotal(): Double {
        return _cartItems.value.sumOf { it.quantity * it.price }
    }
}

class ProductViewModel(private val ProductService: ProductService, val productDao: ProductDao) : ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())


    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            try {
                // Загружаем данные из API
                val fakeProducts = listOf(
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
                    Product(20, "Fitness Band", "Track steps, heart rate, and sleep.", 50.0, category = "Wearables") )
                _products.value = fakeProducts

                // Очищаем базу данных и добавляем новые данные
                productDao.clearAllProducts()
                productDao.insertProducts(fakeProducts.map { product ->
                    ProductEntity(
                        productId = product.productId,
                        name = product.name,
                        description = product.description,
                        price = product.price,
                        imageUrl = product.imageUrl,
                        category = product.category
                    )
                })
            } catch (e: Exception) {
                // Если API недоступен, загружаем данные из базы данных
                productDao.getAllProducts().collect { productEntities ->
                    _products.value = productEntities.map { entity ->
                        Product(
                            productId = entity.productId,
                            name = entity.name,
                            description = entity.description,
                            price = entity.price,
                            imageUrl = entity.imageUrl,
                            category = entity.category
                        )
                    }
                }
            }
        }
    }
}



