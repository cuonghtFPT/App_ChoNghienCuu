package com.example.fdev.View.User

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Preview
@Composable
fun PrivacyPolicyScreenPreview() {
    PrivacyPolicyScreen(navController = rememberNavController())
}

@Composable
fun PrivacyPolicyScreen(navController: NavHostController) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Chính sách ",
                fontFamily = FontFamily.Serif,
                fontSize = 15.sp,
                color = Color(0xff909090)
            )
            Text(
                text = "Bảo mật và Pháp lý",
                fontFamily = FontFamily.Serif,
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                fontWeight = FontWeight(700)
            )
        }
        Column {
            Text(
                text = "Chính Sách Bảo Mật\n" +
                        "\n" +
                        "1. Giới thiệu\n" +
                        "\n" +
                        "Chúng tôi cam kết bảo vệ thông tin cá nhân của bạn khi bạn sử dụng ứng dụng bán sản phẩm tranh và ảnh nghệ thuật. Chính sách bảo mật này mô tả cách chúng tôi thu thập, sử dụng, và bảo vệ thông tin của bạn.\n" +
                        "\n" +
                        "2. Thông tin chúng tôi thu thập\n" +
                        "\n" +
                        "Thông tin cá nhân: Khi bạn đăng ký tài khoản, chúng tôi có thể thu thập tên, địa chỉ email, số điện thoại và địa chỉ giao hàng.\n" +
                        "Thông tin thanh toán: Chúng tôi không lưu trữ thông tin thẻ tín dụng của bạn. Tất cả thông tin thanh toán được xử lý qua các cổng thanh toán an toàn.\n" +
                        "Thông tin sử dụng: Chúng tôi thu thập thông tin về cách bạn sử dụng ứng dụng, bao gồm các trang bạn truy cập và thời gian bạn dành cho chúng.\n" +
                        "3. Cách chúng tôi sử dụng thông tin\n" +
                        "\n" +
                        "Để cung cấp và quản lý dịch vụ của chúng tôi.\n" +
                        "Để xử lý đơn hàng và giao hàng.\n" +
                        "Để liên lạc với bạn về đơn hàng, các chương trình khuyến mãi và thông tin cập nhật.\n" +
                        "Để cải thiện trải nghiệm người dùng và phát triển sản phẩm.\n" +
                        "4. Bảo vệ thông tin\n" +
                        "\n" +
                        "Chúng tôi áp dụng các biện pháp bảo mật hợp lý để bảo vệ thông tin cá nhân của bạn khỏi việc truy cập trái phép, sử dụng hoặc tiết lộ. Tuy nhiên, không có phương thức truyền tải qua Internet hoặc phương thức lưu trữ điện tử nào là hoàn toàn an toàn.\n" +
                        "\n" +
                        "5. Chia sẻ thông tin\n" +
                        "\n" +
                        "Chúng tôi không bán hoặc cho thuê thông tin cá nhân của bạn cho bên thứ ba. Chúng tôi chỉ chia sẻ thông tin của bạn với các nhà cung cấp dịch vụ cần thiết để thực hiện các chức năng của ứng dụng, chẳng hạn như xử lý thanh toán và giao hàng.\n" +
                        "\n" +
                        "6. Quyền của bạn\n" +
                        "\n" +
                        "Bạn có quyền yêu cầu truy cập, chỉnh sửa hoặc xóa thông tin cá nhân của mình. Nếu bạn có bất kỳ câu hỏi hoặc yêu cầu nào liên quan đến thông tin cá nhân của mình, vui lòng liên hệ với chúng tôi qua thông tin liên lạc bên dưới.\n" +
                        "\n" +
                        "7. Thay đổi chính sách\n" +
                        "\n" +
                        "Chúng tôi có thể cập nhật chính sách bảo mật này theo thời gian. Mọi thay đổi sẽ được thông báo trên ứng dụng và có hiệu lực ngay khi được công bố.\n" +
                        "\n" +
                        "8. Liên hệ\n" +
                        "\n" +
                        "Nếu bạn có bất kỳ câu hỏi nào về chính sách bảo mật này, vui lòng liên hệ với chúng tôi qua:"
            )
        }
        Spacer(modifier = Modifier.height(100.dp))
    }
}