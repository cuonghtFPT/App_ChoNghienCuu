package com.example.fdev.ViewModel

import RetrofitService
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fdev.model.ContactMailResponse // Đảm bảo đã import kiểu dữ liệu này
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
class NotificationViewModel : ViewModel() {
    private val apiService = RetrofitService().fdevApiService
    private val _notificationItem = MutableStateFlow<List<ContactMailResponse>>(emptyList())
    val NotificationItem: StateFlow<List<ContactMailResponse>> = _notificationItem

    fun getContactList() {
        viewModelScope.launch {
            try {
                val response = apiService.getListContactNotifi() // Gọi API lấy danh sách contact
                if (response.isSuccessful) {
                    response.body()?.let { contactResponse ->
                        _notificationItem.value = contactResponse.data // Lấy danh sách từ data
                    }
                } else {
                    Log.e("NotificationViewModel", "Error getting contact list: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("NotificationViewModel", "API call failed: ${e.message}", e)
            }
        }
    }
}

