package com.example.fdev.View.User


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.fdev.model.ContactMailResponse // Đảm bảo đã import kiểu dữ liệu này
import com.example.fdev.ViewModel.NotificationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(navController: NavHostController) {
    val notificationViewModel: NotificationViewModel = viewModel()
    val contactList by notificationViewModel.NotificationItem.collectAsState() // Theo dõi trạng thái danh sách contact

    // Gọi hàm để lấy danh sách contact
    LaunchedEffect(Unit) {
        notificationViewModel.getContactList()
    }

    Column (
        modifier = Modifier.fillMaxWidth()
    ){
        Text(
            text = "NOTIFICATION",
            modifier = Modifier.fillMaxWidth(),
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = FontFamily.Serif,
            textAlign = TextAlign.Center
        )

        // Hiển thị danh sách contact
        LazyColumn {
            items(contactList) { contact ->
                NotificationItemRow(contact)
            }
        }
    }
}

@Composable
fun NotificationItemRow(contact: ContactMailResponse) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = contact.name, style = MaterialTheme.typography.titleMedium)
            Text(text = contact.content, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationScreenPreview() {
    NotificationScreen(navController = rememberNavController())
}
