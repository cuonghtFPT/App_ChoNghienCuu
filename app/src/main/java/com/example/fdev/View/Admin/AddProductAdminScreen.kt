package com.example.fdev.View.Admin
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fdev.R
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import com.example.fdev.ViewModel.ProductAdminViewModel
import com.example.fdev.model.ProductAdminRequest

@Composable
fun AddProductScreen(navController: NavController, viewModel: ProductAdminViewModel = viewModel()) {
    var productName by remember { mutableStateOf("") }
    var productPrice by remember { mutableStateOf("") }
    var productDescription by remember { mutableStateOf("") }
    var productImageUrl by remember { mutableStateOf("") }

    val productResponse by viewModel.productResponse.observeAsState()
    val errorMessage by viewModel.errorMessage.observeAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(20.dp, top = 25.dp, end = 20.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painterResource(id = R.drawable.left_black),
                contentDescription = null,
                modifier = Modifier
                    .size(25.dp)
                    .clickable {
                        navController.popBackStack()
                    },
                contentScale = ContentScale.FillBounds,
            )
            Text(
                text = "Add Product Admin",
                modifier = Modifier.align(Alignment.CenterVertically),
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "",
                modifier = Modifier.align(Alignment.CenterVertically),
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Product Name
        OutlinedTextField(
            value = productName,
            onValueChange = { productName = it },
            label = { Text("Product") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Product Price
        OutlinedTextField(
            value = productPrice,
            onValueChange = { productPrice = it },
            label = { Text("Price") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Product Description
        OutlinedTextField(
            value = productDescription,
            onValueChange = { productDescription = it },
            label = { Text("Describe") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 4
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Product Image URL
        OutlinedTextField(
            value = productImageUrl,
            onValueChange = { productImageUrl = it },
            label = { Text("Image") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(52.dp))

        // Save Button
        Button(
            onClick = {
                val price = productPrice.toDoubleOrNull()
                if (price != null && price >= 0) {
                    viewModel.addProduct(
                        ProductAdminRequest(
                            name = productName,
                            price = price,
                            description = productDescription,
                            image = productImageUrl,
                            type = "ProductType"
                        )
                    )
                } else {
                    Toast.makeText(context, "Price must be greater than or equal to 0", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.size(290.dp, 50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xff242424)
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "SAVE PRODUCT",
                fontSize = 16.sp
            )
        }

        LaunchedEffect(productResponse, errorMessage) {
            productResponse?.let {
                Toast.makeText(context, "Product added successfully!", Toast.LENGTH_SHORT).show()
                navController.navigate("CONGRATSADMIN")  // Replace "NextScreenRoute" with your actual route
            }
            errorMessage?.let {
                if (it.isNotEmpty()) {
                    Toast.makeText(context, "Product Add Failed!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddProductScreenPreview() {
    val navController = rememberNavController()
    AddProductScreen(navController)
}