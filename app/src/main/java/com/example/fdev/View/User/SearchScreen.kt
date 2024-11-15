package com.example.fdev.View.User


import RetrofitService
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.fdev.ViewModel.ProductViewModel
import com.example.fdev.model.Product


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavHostController, retrofitService: RetrofitService) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    var searchResults by remember { mutableStateOf(emptyList<Product>()) }
    var isLoading by remember { mutableStateOf(false) }


    val productViewModel: ProductViewModel = remember { ProductViewModel() }
    val products by productViewModel.productList


    LaunchedEffect(Unit) {
        productViewModel.fetchProductList()
    }


    // Cập nhật searchResults khi danh sách sản phẩm được lấy từ API
    LaunchedEffect(products) {
        searchResults = products.filter { it.name.contains(searchQuery.text, ignoreCase = true) }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "Search",
                            style = MaterialTheme.typography.headlineSmall,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(5f)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                    // Cập nhật kết quả tìm kiếm mỗi khi có thay đổi
                    searchResults = products.filter { product ->
                        product.name.contains(searchQuery.text, ignoreCase = true) // Tìm kiếm không phân biệt chữ hoa thường
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .background(Color.White, RoundedCornerShape(50.dp)),
                shape = RoundedCornerShape(50.dp),
                placeholder = { Text(text = "Search...") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.Gray
                )
            )


            Text(
                text = "Results for \"${searchQuery.text}\"",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth()
            )
            Divider(color = Color.Gray, thickness = 1.dp)


            Spacer(modifier = Modifier.height(16.dp))


            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(searchResults) { product ->
                    SearchResultItem(product)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}


@Composable
fun SearchResultItem(item: Product) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberImagePainter(data = item.image),
            contentDescription = item.name,
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )


        Spacer(modifier = Modifier.width(16.dp))


        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = item.name,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = "${item.price}",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 14.sp,
                    color = Color.Black
                )
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    SearchScreen(navController = rememberNavController(), retrofitService = RetrofitService())
}

