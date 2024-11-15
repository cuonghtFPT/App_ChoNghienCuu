package com.example.fdev.View.User

import CartViewModel
import FavoriteItemRow
import FavouriteViewModel
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fdev.model.Product

data class FavouriteItem(
    val product: Product,
    val name: String,
    val price: Number,
    val image: String
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    navController: NavHostController,
    favouriteViewModel: FavouriteViewModel = viewModel(),
    cartViewModel: CartViewModel = viewModel()
) {
    val favouriteItems by favouriteViewModel.favouriteItems.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        favouriteViewModel.getFavouriteItems()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {
                            navController.navigate("SEARCH")
                        }) {
                            Icon(Icons.Default.Search, contentDescription = "Search")
                        }
                        Text(
                            text = "Favorites",
                            style = MaterialTheme.typography.headlineSmall,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(onClick = {
                            navController.navigate("CART")
                        }) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = "Cart")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                LazyColumn(
                    contentPadding = innerPadding,
                    modifier = Modifier
                        .weight(1f) // Chiếm không gian còn lại
                        .padding(horizontal = 16.dp)
                ) {
                    items(favouriteItems) { item ->
                        FavoriteItemRow(item, onRemoveItem = {
                            favouriteViewModel.removeFromFavourites(item.name)
                        })
                    }
                }
            }

            // Nút Add to Cart cố định ở dưới cùng
            Button(
                onClick = {
                    cartViewModel.addAllFavoritesToCart(favouriteItems)
                    Toast.makeText(context, "All favorite products added to cart", Toast.LENGTH_LONG).show()
                },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .align(Alignment.BottomCenter) // Căn giữa dưới cùng
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(16.dp)
                    .shadow(elevation = 2.dp, shape = RoundedCornerShape(10.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xff242424))
            ) {
                Text(
                    text = "Add all to cart",
                    color = Color.White
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun FavoritesScreenPreview() {
    FavoritesScreen(navController = rememberNavController())
}
