 package com.example.fdev.View.User

import CartItem
import CartViewModel
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaScannerConnection
import android.os.Environment
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.fdev.R
import com.example.fdev.View.User.BillItemRow
import com.example.fdev.View.User.checkAndRequestPermissions
import com.example.fdev.View.User.downloadImage
import com.google.firebase.auth.FirebaseAuth
import okhttp3.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LayoutHistoryBuyProductScreen(navController: NavHostController, cartViewModel: CartViewModel = viewModel()) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    var name by remember { mutableStateOf(currentUser?.displayName ?: "") }
    val billItems by cartViewModel.cartItems.collectAsState()
    val totalPrice = cartViewModel.getTotalPrice()
    val selectedItems = remember { mutableStateListOf<CartItem>() }

    LaunchedEffect(Unit) {
        cartViewModel.getCartItems()
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

                            navController.popBackStack()
                        }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                        Text(
                            text = "History of Purchased Products",
                            style = MaterialTheme.typography.headlineSmall,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(1f)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },

        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        // Kiểm tra quyền và tải xuống các mục đã chọn
                        if (checkAndRequestPermissions(context)) {
                            selectedItems.forEach { item ->
                                downloadImage(context, item.image)
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                ) {
                    Text("Tải xuống", color = Color.White)
                }
            }
        }
    ) { innerPadding ->
        if (billItems.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("No items available", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyColumn(
                contentPadding = innerPadding,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxSize()
            ) {
                items(billItems) { item ->
                    val isChecked = remember { mutableStateOf(false) }
                    HistoryItemRow(
                        item = item,
                        isChecked = isChecked.value,
                        onCheckedChange = { checked ->
                            isChecked.value = checked
                            if (checked) {
                                selectedItems.add(item)
                            } else {
                                selectedItems.remove(item)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun HistoryItemRow(
    item: CartItem,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
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
                .size(80.dp)
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
                text = "$${item.price}",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            )
        }

        // Checkbox ở góc phải
        androidx.compose.material3.Checkbox(
            checked = isChecked,
            onCheckedChange = { onCheckedChange(it) },
            modifier = Modifier.padding(end = 8.dp)
        )
    }
}




@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HistoryBuyProductScreen() {
    LayoutHistoryBuyProductScreen(navController = rememberNavController(), cartViewModel = CartViewModel())
}
