package com.example.fdev.View.User

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fdev.R

@Composable
fun PaymentMethodScreen(navController: NavController) {
    // Khai báo biến selectedCheckBox ở đây
    var selectedCheckBox by remember { mutableStateOf(-1) } // Lưu trữ trạng thái của checkbox được chọn

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("ADDPAYMENTMETHOD")
                },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.add_icon),
                    contentDescription = "Add Payment Method",
                    modifier = Modifier
                        .size(20.dp)
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(20.dp)
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
                    painter = painterResource(id = R.drawable.left_black),
                    contentDescription = "back",
                    modifier = Modifier
                        .size(25.dp)
                        .clickable {
                            navController.popBackStack()
                        },
                    contentScale = ContentScale.FillBounds,
                )
                Text(
                    text = "Thêm phương thức thanh toán",
                    modifier = Modifier.align(Alignment.CenterVertically),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.width(25.dp))
            }

            PaymentCard(
                cardNumber = "* * * *  * * * *  * * * *  3947",
                cardHolderName = "NGUYEN ANH TUAN",
                expiryDate = "06/26"
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                // Checkbox đầu tiên
                Checkbox(
                    checked = selectedCheckBox == 0, // Kiểm tra xem checkbox đầu tiên có được chọn không
                    onCheckedChange = {
                        if (it) selectedCheckBox = 0 // Khi được chọn, đặt giá trị thành 0
                    },
                    modifier = Modifier.padding(start = 10.dp)
                )
                Text(
                    text = "Sử dụng phương thức làm mặc định",
                    fontSize = 17.sp,
                )
            }

            PaymentCard(
                cardNumber = "* * * *  * * * *  * * * *  3947",
                cardHolderName = "NGUYEN ANH TUAN",
                expiryDate = "06/26"
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                // Checkbox thứ hai
                Checkbox(
                    checked = selectedCheckBox == 1, // Kiểm tra xem checkbox thứ hai có được chọn không
                    onCheckedChange = {
                        if (it) selectedCheckBox = 1 // Khi được chọn, đặt giá trị thành 1
                    },
                    modifier = Modifier.padding(start = 10.dp)
                )
                Text(
                    text = "Use as default payment method",
                    fontSize = 17.sp,
                )
            }
        }
    }
}

@Composable
fun PaymentCard(cardNumber: String, cardHolderName: String, expiryDate: String) {
    Box(
        modifier = Modifier
            .padding(10.dp, top = 20.dp, end = 10.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(Color.Black)
            .fillMaxWidth()
            .height(210.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize()
        ) {
            Image(
                painterResource(id = R.drawable.pain),
                contentDescription = null,
                modifier = Modifier.padding(start = 25.dp, bottom = 18.dp, top = 20.dp)
            )

            Text(
                text = cardNumber,
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 25.dp)
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Card Holder Name",
                    color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 28.dp, start = 25.dp)
                )
                Text(
                    text = "Expiry Date",
                    color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 28.dp, start = 90.dp)
                )
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = cardHolderName,
                    color = Color.White,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 5.dp, start = 25.dp)
                )
                Text(
                    text = expiryDate,
                    color = Color.White,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 5.dp, start = 65.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewPaymentMethol() {
    var navController = rememberNavController()
    PaymentMethodScreen(navController)
}