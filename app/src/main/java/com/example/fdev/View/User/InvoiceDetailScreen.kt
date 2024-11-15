package com.example.fdev.View.User

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter

// Data class cho sản phẩm
data class Product(val imageUrl: String, val name: String, val price: String)

@Composable
fun InvoiceDetailScreen(navController: NavHostController, userName: String, products: List<Product>, totalAmount: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Hiển thị tên người dùng phía trên
        Text(
            text = "User: $userName",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp)
        )

        // Hiển thị danh sách sản phẩm ở giữa
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(products) { product ->
                ProductItem(product)
            }
        }

        // Hiển thị tổng giá tiền ở dưới
        Text(
            text = "Total: $totalAmount",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp)
        )
    }
}

@Composable
fun ProductItem(product: Product) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Hiển thị hình ảnh sản phẩm
        Image(
            painter = rememberImagePainter(data = product.imageUrl),
            contentDescription = null,
            modifier = Modifier
                .size(60.dp)
                .padding(8.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            // Hiển thị tên sản phẩm
            Text(text = product.name, fontSize = 18.sp, fontWeight = FontWeight.Bold)

            // Hiển thị giá sản phẩm
            Text(text = product.price, fontSize = 14.sp, color = Color.Gray)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InvoiceDetailScreenPreview() {
    val sampleProducts = listOf(
        Product(imageUrl = "https://via.placeholder.com/150", name = "Minimal Stand", price = "$15.0"),
        Product(imageUrl = "https://via.placeholder.com/150", name = "Franz fz", price = "$30.0"),
        Product(imageUrl = "https://via.placeholder.com/150", name = "Finlay Studio", price = "$20.0"),
        Product(imageUrl = "https://via.placeholder.com/150", name = "Giày thể thao", price = "$1200000.0")
    )
    InvoiceDetailScreen(
        navController = rememberNavController(),
        userName = "John Doe",
        products = sampleProducts,
        totalAmount = "$1200000.0"
    )
}
