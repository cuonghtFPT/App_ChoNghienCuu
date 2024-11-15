package com.example.fdev.components


import com.example.fdev.R
import com.example.fdev.model.Profile
import com.example.fdev.View.MainActivity.Router


val setingItem = listOf(
    Profile("Lịch sử mua hàng", "Xem chi tiết", R.drawable.right_black, Router.HISTORYBUYPRODUCT.name),
    Profile("Cài đặt", "Thông báo, Mật khẩu, FAQ, Liên hệ,...", R.drawable.right_black, Router.SETTING.name),
    Profile("FAQ", "Help Center", R.drawable.right_black, Router.HELP.name),
    Profile("Contact Us", "Đang phát triển", R.drawable.right_black, null.toString()),
    Profile("Privacy & Terms", "Đang phát triển", R.drawable.right_black, null.toString())
)


