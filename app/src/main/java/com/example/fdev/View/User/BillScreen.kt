package com.example.fdev.View.User


import CartItem
import CartViewModel
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.google.firebase.auth.FirebaseAuth




@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LayoutBillScreen(navController: NavHostController,cartViewModel: CartViewModel = viewModel()) {
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    var name by remember { mutableStateOf(currentUser?.displayName ?: "") }
    val billItems by cartViewModel.cartItems.collectAsState()  // Lấy danh sách sản phẩm trong giỏ hàng
    val totalPrice = cartViewModel.getTotalPrice()  // Tính tổng giá


    LaunchedEffect(Unit) {
        // Lấy giỏ hàng của người dùng khi màn hình được hiển thị
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
//                        IconButton(onClick = {
//                            navController.popBackStack()
//                        }) {
//                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
//                        }
                        Text(
                            text = "Hóa đơn thanh toán của bạn",
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically  // Đảm bảo các thành phần căn giữa theo chiều dọc
                ) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Tên") },
                        modifier = Modifier
                            .weight(1f)  // Sử dụng weight để OutlinedTextField chiếm không gian còn lại
                            .background(Color.White, RoundedCornerShape(size = 8.dp))
                            .border(1.dp, Color(0xFF909191), RoundedCornerShape(size = 8.dp)),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            focusedLabelColor = Color.Black,
                            unfocusedLabelColor = Color.Gray,
                        ),
                        shape = RoundedCornerShape(8.dp),
                        textStyle = TextStyle(
                            fontFamily = FontFamily.Serif
                        )
                    )


                    Spacer(modifier = Modifier.width(16.dp))  // Thêm khoảng cách giữa OutlinedTextField và văn bản


                    // Kiểm tra tổng giá trị có phải là null hay không
                    Text(
                        text = "Tổng hoá đơn: $${totalPrice?.toString() ?: "0"}",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterVertically)  // Căn giữa văn bản theo chiều dọc
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))


                Button(
                    onClick = {
                        navController.navigate("CONGRATSSCREEN")
                    },  // Điều hướng đến màn hình thanh toán
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                ) {
                    Text("OK, hiểu rồi", color = Color.White)
                }
                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    ) {innerPadding ->
        if (billItems.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyColumn(
                contentPadding = innerPadding,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxSize()
            ) {
                items(billItems) { item ->
                    BillItemRow(item)
                }
            }
        }


    }
}




@Composable
fun BillItemRow(item: CartItem) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Hiển thị ảnh sản phẩm
        Image(
            painter = rememberImagePainter(data = item.image),
            contentDescription = item.name,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(12.dp)),  // Bo góc ảnh
            contentScale = ContentScale.Crop  // Cắt ảnh theo kích thước
        )


        Spacer(modifier = Modifier.width(16.dp))


        // Hiển thị tên và giá sản phẩm
        Column(
            modifier = Modifier.weight(1f)  // Chiếm không gian còn lại
        ) {
            // Tên sản phẩm
            Text(
                text = item.name,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            // Giá sản phẩm
            Text(
                text = "$${item.price}",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BillScreen() {
    LayoutBillScreen(navController = rememberNavController())
}

