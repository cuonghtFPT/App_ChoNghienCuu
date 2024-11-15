package com.example.fdev.View.Admin


import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fdev.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationAdminScreen(navController: NavHostController) {
    val notifications = listOf(
        NotificationItem(
            title = "Your order #123456789 has been confirmed",
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Turpis pretium ut in arcu adipiscing nec.",
            status = "New",
            imageRes = R.drawable.anh1
        ),
        NotificationItem(
            title = "Your order #123456789 has been canceled",
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Turpis pretium ut in arcu adipiscing nec.",
            status = "",
            imageRes = R.drawable.anh2
        ),
        NotificationItem(
            title = "Discover best-selling products this week.",
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Turpis pretium ut in arcu adipiscing nec. Turpis pretium ut in arcu adipiscing nec.",
            status = "HOT!",
            imageRes = 0
        ),
        NotificationItem(
            title = "Your order #123456789 has been shipped successfully",
            description = "Please help us to confirm and rate your order to get 10% discount code for next order.",
            status = "",
            imageRes = R.drawable.anh3
        ),
        NotificationItem(
            title = "Your order #123456789 has been confirmed",
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Turpis pretium ut in arcu adipiscing nec.",
            status = "",
            imageRes = R.drawable.anh4
        ),
        NotificationItem(
            title = "Your order #123456789 has been canceled",
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Turpis pretium ut in arcu adipiscing nec.",
            status = "",
            imageRes = R.drawable.anh1
        ),
        NotificationItem(
            title = "Your order #123456789 has been shipped successfully",
            description = "Please help us to confirm and rate your order to get 10% discount code for next order.",
            status = "",
            imageRes = R.drawable.anh5
        )
    )

    val selectedIndex = remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { /* Do something */ }) {
                            Icon(Icons.Default.Search, contentDescription = "Search")
                        }
                        Text(
                            text = "Notification",
                            style = MaterialTheme.typography.headlineSmall,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(onClick = { /* Do something */ }) {
                            Icon(Icons.Default.Notifications, contentDescription = "Notifications")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {
            items(notifications) { notification ->
                NotificationItemRow(notification)
                Divider(
                    color = Color.LightGray,
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
fun NotificationItemRow(item: NotificationItem) {
    Column(modifier = Modifier.fillMaxWidth()) {
        if (item.imageRes != 0) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = item.imageRes),
                    contentDescription = item.title,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = item.description,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 14.sp,
                            color = Color.Gray
                        ),
                        maxLines = 2
                    )
                }

                if (item.status.isNotEmpty()) {
                    Text(
                        text = item.status,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (item.status == "New") Color.Green else Color.Red
                        ),
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        } else {
            Text(
                text = item.title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text = item.description,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 14.sp,
                    color = Color.Gray
                ),
                maxLines = 2,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            if (item.status.isNotEmpty()) {
                Text(
                    text = item.status,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Red
                    ),
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}

data class NotificationItem(
    val title: String,
    val description: String,
    val status: String,
    val imageRes: Int
)

@Preview(showBackground = true)
@Composable
fun NotificationAdminScreenPreview() {
    NotificationAdminScreen(navController = rememberNavController())
}
