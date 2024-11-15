package com.example.fdev.View.User

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fdev.R
import com.example.fdev.ViewModel.ReviewViewModel
import com.example.fdev.model.ReviewRequest
import com.example.fdev.model.ReviewResponse
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ReviewScreen(navController: NavController, productId: String, productName: String) {
    val viewModel: ReviewViewModel = viewModel()
    val reviews by viewModel.reviews.collectAsState(initial = emptyList())
    var rating by remember { mutableStateOf(0) }
    var comment by remember { mutableStateOf("") }

    // Get current user from Firebase
    val currentUser = FirebaseAuth.getInstance().currentUser
    val userName = currentUser?.displayName ?: "Ẩn danh"

    // Fetch reviews
    viewModel.fetchReviews(productId)

    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painterResource(id = R.drawable.left_black),
                contentDescription = null,
                modifier = Modifier
                    .size(25.dp)
                    .clickable { navController.popBackStack() }, // Back action
                contentScale = ContentScale.FillBounds,
            )
            Text(
                text = "Đánh giá & Nhận xét",
                modifier = Modifier.align(Alignment.CenterVertically),
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "",
                modifier = Modifier.align(Alignment.CenterVertically),
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium
            )
        }

        // Form viết đánh giá
        Column(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Tên sản phẩm và người dùng
            Text(text = "Người dùng: $userName", fontSize = 16.sp)

            // Rating stars (1 to 5)
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                for (i in 1..5) {
                    IconButton(onClick = { rating = i }) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = if (i <= rating) Color.Yellow else Color.Gray,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Ô nhập bình luận
            OutlinedTextField(
                value = comment,
                onValueChange = { comment = it },
                label = { Text(text = "Nhập bình luận của bạn") },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )

            // Nút "Viết đánh giá"
            Button(
                onClick = {
                    if (rating in 1..5 && comment.isNotBlank()) {
                        val reviewRequest = ReviewRequest(
                            productId = productId,
                            userName = userName,
                            comment = comment,
                            rating = rating
                        )
                        viewModel.postReview(reviewRequest)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFd86d42)
                ),
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Viết đánh giá",
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Default,
                    color = Color.White
                )
            }
        }

        // Danh sách các đánh giá
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            LazyColumn {
                items(reviews.size) { index ->
                    ReviewItem(reviews[index])
                }
            }
        }
    }
}

@Composable
fun ReviewItem(review: ReviewResponse) {
    Card(
        modifier = Modifier
            .padding(top = 20.dp, start = 10.dp, end = 10.dp)
            .clip(RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .background(Color.LightGray)
            .clickable { /* Handle click action */ }
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .clip(RoundedCornerShape(15.dp)),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.avatar_test), // Use a dynamic avatar based on user
                contentDescription = "avatar",
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(50.dp))
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = review.userName,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = review.createdAt, // Assuming createdAt is in a suitable format
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            Row {
                repeat(review.rating) {
                    Icon(
                        painter = painterResource(id = R.drawable.star1), // Replace with your star icon
                        contentDescription = null,
                        tint = Color.Yellow,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            Text(
                text = review.comment,
                fontSize = 14.sp,
                textAlign = TextAlign.Justify
            )
        }
    }
}
