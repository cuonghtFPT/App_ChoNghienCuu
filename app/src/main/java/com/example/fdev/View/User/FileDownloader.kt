package com.example.fdev.View.User

import android.content.Context
import android.media.MediaScannerConnection
import android.os.Environment
import android.widget.Toast
import okhttp3.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

fun downloadImage(context: Context, imageUrl: String) {
    val client = OkHttpClient()
    val request = Request.Builder().url(imageUrl).build()

    client.newCall(request).enqueue(object : okhttp3.Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
        }

        override fun onResponse(call: Call, response: Response) {
            if (!response.isSuccessful) return

            val inputStream = response.body?.byteStream()

            // Tạo tên tệp duy nhất dựa trên thời gian hiện tại
            val fileName = "product_image_${System.currentTimeMillis()}.jpg"
            val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), fileName)

            inputStream?.let {
                val outputStream = FileOutputStream(file)
                val buffer = ByteArray(1024)
                var bytesRead: Int
                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                    outputStream.write(buffer, 0, bytesRead)
                }
                outputStream.close()
                inputStream.close()

                // Thông báo MediaStore để cập nhật ảnh vào thư viện
                MediaScannerConnection.scanFile(context, arrayOf(file.toString()), null, null)

                (context as android.app.Activity).runOnUiThread {
                    Toast.makeText(context, "Image downloaded successfully!", Toast.LENGTH_LONG).show()
                }
            }
        }
    })
}
