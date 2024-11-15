package com.example.fdev.View.User

import RetrofitService
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fdev.R
import com.example.fdev.model.PaymentResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log
import java.text.Normalizer
import java.util.Calendar
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.rememberCoroutineScope
import com.example.fdev.model.BillingAddress
import com.example.fdev.model.PaymentRequest
import kotlinx.coroutines.launch

@Composable
fun AddPaymentMethod(navController: NavHostController,retrofitService:RetrofitService) {

    // State variables for user input
    var cardHolderName by remember { mutableStateOf("") }
    var cardNumber by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }

    // SnackbarHostState để hiển thị thông báo
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // Error states for validation
    var cardNumberError by remember { mutableStateOf(false) }
    var cvvError by remember { mutableStateOf(false) }
    var expiryDateError by remember { mutableStateOf(false) }

    // Function to remove diacritics (dấu) from the input
    fun removeDiacritics(input: String): String {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
            .replace(Regex("\\p{InCombiningDiacriticalMarks}+"), "") // Remove accents/diacritics
            .replace(Regex("[^a-zA-Z0-9 ]"), "") // Allow only letters, digits, and spaces
    }

    // Modified isCardNumberValid function to handle spaces in card number
    fun isCardNumberValid(cardNumber: String): Boolean {
        val cleanedCardNumber = cardNumber.replace(" ", "") // Loại bỏ khoảng trắng
        return cleanedCardNumber.length == 16 && cleanedCardNumber.all { it.isDigit() } && cleanedCardNumber.startsWith("4")
    }

    fun isCvvValid(cvv: String): Boolean {
        return cvv.length == 3 && cvv.all { it.isDigit() }
    }

    fun isExpiryDateValid(expiryDate: String): Boolean {
        val parts = expiryDate.split("/")
        if (parts.size != 2) return false
        val month = parts[0].toIntOrNull()
        val year = parts[1].toIntOrNull()
        if (month == null || year == null || month !in 1..12) return false

        // Sử dụng Calendar để lấy năm và tháng hiện tại
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR) % 100  // Lấy 2 số cuối của năm
        val currentMonth = calendar.get(Calendar.MONTH) + 1  // Calendar.MONTH bắt đầu từ 0

        return year > currentYear || (year == currentYear && month >= currentMonth)
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) } // Đặt SnackbarHost vào Scaffold
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(20.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header với nút quay lại và tiêu đề
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painterResource(id = R.drawable.left_black),
                    contentDescription = "back",
                    modifier = Modifier
                        .size(25.dp)
                        .clickable { navController.popBackStack() }, // Quay lại màn hình trước
                    contentScale = ContentScale.FillBounds,
                )
                Text(
                    text = "Thêm phương thức thanh toán",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.size(25.dp)) // Chừa khoảng trống cho căn chỉnh
            }

            // Giao diện hiển thị thông tin thẻ
            Box(
                modifier = Modifier
                    .padding(10.dp, top = 20.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color.Black)
                    .fillMaxWidth()
                    .height(210.dp),
            ) {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxSize()
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.Top
                    ) {
                        Image(
                            painterResource(id = R.drawable.pain), // Thay đổi với logo của bạn
                            contentDescription = null,
                            modifier = Modifier
                                .padding(start = 16.dp, top = 16.dp)
                                .size(40.dp) // Logo nhỏ
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Image(
                            painterResource(id = R.drawable.visa_logo), // Logo thẻ Visa
                            contentDescription = null,
                            modifier = Modifier
                                .padding(end = 16.dp, top = 16.dp)
                                .size(50.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Cập nhật số thẻ theo người dùng nhập
                    Text(
                        text = if (cardNumber.length >= 16) {
                            "${cardNumber.take(4)} **** **** ${cardNumber.takeLast(4)}"
                        } else {
                            "* * * *  * * * *  * * * *  XXXX"
                        },
                        color = Color.White,
                        fontSize = 22.sp,
                        modifier = Modifier.padding(start = 16.dp, top = 10.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 1.dp)
                    ) {
                        Column {
                            Text(
                                text = "Card Holder Name",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Light
                            )
                            Text(
                                text = if (cardHolderName.isNotBlank()) cardHolderName else "XXXXXX",
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }

                        Column(
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = "Expiry Date",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Light
                            )
                            Text(
                                text = if (expiryDate.isNotBlank() && expiryDate.contains("/")) expiryDate else "XX/XX",
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }
            }

            // Input cho tên chủ thẻ, không cho phép nhập ký tự có dấu
            OutlinedTextField(
                value = cardHolderName,
                onValueChange = {
                    cardHolderName = removeDiacritics(it) // Loại bỏ dấu và ký tự không hợp lệ
                },
                label = { Text("CardHolder Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                shape = RoundedCornerShape(10.dp),
                singleLine = true
            )

            // Input cho số thẻ với kiểm tra hợp lệ
            OutlinedTextField(
                value = cardNumber,
                onValueChange = {
                    cardNumber = it
                    cardNumberError = !isCardNumberValid(it) // Nếu hợp lệ, trạng thái lỗi được cập nhật
                },
                label = { Text("Card Number") },
                isError = cardNumberError,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                shape = RoundedCornerShape(10.dp),
                singleLine = true
            )

            // Input cho CVV và ngày hết hạn với kiểm tra hợp lệ
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    OutlinedTextField(
                        value = cvv,
                        onValueChange = {
                            cvv = it
                            cvvError = !isCvvValid(it)
                        },
                        label = { Text("CVV") },
                        shape = RoundedCornerShape(10.dp),
                        singleLine = true
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                ) {
                    OutlinedTextField(
                        value = expiryDate,
                        onValueChange = {
                            expiryDate = it
                            expiryDateError = !isExpiryDateValid(it)
                        },
                        label = { Text("Expiry Date (MM/YY)") },
                        isError = expiryDateError,
                        shape = RoundedCornerShape(10.dp),
                        singleLine = true
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Nút thêm thẻ mới
            Button(
                onClick = {
                    // Kiểm tra các trường dữ liệu
                    cardNumberError = !isCardNumberValid(cardNumber)
                    cvvError = !isCvvValid(cvv)
                    expiryDateError = !isExpiryDateValid(expiryDate)

                    if (!cardNumberError && !cvvError && !expiryDateError) {
                        // Gọi API thêm thẻ mới
                        val paymentRequest = PaymentRequest(
                            userId = "12345",
                            cardNumber = cardNumber.takeLast(4),
                            nameOnCard = cardHolderName,
                            expiryMonth = expiryDate.split("/")[0].toInt(),
                            expiryYear = expiryDate.split("/")[1].toInt(),
                            type = "Visa",
                            cardType = "Credit",
                            bankName = "BankName",
                            billingAddress = BillingAddress(
                                street = "123 Street",
                                city = "City",
                                postalCode = "Postal Code",
                                country = "Country"
                            ),
                            image = null
                        )

                        val call = retrofitService.fdevApiService.addPayment(paymentRequest)
                        call.enqueue(object : Callback<PaymentResponse> {
                            override fun onResponse(
                                call: Call<PaymentResponse>,
                                response: Response<PaymentResponse>
                            ) {
                                if (response.isSuccessful) {
                                    // Hiển thị thông báo thành công và quay lại màn hình Checkout
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar("Thêm thẻ thành công")
                                    }
                                    navController.popBackStack("CHECKOUT", false) // Quay lại màn hình Checkout
                                } else {
                                    Log.e("AddPaymentMethod", "Failed response: ${response.errorBody()?.string()}")
                                }
                            }

                            override fun onFailure(call: Call<PaymentResponse>, t: Throwable) {
                                Log.e("AddPaymentMethod", "Request failed: ${t.message}")
                            }
                        })
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                shape = RoundedCornerShape(8.dp),
                elevation = ButtonDefaults.elevatedButtonElevation(8.dp)
            ) {
                Text(
                    text = "Thêm thẻ mới",
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewAddPayment() {
    val navController = rememberNavController()
    AddPaymentMethod(navController, retrofitService = RetrofitService())
}









