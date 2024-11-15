package com.example.fdev.View.User

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
import com.google.firebase.auth.UserProfileChangeRequest

@Composable
fun LayoutRegisterScreen(navController: NavHostController) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()

    var isShowPass by remember { mutableStateOf(false) }
    var isShowConfirmPass by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var confirmpass by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {

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
            Column(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier.padding(
                        top = 20.dp, start = 10.dp, bottom = 15.dp
                    )
                ) {
                    Text(
                        text = "WELCOME",
                        fontFamily = FontFamily.Serif,
                        color = Color(0xFFd86d42),
                        fontWeight = FontWeight(700),
                        fontSize = 35.sp
                    )
                }

                Column(
                    modifier = Modifier
                        .size(370.dp, 580.dp)
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
                        // Add the Name field
                        Column {
                            Text(
                                text = "Name",
                                color = Color(0xFFd86d42),
                                fontSize = 15.sp,
                                fontFamily = FontFamily.Serif,
                                modifier = Modifier.padding(bottom = 3.dp)
                            )
                            TextField(
                                value = name,
                                onValueChange = { name = it },
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
                                value = pass,
                                onValueChange = { pass = it },
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
                        Spacer(modifier = Modifier.height(20.dp))
                        Column {
                            Text(
                                text = "Confirm Password",
                                color = Color(0xFFd86d42),
                                fontSize = 15.sp,
                                fontFamily = FontFamily.Serif,
                                modifier = Modifier.padding(bottom = 3.dp)
                            )
                            TextField(
                                value = confirmpass,
                                onValueChange = { confirmpass = it },
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
                                        isShowConfirmPass = !isShowConfirmPass
                                    }) {
                                        Icon(
                                            painter = painterResource(
                                                id = if (isShowConfirmPass) R.drawable.an else R.drawable.show
                                            ),
                                            contentDescription = null,
                                            modifier = Modifier.size(20.dp, 20.dp)
                                        )
                                    }
                                },
                                visualTransformation = if (isShowConfirmPass) VisualTransformation.None else PasswordVisualTransformation(),
                                textStyle = TextStyle(
                                    fontFamily = FontFamily.Serif
                                ),
                            )
                        }
                        Spacer(modifier = Modifier.height(15.dp))

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 15.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Button(
                                onClick = {
                                    if (pass == confirmpass && pass.isNotEmpty()) {
                                        auth.createUserWithEmailAndPassword(email, pass)
                                            .addOnCompleteListener { task ->
                                                if (task.isSuccessful) {
                                                    // Update the user's display name in Firebase
                                                    val user = auth.currentUser
                                                    val profileUpdates = UserProfileChangeRequest.Builder()
                                                        .setDisplayName(name)
                                                        .build()
                                                    user?.updateProfile(profileUpdates)
                                                        ?.addOnCompleteListener { updateTask ->
                                                            if (updateTask.isSuccessful) {
                                                                Toast.makeText(context, "Registration Successful", Toast.LENGTH_LONG).show()
                                                                navController.navigate("login")
                                                            }
                                                        }
                                                } else {
                                                    Toast.makeText(context, "Registration Failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                                                }
                                            }
                                    } else {
                                        Toast.makeText(context, "Passwords do not match or are empty", Toast.LENGTH_LONG).show()
                                    }
                                },
                                modifier = Modifier.size(290.dp, 50.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFd86d42)
                                ),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = "Sign up",
                                    fontFamily = FontFamily.Serif,
                                    fontWeight = FontWeight(600)
                                )
                            }
                            Row {
                                Text(
                                    text = "Already have account?",
                                    modifier = Modifier.padding(top = 20.dp),
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily.Serif,
                                    color = Color(0xff909090)
                                )
                                Text(
                                    text = "LOGIN",
                                    modifier = Modifier
                                        .padding(top = 20.dp, start = 5.dp)
                                        .selectable(
                                            selected = true,
                                            onClick = {
                                                navController.navigate("login")
                                            }
                                        ),
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily.Serif,
                                    color = Color(0xFFd86d42),
                                    fontWeight = FontWeight.ExtraBold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterScreen() {
    LayoutRegisterScreen(navController = rememberNavController())
}

