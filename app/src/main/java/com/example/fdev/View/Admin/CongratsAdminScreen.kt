package com.example.fdev.View

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
fun CongratsAdminScreen(navController: NavController) {
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
            text = "Thank you for contributing products to our application.",
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(25.dp),
        )
    }
    btnSingUpAdmin(navController)
}

@Composable
fun btnSingUpAdmin(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp, 70.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { navController.navigate("home")},
            colors = ButtonDefaults.buttonColors(
                Color.Black,
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .size(width = 285.dp, height = 50.dp),

        ) {
            Text(
                text = "BACK TO HOME",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontFamily = FontFamily.Default,
            )
        }

        Spacer(modifier = Modifier.height(15.dp))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewCongratsAdmin() {
    var navController = rememberNavController()
    CongratsAdminScreen(navController)
}