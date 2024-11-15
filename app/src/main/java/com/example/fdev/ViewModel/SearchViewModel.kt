package com.example.fdev.ViewModel

import RetrofitService
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fdev.model.Product
import com.example.fdev.model.ProductResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class SearchViewModel(private val retrofitService: RetrofitService) : ViewModel() {
    private val _searchResults = MutableStateFlow<List<Product>>(emptyList())
    val searchResults: StateFlow<List<Product>> get() = _searchResults

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    fun searchProduct(query: String) {
        if (query.isNotEmpty()) {
            _isLoading.value = true
            viewModelScope.launch {
                val response: Response<List<ProductResponse>> = retrofitService.fdevApiService.searchProduct(query)
                if (response.isSuccessful) {
                    _searchResults.value = (response.body() ?: emptyList()) as List<Product>
                } else {
                    // Xử lý lỗi nếu cần
                    _searchResults.value = emptyList()
                }
                _isLoading.value = false
            }
        } else {
            _searchResults.value = emptyList() // Xóa kết quả khi ô tìm kiếm trống
        }
    }
}
