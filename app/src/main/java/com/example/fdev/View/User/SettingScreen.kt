package com.example.fdev.View.User

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fdev.R
import com.google.firebase.auth.FirebaseAuth

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun SettingScreen() {
    LayoutSetting(navController = rememberNavController())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LayoutSetting(navController: NavHostController) {

    val painter0: Painter = painterResource(id = R.drawable.back)
    val painter1: Painter = painterResource(id = R.drawable.admin)
    val painter2: Painter = painterResource(id = R.drawable.edit)
    val painter3: Painter = painterResource(id = R.drawable.next)

    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser

    var name by remember { mutableStateOf(currentUser?.displayName ?: "") }
    var email by remember { mutableStateOf(currentUser?.email ?: "") }
    var PassWord by remember { mutableStateOf("") }
    var promotionsEnabled by remember { mutableStateOf(false) }
    var newProductsEnabled by remember { mutableStateOf(false) }
    var contentUpdatesEnabled by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, top = 50.dp, end = 16.dp, bottom = 16.dp)
            .verticalScroll(scrollState)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painter0,
                contentDescription = "Back",
                modifier = Modifier
                    .width(20.dp)
                    .height(20.dp)
                    .clickable {
                        navController.popBackStack()
                    }
            )
            Text(
                text = "Setting",
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    fontSize = 20.sp
                ),
                textAlign = TextAlign.Center
            )
            Image(
                painter = painter1,
                contentDescription = null,
                modifier = Modifier
                    .width(30.dp)
                    .height(30.dp)
                    .clip(CircleShape)
            )
        }
        Spacer(modifier = Modifier.height(50.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Thông tin cá nhân",
                style = TextStyle(
                    fontSize = 20.sp,
                    color = Color(0xFF909191)
                )
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp)
        ) {// Tên (pre-filled from Firebase)
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Tên") },
                modifier = Modifier
                    .fillMaxWidth()
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
            Spacer(modifier = Modifier.height(20.dp))

            // Email (pre-filled from Firebase)
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
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
                ),
                enabled = false  // Disable this field so users cannot edit the email
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Thông báo",
                style = TextStyle(
                    fontSize = 20.sp,
                    color = Color(0xFF909191)
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .background(color = Color(0xFFe7e7e7))
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Khuyến mãi",
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = Color(0xFF909191),
                    ),
                    modifier = Modifier.weight(1f)
                )
                Switch(
                    checked = promotionsEnabled,
                    onCheckedChange = { isChecked ->
                        promotionsEnabled = isChecked
                    },
                    modifier = Modifier
                        .width(60.dp)
                        .height(30.dp)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .background(color = Color(0xFFe7e7e7))
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Sản phẩm mới",
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = Color(0xFF909191),
                    ),
                    modifier = Modifier.weight(1f)
                )
                Switch(
                    checked = newProductsEnabled,
                    onCheckedChange = { isChecked ->
                        newProductsEnabled = isChecked
                    },
                    modifier = Modifier
                        .width(60.dp)
                        .height(30.dp)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .background(color = Color(0xFFe7e7e7))
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Cập nhật nội dung",
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = Color(0xFF909191),
                    ),
                    modifier = Modifier.weight(1f)
                )
                Switch(
                    checked = contentUpdatesEnabled,
                    onCheckedChange = { isChecked ->
                        contentUpdatesEnabled = isChecked
                    },
                    modifier = Modifier
                        .width(60.dp)
                        .height(30.dp)
                )
            }
        }
    }
}

// ở màn hình này phúc đã bỏ phần hiển thị password do tính năng bảo mật của firebase