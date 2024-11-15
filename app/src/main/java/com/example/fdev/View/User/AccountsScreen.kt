package com.example.fdev.View.User

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
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
fun AccountsScreen() {
    LayoutAccounts(navController = rememberNavController())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LayoutAccounts(navController: NavHostController) {

    val painter0: Painter = painterResource(id = R.drawable.back)
    val painter1: Painter = painterResource(id = R.drawable.admin)
    val painter2: Painter = painterResource(id = R.drawable.log_out)

    val scrollState = rememberScrollState()
    val context = LocalContext.current

    // Lấy tên người dùng từ Firebase
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    val displayName = currentUser?.displayName ?: "Unknown User"  // Nếu không có tên thì hiện "Unknown User"
    var showLogoutDialog = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, top = 50.dp, end = 16.dp, bottom = 16.dp)
            .verticalScroll(scrollState)
    ) {
        Row(
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
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Chuyển tài khoản",
                modifier = Modifier.padding(horizontal = 8.dp),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    fontSize = 20.sp
                ),
                textAlign = TextAlign.Start
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .background(color = Color(0xFFfafafa))
                    .border(
                        width = 1.dp,
                        color = Color(0xFFfafafa),
                        shape = RoundedCornerShape(12.dp)
                    )
            ) {
                Image(
                    painter = painter1,
                    contentDescription = null,
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                        .padding(5.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = displayName,  // Hiển thị tên người dùng từ Firebase
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.weight(1f)
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        if (showLogoutDialog.value) {
            LogoutConfirmationDialog(
                onConfirm = {
                    showLogoutDialog.value = false
                    navController.navigate("LOGIN") // Điều hướng đến màn hình LayoutLoginScreen
                },
                onDismiss = {
                    showLogoutDialog.value = false // Ẩn hộp thoại nếu ấn hủy
                }
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .background(color = Color(0xFFfafafa))
                    .border(
                        width = 1.dp,
                        color = Color(0xFFfafafa),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable {
                        showLogoutDialog.value = true
                    }
            ) {
                Image(
                    painter = painter2,
                    contentDescription = null,
                    modifier = Modifier
                        .width(70.dp)
                        .height(50.dp)
                        .padding(5.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = " Đăng xuất tài khoản",
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
