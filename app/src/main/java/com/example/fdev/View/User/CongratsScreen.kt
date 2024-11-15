package com.example.fdev.View.User

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fdev.R

@Composable
fun CongratsScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .padding(top = 90.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Thành Công!",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )
        Image(
            painterResource(id = R.drawable.tick_notifi),
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
        )
        Text(
            text = "Thank you for choosing our app!",
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(25.dp)
        )
    }
    btnSingUp(navController)
}

@Composable
fun btnSingUp(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp, 70.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {navController.navigate("BILL")/* Su kien OnClick*/ },
            colors = ButtonDefaults.buttonColors(
                Color.Black,
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier

        ) {
            Text(
                text = "Xem Chi Tiết",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontFamily = FontFamily.Default,
                modifier = Modifier
                    .size(width = 285.dp, height = 30.dp)
                    .clickable {navController.navigate("BILL")},
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        Button(
            onClick = {
                navController.navigate("HOME")
            },
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, color = Color.Black),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
        ) {
            Text(
                text = "Quay Lại Trang Chủ",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontFamily = FontFamily.Default,
                modifier = Modifier
                    .size(width = 285.dp, height = 30.dp),
                color = Color.Black
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewCongrats() {
    var navController = rememberNavController()
    CongratsScreen(navController)
}