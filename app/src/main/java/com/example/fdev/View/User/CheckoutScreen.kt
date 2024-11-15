package com.example.fdev.View.User

import RetrofitService
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.tooling.preview.Preview
import com.example.fdev.R
import com.example.fdev.model.PaymentData
import com.example.fdev.model.PaymentResponse
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(navController: NavHostController, totalPrice: String, retrofitService: RetrofitService) {
    var paymentMethods by remember { mutableStateOf(listOf<PaymentData>()) }

    // Lấy displayName và email từ Firebase
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    var displayName by remember { mutableStateOf(currentUser?.displayName ?: "Unknown") }
    var email by remember { mutableStateOf(currentUser?.email ?: "Unknown") }

    // API Call to fetch payment methods
    LaunchedEffect(Unit) {
        val call = retrofitService.fdevApiService.getPayments("12345")  // Thay thế bằng userId thực tế
        call.enqueue(object : Callback<PaymentResponse> {
            override fun onResponse(
                call: Call<PaymentResponse>,
                response: Response<PaymentResponse>
            ) {
                if (response.isSuccessful) {
                    val fetchedPayments = response.body()
                    if (fetchedPayments != null && fetchedPayments.data.isNotEmpty()) {
                        paymentMethods = fetchedPayments.data // Lưu danh sách thẻ vào biến
                        Log.d("CheckoutScreen", "Fetched payment methods: $paymentMethods")
                    } else {
                        Log.e("CheckoutScreen", "No payment methods found")
                    }
                } else {
                    Log.e("CheckoutScreen", "Failed to fetch payment methods: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<PaymentResponse>, t: Throwable) {
                Log.e("CheckoutScreen", "API call failed: ${t.message}")
            }
        })
    }

    // Scaffold layout with TopBar and LazyColumn for scrolling
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "Check out",
                            style = MaterialTheme.typography.headlineSmall,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(5f)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                // Receiving Email Address Section
                SectionHeader("Receiving Email Address")
                EditableInfoCard(title = displayName, subtitle = email)
            }

            item {
                // Payment Method Section
                SectionHeader("Payment Methods")
            }

            // LazyColumn items for payment methods
            items(paymentMethods) { paymentMethod ->
                PaymentMethodCard(paymentMethod)  // Truyền đối tượng PaymentData
            }

            item {
                if (paymentMethods.isEmpty()) {
                    Text(text = "No payment methods available", color = Color.Gray) // Hiển thị thông báo nếu không có phương thức thanh toán
                }
            }

            item {
                AddPaymentMethodButton(navController = navController)
            }

            item {
                // Note Section
                Text(
                    text = "Note",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = "After purchasing, the product will be sent to your email address, please check your email to receive the goods",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray),
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            item {
                // Order Summary Section
                OrderSummary(totalPrice = totalPrice)
            }

            item {
                // Submit Order Button
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        navController.navigate("CONGRATSSCREEN")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                ) {
                    Text("SUBMIT ORDER", color = Color.White, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.bodyMedium.copy(
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        ),
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
fun EditableInfoCard(title: String, subtitle: String) {
    var showDialog by remember { mutableStateOf(false) }
    var editableTitle by remember { mutableStateOf(title) }
    var editableSubtitle by remember { mutableStateOf(subtitle) }

    if (showDialog) {
        CustomDialog(
            title = editableTitle,
            subtitle = editableSubtitle,
            onDismiss = { showDialog = false },
            onConfirm = { newTitle, newSubtitle ->
                editableTitle = newTitle
                editableSubtitle = newSubtitle
                showDialog = false
            }
        )
    }

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFF5F5F5),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = editableTitle, style = MaterialTheme.typography.bodyMedium)
                Text(
                    text = editableSubtitle,
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
                )
            }
            IconButton(onClick = { showDialog = true }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit"
                )
            }
        }
    }
}

@Composable
fun CustomDialog(
    title: String,
    subtitle: String,
    onDismiss: () -> Unit,
    onConfirm: (String, String) -> Unit
) {
    var newTitle by remember { mutableStateOf(title) }
    var newSubtitle by remember { mutableStateOf(subtitle) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Edit Information") },
        text = {
            Column {
                OutlinedTextField(
                    value = newTitle,
                    onValueChange = { newTitle = it },
                    label = { Text("Title") }
                )
                OutlinedTextField(
                    value = newSubtitle,
                    onValueChange = { newSubtitle = it },
                    label = { Text("Subtitle") }
                )
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(newTitle, newSubtitle) }) {
                Text("Confirm")
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun PaymentMethodCard(paymentData: PaymentData) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFF5F5F5),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Box chứa logo thẻ
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.Gray, CircleShape)
                ) {
                    // Placeholder cho logo thẻ
                    val logo = when (paymentData.type) {
                        "Visa" -> R.drawable.visa_logo
                        "Mastercard" -> R.drawable.pain
                        else -> R.drawable.visa_logo
                    }
                    Image(
                        painter = painterResource(id = logo),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Hiển thị số thẻ dạng ẩn
                val maskedCardNumber = "**** **** **** ${paymentData.cardNumber.takeLast(4)}" // Hiển thị 4 số cuối từ API
                Text(
                    text = maskedCardNumber,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // Nút xóa thẻ
            IconButton(onClick = { /* Xử lý chỉnh sửa hoặc xóa thẻ */ }) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Remove"
                )
            }
        }
    }
}

@Composable
fun AddPaymentMethodButton(navController: NavHostController) {
    Button(
        onClick = {
            navController.navigate("PAYMENTMETHODSCREEN")
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
    ) {
        Text("+ Add payment method", color = Color.White)
    }
}

@Composable
fun OrderSummary(totalPrice: String) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFF5F5F5),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Divider(color = Color.Gray, thickness = 1.dp)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Total:", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                Text("$$totalPrice", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CheckoutScreenPreview() {
    CheckoutScreen(navController = rememberNavController(), retrofitService = RetrofitService(), totalPrice = "100.00")
}
