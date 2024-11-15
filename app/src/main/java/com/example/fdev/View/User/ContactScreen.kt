package com.example.fdev.View.User

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fdev.R
import com.google.firebase.auth.FirebaseAuth

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ContactScreen() {
    LayoutContact(navController = rememberNavController())
}
@Composable
fun LayoutContact(navController: NavHostController) {

    val painter0: Painter = painterResource(id = R.drawable.back)
    val painter1: Painter = painterResource(id = R.drawable.iconadmin)
    val painter2: Painter = painterResource(id = R.drawable.phone)
    val painter3: Painter = painterResource(id = R.drawable.mail)
    val painter4: Painter = painterResource(id = R.drawable.hom)
    val scrollState = rememberScrollState()

    val context = LocalContext.current

    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    val email = currentUser?.email ?: "Unknown Email"  // Fetching user's email from Firebase

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
                text = "Thông tin liên hệ ",
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    fontSize = 20.sp
                ),
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painter1,
                contentDescription = null,
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .clip(CircleShape)
            )
        }
        Spacer(modifier = Modifier.height(50.dp))

        // Box for phone number
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
                        val intent = Intent(Intent.ACTION_DIAL)
                        intent.data = Uri.parse("tel:0981139895")
                        context.startActivity(intent)
                    }
            ) {
                Image(
                    painter = painter2,
                    contentDescription = null,
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                        .padding(5.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = " 098 113 9895",
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = Color(0xFF909191),
                    ),
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(50.dp))

        // Box for email
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
                        Toast.makeText(context,"Opening email",Toast.LENGTH_SHORT).show()
                        navController.navigate("MAIL")
                    }
            ) {
                Image(
                    painter = painter3,
                    contentDescription = null,
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                        .padding(5.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = email,  // Show user's email from Firebase
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = Color(0xFF909191),
                    ),
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(50.dp))

        // Box for address
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
                    painter = painter4,
                    contentDescription = null,
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                        .padding(5.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = " Trịnh Văn Bô, Bắc Từ Liêm, Hà Nội ",
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = Color(0xFF909191),
                    ),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

