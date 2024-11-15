package com.example.fdev.View.Design

import CartViewModel
import DesignViewModel
import FavouriteViewModel
import android.net.Uri
import android.util.Log
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.fdev.R
import com.example.fdev.View.User.checkAndRequestPermissions
import com.example.fdev.View.User.downloadImage
import com.example.fdev.model.DesignResponse
import com.example.fdev.model.Product


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ViewProductDetailsDesignerPreview(){
    ViewProductDetailsDesigner(navController= rememberNavController(),cartViewModel= CartViewModel())
}
@Composable
fun ViewProductDetailsDesigner(navController: NavHostController,cartViewModel: CartViewModel) {
    val designViewModel: DesignViewModel = viewModel()  // Sử dụng DesignViewModel
    val scrollState = rememberScrollState()
    val design = navController.previousBackStackEntry?.savedStateHandle?.get<DesignResponse>("design")
    val product = navController.previousBackStackEntry?.savedStateHandle?.get<Product>("product")
    val context = LocalContext.current
    val favouriteViewModel : FavouriteViewModel = viewModel()
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
            design?.let {
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
            design?.let {
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
                design?.let {
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
                text = design?.description ?: "",
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
                    .fillMaxSize()
                    .padding(top = 50.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = {
                        product?.let {
                            favouriteViewModel.addToFavourite(it, quantity = 1)
                            Toast.makeText(context, "Favourite added to successfully", Toast.LENGTH_LONG).show()
                        } ?: run {
                            Toast.makeText(context, "Favourite not found", Toast.LENGTH_SHORT).show()
                        }/*TODO*/ },
                    modifier = Modifier
                        .size(60.dp)
                        .background(color = Color(0xffF0F0F0), shape = RoundedCornerShape(10.dp))
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.favourite),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }


                Button(
                    onClick = {
                        product?.let {
                            if (it.price.toDouble() == 0.0) {
                                // In ra URL hình ảnh trong Logcat
                                Log.d("Download", "Downloading image from URL: ${it.image}")
                                if (checkAndRequestPermissions(context)) {
                                    downloadImage(context, it.image)
                                } else {
                                    Toast.makeText(context, "Permission denied", Toast.LENGTH_LONG).show()
                                }
                            } else {
                                // Logic thêm vào giỏ hàng
                                cartViewModel.addToCart(it)
                                Toast.makeText(context, "Product added to cart successfully", Toast.LENGTH_LONG).show()
                                navController.navigate("CART")
                            }
                        }
                    },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .height(60.dp)
                        .shadow(
                            elevation = 2.dp,
                            shape = RoundedCornerShape(10.dp)
                        ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xff242424)
                    )
                ) {
                    Text(
                        text = if (product?.price?.toDouble() == 0.0) "Download" else "Add to cart",
                        fontFamily = FontFamily.Serif
                    )
                }
            }
        }
    }
}
