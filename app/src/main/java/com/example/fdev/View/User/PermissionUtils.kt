package com.example.fdev.View.User

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

// Kiểm tra và yêu cầu quyền truy cập bộ nhớ nếu chưa có
fun checkAndRequestPermissions(context: Context): Boolean {
    val writePermission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    return if (writePermission != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(
            (context as android.app.Activity),
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            1
        )
        false
    } else {
        true
    }
}
