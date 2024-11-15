package com.example.fdev.View.Admin


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fdev.R
import com.example.fdev.components.setingItem
import com.example.fdev.model.Profile
import com.google.firebase.auth.FirebaseAuth


@Composable
fun ProfileAdminScreen(navController: NavController) {
    var showLogoutDialog = remember { mutableStateOf(false) } // Trạng thái hiển thị hộp thoại
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser

    // Auto-fill user's name and email from Firebase
    var name by remember { mutableStateOf(currentUser?.displayName ?: "Unknown") }
    var email by remember { mutableStateOf(currentUser?.email ?: "Unknown") }

    Column(
        modifier = Modifier
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
                painterResource(id = R.drawable.search),
                contentDescription = null,
                modifier = Modifier
                    .size(25.dp)
                    .clickable { /* sự kiện onClick */ },
                contentScale = ContentScale.FillBounds
            )
            Text(
                text = "PROFILE",
                modifier = Modifier.align(Alignment.CenterVertically),
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily.Serif,
            )
            Image(
                painterResource(id = R.drawable.log_out),
                contentDescription = null,
                modifier = Modifier
                    .size(25.dp)
                    .clickable { showLogoutDialog.value = true }, // Hiển thị hộp thoại khi ấn vào
                contentScale = ContentScale.FillBounds
            )
        }
        Spacer(modifier = Modifier.height(15.dp))

        TopNotifi(name = name, email = email)

        LazyColumn {
            items(setingItem.size) { index ->
                ItemSeting1(setingItem[index], navController)
            }
        }

        // Hiển thị hộp thoại đăng xuất
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
    }
}

@Composable
fun TopNotifi(name: String, email: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        ) {
            Image(
                painterResource(id = R.drawable.avatar_test),
                contentDescription = "avatar",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(50.dp))
            )
            Column(
                modifier = Modifier
                    .padding(start = 20.dp, top = 15.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = name,  // Hiển thị tên người dùng
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = email,  // Hiển thị email người dùng
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(top = 5.dp)
                )
            }
        }
    }
}

@Composable
fun ItemSeting1(item: Profile, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .height(80.dp)
            .background(Color.LightGray)
            .clickable {
                navController.navigate(item.route)  // Điều hướng theo route của từng item
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(5.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = item.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = item.content)
            }
            Image(
                painterResource(id = item.iconResId),
                contentDescription = null,
                modifier = Modifier.size(25.dp),
                contentScale = ContentScale.FillBounds,
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
    }
}

@Composable
fun LogoutConfirmationDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(text = "Xác nhận đăng xuất")
        },
        text = {
            Text("Bạn có chắc muốn đăng xuất không?")
        },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text("Có")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Không")
            }
        }
    )
}
