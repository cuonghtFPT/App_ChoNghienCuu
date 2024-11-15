
package com.example.fdev.View.Design

import DesignViewModel
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fdev.model.DesignRequest
@Composable
fun UpdateProductScreenDesigner(
    navController: NavController,
    viewModel: DesignViewModel = viewModel(),
    designId: String,
    designName: String = "",
    designPrice: String = "",
    designDescription: String = "",
    designImageUri: String = ""
) {
    Log.e("ID: ", "UpdateProductScreenDesigner: " +designId)
    var designNameState by remember { mutableStateOf(designName) }
    var designPriceState by remember { mutableStateOf(designPrice) }
    var designDescriptionState by remember { mutableStateOf(designDescription) }
    var designImageState by remember { mutableStateOf(designImageUri) }

    var productImageUri by remember { mutableStateOf<Uri?>(null) }
    var imageBytes by remember { mutableStateOf<ByteArray?>(null) }

    val updateDesignResponse by viewModel.addDesignResponse.observeAsState()
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
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Các trường nhập dữ liệu sản phẩm và chọn ảnh
        OutlinedTextField(
            value = designNameState + (updateDesignResponse?.id ?: ""),
            onValueChange = { designNameState = it },
            label = { Text("Tên sản phẩm") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = designPriceState,
            onValueChange = { designPriceState = it },
            label = { Text("Giá sản phẩm") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = designDescriptionState,
            onValueChange = { designDescriptionState = it },
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

        // Nút cập nhật sản phẩm
        Button(
            onClick = {
                navController.navigate("CONGRATSADMIN")
                val price = designPriceState.toDoubleOrNull()
                if (price != null && price >= 0) {
                    // Truyền mảng byte ảnh cho API nếu có hình ảnh
                    Log.e("Check image", "UpdateProductScreenDesigner: img: "+ designImageState, )
                    viewModel.updateDesign(
                        designId,
                        DesignRequest(
                            name = designNameState,
                            price = price,
                            description = designDescriptionState,
                            imageUri = productImageUri.toString(), // Truyền URI ảnh
                            type = "ProductType" // Cập nhật loại sản phẩm nếu cần
                        ),
                        context
                    )
                    Toast.makeText(context, "Cập nhật sản phẩm thành công!", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(context, "Giá phải lớn hơn 0 và cần chọn ảnh", Toast.LENGTH_SHORT).show()
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
                text = "Cập nhật sản phẩm",
                fontSize = 16.sp,
                color = Color.White
            )
        }



        // Xử lý thông báo từ API
        LaunchedEffect(updateDesignResponse, errorMessage) {
            updateDesignResponse?.let {
                navController.navigate("CONGRATSADMIN") {
                    popUpTo("UpdateProductScreenDesigner") { inclusive = true }
                Toast.makeText(context, "Cập nhật sản phẩm thành công!", Toast.LENGTH_SHORT).show()
            }
            errorMessage?.let {
                if (it.isNotEmpty()) {
                    Toast.makeText(context, "Cập nhật sản phẩm thất bại: $it", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
}
