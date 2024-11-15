package com.example.fdev.View.User

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fdev.R
import com.example.fdev.ViewModel.ContactViewModel
import com.example.fdev.model.ContactMailRequest
import com.google.firebase.auth.FirebaseAuth

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun MailScreen() {
    LayoutMail(navController = rememberNavController())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LayoutMail(navController: NavHostController, contactViewModel: ContactViewModel = viewModel()) {

    val painter0: Painter = painterResource(id = R.drawable.back)
    val painter1: Painter = painterResource(id = R.drawable.admin)

    // Firebase Authentication instance to get the current user
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser

    // Auto-fill user's name and email from Firebase
    var name by remember { mutableStateOf(currentUser?.displayName ?: "") }
    var email by remember { mutableStateOf(currentUser?.email ?: "") }
    var noiDung by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, top = 50.dp, end = 16.dp, bottom = 16.dp)
            .verticalScroll(scrollState)
    ) {
        // Tiêu đề và ảnh
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
                text = "Form liên hệ ",
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
        Spacer(modifier = Modifier.height(20.dp))

        // Tên (pre-filled from Firebase)
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

        // Nội dung yêu cầu (this is where the user will input their message)
        OutlinedTextField(
            value = noiDung,
            onValueChange = { noiDung = it },
            label = { Text("Nội dung yêu cầu") },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
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

        // Nút gửi
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(25.dp))
                .background(color = Color(0xFFe7e7e7))
                .clickable {
                    // Check if content is entered
                    if (noiDung.isBlank()) {
                        Toast.makeText(context, "Vui lòng nhập nội dung phản hồi!", Toast.LENGTH_SHORT).show()
                        return@clickable
                    }

                    // Tạo request body
                    val contactMailRequest = ContactMailRequest(
                        name = name,
                        email = email,
                        content = noiDung
                    )

                    // Gửi yêu cầu sử dụng ViewModel
                    contactViewModel.sendContactMail(
                        contactMailRequest,
                        onSuccess = {
                            Toast.makeText(context, "Gửi thành công!", Toast.LENGTH_SHORT).show()
                        },
                        onFailure = { errorMessage ->
                            Toast.makeText(context, "Thất bại: $errorMessage", Toast.LENGTH_SHORT).show()
                        }
                    )
                },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Gửi", color = Color.Black, fontSize = 18.sp)
        }
    }
}
