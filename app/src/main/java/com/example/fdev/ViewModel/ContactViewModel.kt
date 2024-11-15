package com.example.fdev.ViewModel

import RetrofitService
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fdev.model.ContactMailRequest
import com.example.fdev.model.ContactMailResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class ContactViewModel : ViewModel() {
    private val apiService = RetrofitService().fdevApiService

    // Hàm để gửi dữ liệu liên hệ
    fun sendContactMail(contactMailRequest: ContactMailRequest, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val response: Response<ContactMailResponse> = apiService.createContact(contactMailRequest)
                if (response.isSuccessful) {
                    onSuccess()  // Gửi thành công
                } else {
                    onFailure("Thất bại: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                onFailure("Lỗi: ${e.message}")
            }
        }
    }
}

