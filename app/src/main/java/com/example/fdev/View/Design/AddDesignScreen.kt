package com.example.fdev.View.Design

import DesignViewModel
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.fdev.R
import com.example.fdev.model.DesignRequest

@Composable
fun AddDesignScreen(
    navController: NavHostController,
    viewModel: DesignViewModel = viewModel()
) {
    var productName by remember { mutableStateOf("") }
    var productPrice by remember { mutableStateOf("") }
    var productDescription by remember { mutableStateOf("") }
    var productImageUri by remember { mutableStateOf<Uri?>(null) }
    var imageBytes by remember { mutableStateOf<ByteArray?>(null) }

    val addDesignResponse by viewModel.addDesignResponse.observeAsState()
    val errorMessage by viewModel.errorMessage.observeAsState()
    val context = LocalContext.current

    // Lấy ảnh từ thư viện
    val getImage = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            productImageUri = uri
            uri?.let {
                // Lấy mảng byte từ URI
                val inputStream = context.contentResolver.openInputStream(it)
                imageBytes = inputStream?.readBytes()
                inputStream?.close()
            }
        }
    )

    Column(
        modifier = Modifier
            .padding(top = 60.dp)
            .padding(20.dp)
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()), // Thêm cuộn dọc khi nội dung dài
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Các trường nhập dữ liệu sản phẩm và chọn ảnh
        OutlinedTextField(
            value = productName,
            onValueChange = { productName = it },
            label = { Text("Tên sản phẩm") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = productPrice,
            onValueChange = { productPrice = it },
            label = { Text("Giá sản phẩm") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = productDescription,
            onValueChange = { productDescription = it },
            label = { Text("Mô tả sản phẩm") },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 100.dp),
            singleLine = false
        )

        // Nút chọn ảnh
        Button(
            onClick = { getImage.launch("image/*") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF059BEE)),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Chọn ảnh", color = Color.White)
        }

        productImageUri?.let { uri ->
            Text("Ảnh đã chọn: $uri", modifier = Modifier.padding(top = 10.dp))
        }

        // Nút lưu sản phẩm
        Button(
            onClick = {
                val price = productPrice.toDoubleOrNull()
                if (price != null && price >= 0 && productImageUri != null) {
                    // Truyền mảng byte ảnh cho API
                    viewModel.addDesign(
                        DesignRequest(
                            name = productName,
                            price = price,
                            description = productDescription,
                            imageUri = productImageUri.toString(),
                            type = "ProductType" // Cập nhật loại sản phẩm nếu cần
                        ),
                        context
                    )
                } else {
                    Toast.makeText(
                        context,
                        "Giá phải lớn hơn 0 và cần chọn ảnh",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF242424)),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Lưu sản phẩm",
                fontSize = 16.sp,
                color = Color.White
            )
        }


        // Xử lý thông báo từ API
        LaunchedEffect(addDesignResponse, errorMessage) {
            addDesignResponse?.let {
                navController.navigate("CONGRATSADMIN") {
                    popUpTo("AddDesignScreen") { inclusive = true }
                    Toast.makeText(context, "Thêm sản phẩm thành công!", Toast.LENGTH_SHORT).show()
                }
                errorMessage?.let {
                    if (it.isNotEmpty()) {
                        Toast.makeText(context, "Thêm sản phẩm thất bại: $it", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }
}
