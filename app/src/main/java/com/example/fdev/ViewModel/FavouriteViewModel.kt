import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fdev.View.User.FavouriteItem
import com.example.fdev.model.AddToFavouriteRequest
import com.example.fdev.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.google.firebase.auth.FirebaseAuth


class FavouriteViewModel : ViewModel() {
    private val apiService = RetrofitService().fdevApiService
    private val _favouriteItems = MutableStateFlow<List<FavouriteItem>>(emptyList())
    val favouriteItems: StateFlow<List<FavouriteItem>> = _favouriteItems


    // Hàm lấy danh sách yêu thích từ API
    fun getFavouriteItems() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val userName = currentUser?.displayName ?: ""  // Lấy tên người dùng từ Firebase


        if (userName.isNotBlank()) {
            viewModelScope.launch {
                try {
                    val response =
                        apiService.getFavourite(userName)  // Thay đổi apiService với hàm lấy yêu thích
                    if (response.isSuccessful) {
                        response.body()?.let { favouriteResponse ->
                            _favouriteItems.value =
                                favouriteResponse.data.products.mapNotNull { favouriteProduct ->
                                    // Giả sử favouriteProduct chứa thông tin đầy đủ về sản phẩm
                                    FavouriteItem(
                                        product = favouriteProduct.product,  // ID sản phẩm
                                        name = favouriteProduct.product.name
                                            ?: "Unknown Product",  // Tên sản phẩm
                                        price = favouriteProduct.product.price
                                            ?: 0.0,  // Giá sản phẩm
                                        image = favouriteProduct.product.image
                                            ?: ""  // Ảnh sản phẩm
                                    )
                                }
                        }
                    } else {
                        Log.e(
                            "FavouriteViewModel",
                            "Error getting favourites: ${response.code()} - ${response.message()}"
                        )
                    }
                } catch (e: Exception) {
                    Log.e("FavouriteViewModel", "API call failed: ${e.message}", e)
                }
            }
        }
    }


    // Thêm sản phẩm vào danh sách yêu thích
    fun addToFavourite(product: Product, quantity: Int = 1) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val userName = currentUser?.displayName ?: ""


        if (userName.isNotBlank()) {
            viewModelScope.launch {
                try {
                    val request = AddToFavouriteRequest(userName, product.id, quantity)
                    val response = apiService.addToFavourite(request)
                    if (response.isSuccessful) {
                        _favouriteItems.value = _favouriteItems.value + FavouriteItem(
                            product = product,
                            name = product.name,
                            price = product.price,
                            image = product.image
                        )
                    } else {
                        Log.e(
                            "FavouriteViewModel",
                            "Error adding to favourites: ${response.code()} - ${response.message()}"
                        )
                    }
                } catch (e: Exception) {
                    Log.e("FavouriteViewModel", "API call failed: ${e.message}", e)
                }
            }
        }
    }


    // Hàm xóa sản phẩm khỏi danh sách yêu thích
    fun removeFromFavourites(productId: String): Boolean {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val userName = currentUser?.displayName ?: ""


        if (userName.isNotBlank()) {
            viewModelScope.launch {
                try {
                    val response = apiService.removeFromFavourite(
                        userName,
                        productId
                    )  // Sử dụng productId thay vì productName
                    if (response.isSuccessful) {
                        _favouriteItems.value =
                            _favouriteItems.value.filter { it.product.id != productId }
                    } else {
                        Log.e(
                            "FavouriteViewModel",
                            "Error removing from favourites: ${response.code()} - ${response.message()}"
                        )
                    }
                } catch (e: Exception) {
                    Log.e("FavouriteViewModel", "API call failed: ${e.message}", e)
                }
            }
        }
        return false
    }
}

