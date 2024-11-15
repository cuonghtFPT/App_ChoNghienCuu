import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import androidx.compose.ui.text.font.FontWeight
import com.example.fdev.model.Product


data class CartItem(
    val product: Product,
    val name: String,
    val price: Number,
    val image: String
)
@Composable
fun CartItemRow(item: CartItem, onRemoveItem: (CartItem) -> Boolean) {
    var showDialog by remember { mutableStateOf(false) }  // Trạng thái để hiển thị Dialog


    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Hiển thị ảnh sản phẩm
        Image(
            painter = rememberImagePainter(data = item.image),
            contentDescription = item.name,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(12.dp)),  // Bo góc ảnh
            contentScale = ContentScale.Crop  // Cắt ảnh theo kích thước
        )


        Spacer(modifier = Modifier.width(16.dp))


        // Hiển thị tên và giá sản phẩm
        Column(
            modifier = Modifier.weight(1f)  // Chiếm không gian còn lại
        ) {
            // Tên sản phẩm
            Text(
                text = item.name,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            // Giá sản phẩm
            Text(
                text = "$${item.price}",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            )
        }


        // Nút xóa sản phẩm
        IconButton(
            onClick = { showDialog = true },  // Hiển thị dialog khi bấm nút xóa
            modifier = Modifier.size(40.dp)
        ) {
            Icon(Icons.Default.Close, contentDescription = "Remove")  // Nút xóa
        }


        // Hiển thị thông báo xác nhận khi showDialog = true
        if (showDialog) {
            AlertDialog(
                onDismissRequest = {
                    showDialog = false  // Ẩn Dialog khi người dùng click ra ngoài
                },
                title = {
                    Text(text = "Xác nhận xóa")
                },
                text = {
                    Text("Bạn có chắc chắn muốn xóa sản phẩm này khỏi giỏ hàng?")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (onRemoveItem(item)) {  // Gọi hàm xóa sản phẩm với item
                                Toast.makeText(context, "Xóa thành công!", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(context, "Xóa thất bại!", Toast.LENGTH_LONG).show()
                            }
                            showDialog = false  // Ẩn Dialog sau khi xóa
                        }
                    ) {
                        Text("Đồng ý")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { showDialog = false }  // Ẩn Dialog nếu người dùng hủy
                    ) {
                        Text("Hủy")
                    }
                }
            )
        }
    }
}

