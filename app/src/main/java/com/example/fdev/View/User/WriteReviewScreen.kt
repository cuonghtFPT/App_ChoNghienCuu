package com.example.fdev.View.User

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fdev.R
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WriteReviewScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .padding(20.dp, top = 50.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
                    .clickable { navController.navigate("REVIEW")},
                contentScale = ContentScale.FillBounds,
            )
            Text(
                text = "Write a Review",
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

        Spacer(modifier = Modifier.height(20.dp))

        // Ô TextField với chữ mờ
        OutlinedTextField(
            value = "",
            onValueChange = { /* Handle text change */ },
            modifier = Modifier
                .padding(top = 30.dp)
                .fillMaxWidth()
                .height(150.dp), // Chiều cao của ô text
            placeholder = {
                Text(text = "Viết đánh giá...")
            },
            textStyle = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily.Default
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Nút Gửi đánh giá
        Button(
            onClick = { /* su kien onClick */ },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Submit a review",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontFamily = FontFamily.Default,
                color = Color.White,
                modifier = Modifier
                    .padding(vertical = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WriteReviewScreenPreview() {
    val navController = rememberNavController()
    WriteReviewScreen(navController)
}
