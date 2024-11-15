package com.example.fdev.View.User


import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fdev.R
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LayoutLoginScreen(navController: NavHostController) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()

    // Trạng thái cho checkbox lưu tài khoản
    var rememberMe by remember { mutableStateOf(false) }

    // Đọc SharedPreferences để biết người dùng có chọn lưu tài khoản hay không
    val sharedPreferences = context.getSharedPreferences("login_prefs", Context.MODE_PRIVATE)
    val savedEmail = sharedPreferences.getString("email", "")
    val savedPassword = sharedPreferences.getString("password", "")

    // Khởi tạo email và mật khẩu nếu đã lưu
    var email by remember { mutableStateOf(savedEmail ?: "") }
    var password by remember { mutableStateOf(savedPassword ?: "") }
    var isShowPass by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 80.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .height(1.dp)
                        .background(color = Color(0xFFd86d42))
                ) {}
                Image(
                    painter = painterResource(id = R.drawable.loogo),
                    contentDescription = "logo",
                    modifier = Modifier
                        .padding(start = 30.dp, end = 30.dp)
                        .size(50.dp, 50.dp)
                        .weight(1f)
                )
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .height(1.dp)
                        .background(color = Color(0xFFd86d42))
                ) {}
            }
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.padding(top = 70.dp, start = 10.dp, bottom = 15.dp)
                ) {
                    Text(
                        text = "Hello !",
                        fontFamily = FontFamily.Serif,
                        color = Color(0xFFC9AB9F),
                        fontWeight = FontWeight(500),
                        fontSize = 35.sp
                    )
                    Text(
                        text = "WELCOME BACK",
                        fontFamily = FontFamily.Serif,
                        color = Color(0xFFd86d42),
                        fontWeight = FontWeight(700),
                        fontSize = 35.sp
                    )
                }
                Column(
                    modifier = Modifier
                        .size(370.dp, 440.dp)
                        .shadow(
                            elevation = 3.dp,
                            shape = RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp)
                        )
                        .background(
                            Color.White,
                            shape = RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp)
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 20.dp, end = 20.dp, top = 30.dp, bottom = 30.dp
                            )
                    ) {
                        Column {
                            Text(
                                text = "Email",
                                color = Color(0xFFd86d42),
                                fontSize = 15.sp,
                                fontFamily = FontFamily.Serif,
                                modifier = Modifier.padding(bottom = 3.dp)
                            )
                            TextField(
                                value = email,
                                onValueChange = { email = it },
                                modifier = Modifier.fillMaxWidth(),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color(0xffE0E0E0),
                                    focusedIndicatorColor = Color(0xffE0E0E0),
                                    cursorColor = Color(0xFFd86d42)
                                ),
                                textStyle = TextStyle(
                                    fontFamily = FontFamily.Serif
                                ),
                            )
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Column {
                            Text(
                                text = "Password",
                                color = Color(0xFFd86d42),
                                fontSize = 15.sp,
                                fontFamily = FontFamily.Serif,
                                modifier = Modifier.padding(bottom = 3.dp)
                            )
                            TextField(
                                value = password,
                                onValueChange = { password = it },
                                modifier = Modifier.fillMaxWidth(),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color(0xffE0E0E0),
                                    focusedIndicatorColor = Color(0xffE0E0E0),
                                    cursorColor = Color(0xFFd86d42)
                                ),
                                trailingIcon = {
                                    IconButton(onClick = {
                                        isShowPass = !isShowPass
                                    }) {
                                        Icon(
                                            painter = painterResource(
                                                id = if (isShowPass) R.drawable.an else R.drawable.show
                                            ),
                                            contentDescription = null,
                                            modifier = Modifier.size(20.dp, 20.dp)
                                        )
                                    }
                                },
                                visualTransformation = if (isShowPass) VisualTransformation.None else PasswordVisualTransformation(),
                                textStyle = TextStyle(
                                    fontFamily = FontFamily.Serif
                                ),
                            )
                        }
                        Spacer(modifier = Modifier.height(15.dp))

                        // Checkbox để lưu tài khoản
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = rememberMe,
                                onCheckedChange = { rememberMe = it },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = Color(0xFFd86d42)
                                )
                            )
                            Text(
                                text = "Remember me",
                                fontFamily = FontFamily.Serif,
                                fontSize = 16.sp,
                                color = Color(0xFFd86d42)
                            )
                        }

                        Spacer(modifier = Modifier.height(15.dp))

                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Button(
                                onClick = {
                                    // Kiểm tra xem email hoặc mật khẩu có rỗng hay không
                                    if (email.isBlank()) {
                                        Toast.makeText(context, "Email không được để trống", Toast.LENGTH_LONG).show()
                                    } else if (password.isBlank()) {
                                        Toast.makeText(context, "Mật khẩu không được để trống", Toast.LENGTH_LONG).show()
                                    } else {
                                        auth.signInWithEmailAndPassword(email, password)
                                            .addOnCompleteListener { task ->
                                                if (task.isSuccessful) {
                                                    val user = auth.currentUser
                                                    val name = user?.displayName

                                                    // Lưu trạng thái đăng nhập nếu checkbox được chọn
                                                    if (rememberMe) {
                                                        sharedPreferences.edit().apply {
                                                            putString("email", email)
                                                            putString("password", password)
                                                            apply()
                                                        }
                                                    }

                                                    Toast.makeText(context, "Chào mừng, $name!", Toast.LENGTH_LONG).show()
                                                    navController.navigate("HOME")
                                                } else {
                                                    Toast.makeText(context, "Đăng nhập thất bại: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                                                }
                                            }
                                    }
                                },
                                modifier = Modifier.size(290.dp, 50.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFd86d42)
                                ),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = "Login",
                                    fontFamily = FontFamily.Serif,
                                    fontWeight = FontWeight(600)
                                )
                            }
                            Text(
                                text = "SIGN UP",
                                modifier = Modifier
                                    .padding(top = 20.dp)
                                    .selectable(
                                        selected = true,
                                        onClick = {
                                            navController.navigate("REGISTER")
                                        }
                                    ),
                                fontSize = 18.sp,
                                fontFamily = FontFamily.Serif,
                                color = Color(0xFFd86d42)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun LoginScreen() {
    LayoutLoginScreen(navController = rememberNavController())
}
