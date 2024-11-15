package com.example.fdev.View.User

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fdev.R
import java.text.Normalizer

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun HelpScreen() {
    LayoutHelp(navController = rememberNavController())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LayoutHelp(navController: NavHostController) {
    val painter0: Painter = painterResource(id = R.drawable.banner)
    val painter1: Painter = painterResource(id = R.drawable.video)
    val painter2: Painter = painterResource(id = R.drawable.admin)
    val painter3: Painter = painterResource(id = R.drawable.chinhsua)
    val painter4: Painter = painterResource(id = R.drawable.dammay)
    val painter5: Painter = painterResource(id = R.drawable.iconadmin)
    val painter6: Painter = painterResource(id = R.drawable.phattructiep)
    val painter7: Painter = painterResource(id = R.drawable.baomat)
    val painter8: Painter = painterResource(id = R.drawable.goi)
    val painter9: Painter = painterResource(id = R.drawable.error)

    var search by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    // Danh sách các mục với tên tiếng Việt
    val items = listOf(
        Pair("Tài Khoản", painter2),
        Pair("Liên hệ ngay", painter5),
        Pair("Cơ bản về ảnh / video", painter1),
        Pair("Chỉnh sửa và sản xuất", painter3),
        Pair("Lưu trữ phương tiện", painter4),
        Pair("Phát trực tiếp", painter6),
        Pair("Bảo mật và pháp lý", painter7),
        Pair("Gói đăng ký", painter8),
        Pair("Khắc phục sự cố & FAQ", painter9)
    )

    // Lọc danh sách dựa trên giá trị tìm kiếm
    val filteredItems = items.filter {
        // Kiểm tra xem mục có chứa giá trị tìm kiếm (có dấu, không dấu hoặc tiếng Anh)
        it.first.contains(search, ignoreCase = true) ||
                removeVietnameseDiacritics(it.first).contains(removeVietnameseDiacritics(search), ignoreCase = true) ||
                it.first.contains(search, ignoreCase = true) // Tìm kiếm bằng tiếng Anh
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painter0,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
        ) {
            OutlinedTextField(
                value = search,
                onValueChange = { search = it },
                placeholder = { Text("Tìm kiếm tại đây ... ") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color.White, RoundedCornerShape(size = 8.dp))
                    .border(
                        width = 1.dp,
                        color = Color(0xFF909191),
                        shape = RoundedCornerShape(size = 8.dp)
                    ),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Gray,
                ),
                shape = RoundedCornerShape(size = 8.dp),
                textStyle = TextStyle(
                    fontFamily = FontFamily.Serif
                )
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        // Hiển thị danh sách đã lọc
        for (item in filteredItems) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
                    .clip(RoundedCornerShape(12.dp))
                    .clickable {
                        when (item.first) {
                            "Tài Khoản" -> {
                                Toast.makeText(context, "Successfully transferred to account screen", Toast.LENGTH_SHORT).show()
                                navController.navigate("ACCOUNTS")
                            }
                            "Liên hệ ngay" -> {
                                Toast.makeText(context, "Successfully moved to help screen", Toast.LENGTH_SHORT).show()
                                navController.navigate("CONTACT")
                            }
                            "Cơ bản về ảnh / video" -> {
                                Toast.makeText(context, "Navigating to Basics of Images/Videos", Toast.LENGTH_SHORT).show()
                            }
                            "Chỉnh sửa và sản xuất" -> {
                                Toast.makeText(context, "Navigating to Editing and Production", Toast.LENGTH_SHORT).show()
                            }
                            "Lưu trữ phương tiện" -> {
                                Toast.makeText(context, "Navigating to Media Storage", Toast.LENGTH_SHORT).show()
                            }
                            "Phát trực tiếp" -> {
                                Toast.makeText(context, "Navigating to Live Streaming", Toast.LENGTH_SHORT).show()
                            }
                            "Bảo mật và pháp lý" -> {
                                Toast.makeText(context, "Navigating to Security and Legal", Toast.LENGTH_SHORT).show()
                                navController.navigate("PRIVACYPOICY")
                            }
                            "Gói đăng ký" -> {
                                Toast.makeText(context, "Navigating to Subscription Packages", Toast.LENGTH_SHORT).show()
                            }
                            "Khắc phục sự cố & FAQ" -> {
                                Toast.makeText(context, "Navigating to Troubleshooting & FAQ", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
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
                        painter = item.second,
                        contentDescription = null,
                        modifier = Modifier
                            .width(50.dp)
                            .height(50.dp)
                            .padding(5.dp)
                            .clip(CircleShape)
                    )
                    Text(
                        text = item.first,
                        style = TextStyle(
                            fontSize = 20.sp,
                            color = Color(0xFF909191),
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}

// Hàm để loại bỏ dấu tiếng Việt
fun removeVietnameseDiacritics(str: String): String {
    val normalized = Normalizer.normalize(str, Normalizer.Form.NFD)
    return normalized.replace(Regex("\\p{InCombiningDiacriticalMarks}+"), "")
        .replace(Regex("[^\\p{ASCII}]"), "") // Loại bỏ các ký tự không phải ASCII
}
