package com.example.fdev.View.Admin

import CartViewModel
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.fdev.R
import com.example.fdev.ViewModel.ProductAdminViewModel
import com.example.fdev.model.Product

@Composable
fun ProductAdmin(navController: NavHostController) {
    val productAdminViewModel: ProductAdminViewModel = viewModel()
    val cartViewModel: CartViewModel = viewModel()
    val scrollState = rememberScrollState()
    val product = navController.previousBackStackEntry?.savedStateHandle?.get<Product>("product")
    val context = LocalContext.current

    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        // Box layout for image and radio buttons
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(455.dp)
                .padding(top = 30.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            product?.let {
                Image(
                    painter = rememberImagePainter(data = it.image),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 65.dp)
                        .fillMaxSize()
                        .width(200.dp)
                        .clip(RoundedCornerShape(bottomStart = 50.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            // Back button
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .padding(end = 260.dp, top = 20.dp)
                    .shadow(elevation = 3.dp, shape = RoundedCornerShape(10.dp))
                    .background(color = Color.White, shape = RoundedCornerShape(10.dp))
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_back),
                    contentDescription = null,
                    modifier = Modifier.size(15.dp)
                )
            }

            // Radio buttons
            Column(
                modifier = Modifier
                    .padding(end = 260.dp, top = 120.dp)
                    .shadow(elevation = 3.dp, shape = RoundedCornerShape(30.dp))
                    .background(color = Color.White, shape = RoundedCornerShape(10.dp))
                    .padding(10.dp)
            ) {
                CustomRadioButton(
                    selected = true,
                    onClick = { /*TODO*/ },
                    outerColor = Color.Gray,
                    innerColor = Color.White
                )
                Spacer(modifier = Modifier.height(20.dp))
                CustomRadioButton(
                    selected = false,
                    onClick = { /*TODO*/ },
                    outerColor = Color(0xffF0F0F0),
                    innerColor = Color(0xffB4916C)
                )
                Spacer(modifier = Modifier.height(20.dp))
                CustomRadioButton(
                    selected = false,
                    onClick = { /*TODO*/ },
                    outerColor = Color(0xffE4CBAD),
                    innerColor = Color(0xffE4CBAD)
                )
            }
        }

        // Product details
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 15.dp, top = 20.dp)
        ) {
            product?.let {
                Text(
                    text = it.name,
                    fontWeight = FontWeight(500),
                    fontFamily = FontFamily.Serif,
                    fontSize = 24.sp,
                )
            }

            Row(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                product?.let {
                    Text(
                        text = "${it.price}",
                        fontFamily = FontFamily.Serif,
                        fontSize = 30.sp,
                        fontWeight = FontWeight(700),
                    )
                }
            }

            // Star rating
            Row(
                modifier = Modifier.padding(top = 25.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.sta),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "4.5",
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight(700),
                    modifier = Modifier.padding(start = 7.dp)
                )
                Text(
                    text = "(50 reviews)",
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Serif,
                    color = Color(0xff808080),
                    modifier = Modifier.padding(start = 10.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
            }

            // Product description
            Text(
                text = product?.description ?: "",
                modifier = Modifier.padding(top = 20.dp),
                textAlign = TextAlign.Justify,
                fontSize = 17.sp,
                color = Color(0xff606060),
                lineHeight = 22.sp,
                fontFamily = FontFamily.Serif
            )

            // Favourite and Add to Cart buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.Black)
                        .weight(1f)
                        .height(60.dp)
                        .clickable {
                            showDialog = true
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Delete to cart",
                        color = Color.White,
                        fontSize = 20.sp
                    )
                }
                Box(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.Black)
                        .weight(1f)
                        .height(60.dp)
                        .clickable {
                            product?.let {
                                navController.navigate("updateProduct/${it.id}/${Uri.encode(it.name)}/${it.price}/${Uri.encode(it.description)}/${Uri.encode(it.image)}")                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Update to cart",
                        color = Color.White,
                        fontSize = 20.sp
                    )
                }
            }

            // Confirmation dialog
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text(text = "Xác nhận xóa sản phẩm") },
                    text = { Text("Bạn có chắc chắn muốn xóa sản phẩm này không?") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                product?.let {
                                    productAdminViewModel.deleteProduct(it.id)
                                }
                                showDialog = false
                                navController.navigate("CONGRATSADMIN") // Navigate to previous screen
                            }
                        ) {
                            Text("OK")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { showDialog = false }
                        ) {
                            Text("Hủy")
                        }
                    }
                )
            }
        }
    }
}

// Reuse CustomRadioButton component
@Composable
fun CustomRadioButton(
    selected: Boolean,
    onClick: () -> Unit,
    outerColor: Color,
    innerColor: Color
) {
    Box(
        modifier = Modifier
            .size(24.dp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(20.dp)
                .background(color = outerColor, shape = CircleShape)
        )
        if (selected) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(color = innerColor, shape = CircleShape)
            )
        }
    }
}
