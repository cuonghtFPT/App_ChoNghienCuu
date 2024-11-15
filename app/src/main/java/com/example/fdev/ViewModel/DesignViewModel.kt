import android.app.Application
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.fdev.model.Design
import com.example.fdev.model.DesignRequest
import com.example.fdev.model.DesignResponse
import com.example.fdev.model.Product
import com.example.fdev.model.ProductAdminResponse
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import kotlin.math.log

class DesignViewModel(application: Application) : AndroidViewModel(application) {
    private val apiService = RetrofitService().fdevApiService
    val designResponse: MutableLiveData<List<DesignResponse>> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()
    val addDesignResponse: MutableLiveData<DesignResponse?> = MutableLiveData()

    val deleteSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val designs: MutableLiveData<List<Design>> = MutableLiveData(listOf())



    // Hàm lấy danh sách các thiết kế
    fun getDesigns() {
        viewModelScope.launch {
            try {
                val response = apiService.getDesigns()
                if (response.isSuccessful) {
                    designResponse.postValue(response.body()?.data)  // Lấy danh sách thiết kế từ trường `data`
                    Log.e("DATA", "getDesigns: "+response.body()?.data )
                } else {
                    errorMessage.postValue("Error: ${response.message()}")
                }
            } catch (e: Exception) {
                errorMessage.postValue("Exception: ${e.message}")
            }
        }
    }

    // Hàm thêm một thiết kế mới
    fun addDesign(designRequest: DesignRequest, context: Context) {
        viewModelScope.launch {
            try {
                val namePart =
                    RequestBody.create("text/plain".toMediaTypeOrNull(), designRequest.name)
                val pricePart = RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),
                    designRequest.price.toString()
                )
                val descriptionPart =
                    RequestBody.create("text/plain".toMediaTypeOrNull(), designRequest.description)
                val typePart = designRequest.type?.let {
                    RequestBody.create(
                        "text/plain".toMediaTypeOrNull(),
                        it
                    )
                }

                // Thay vì chuyển thành File, bạn có thể sử dụng InputStream hoặc Bitmap trực tiếp
                val inputStream =
                    context.contentResolver.openInputStream(Uri.parse(designRequest.imageUri))
                val imageBytes = inputStream?.readBytes()
                val imageRequestBody =
                    RequestBody.create("image/*".toMediaTypeOrNull(), imageBytes ?: ByteArray(0))
                val imagePart =
                    MultipartBody.Part.createFormData("image", "image.jpg", imageRequestBody)

                val response =
                    apiService.addDesign(namePart, pricePart, descriptionPart, imagePart, typePart)
                if (response.isSuccessful) {
                    addDesignResponse.postValue(response.body())
                } else {
                    errorMessage.postValue("Error: ${response.message()}")
                }
            } catch (e: Exception) {
                errorMessage.postValue("Exception: ${e.message}")
            }
        }
    }


    // Hàm cập nhật thiết kế
    fun updateDesign(id: String, designRequest: DesignRequest, context: Context) {
        Log.w("Data: ", designRequest.imageUri)
        viewModelScope.launch {
            try {
                val namePart = RequestBody.create("text/plain".toMediaTypeOrNull(), designRequest.name)
                val pricePart = RequestBody.create("text/plain".toMediaTypeOrNull(), designRequest.price.toString())
                val descriptionPart = RequestBody.create("text/plain".toMediaTypeOrNull(), designRequest.description)
                val typePart = designRequest.type?.let {
                    RequestBody.create("text/plain".toMediaTypeOrNull(), it)
                }

                // Handling image upload if image URI exists
                val imagePart = designRequest.imageUri?.let { uri ->
                    val inputStream = context.contentResolver.openInputStream(Uri.parse(uri))
                    val imageBytes = inputStream?.readBytes()
                    val imageRequestBody = RequestBody.create("image/*".toMediaTypeOrNull(), imageBytes ?: ByteArray(0))
                    MultipartBody.Part.createFormData("image", "image.jpg", imageRequestBody)
                }

                // Send the request to the update API
                val response = apiService.updateDesign(
                    id,
                    namePart,
                    pricePart,
                    descriptionPart,
                    imagePart,
                    typePart
                )

                if (response.isSuccessful) {
                    // After a successful update, fetch the updated designs list
                    getDesigns()
                } else {
                    errorMessage.postValue("Error: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("Error", "Error update: " + e.message.toString())
                errorMessage.postValue("Exception: ${e.message}")
            }
        }
    }


    // Lấy đường dẫn thực của ảnh từ URI
    fun getRealPathFromURI(uri: Uri, context: Context): String? {
        var result: String? = null
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val index = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
                result = if (index != -1) cursor.getString(index) else null
            }
            cursor.close()
        }
        return result
    }

    // Hàm xóa thiết kế
//    fun deleteDesign(id: String) {
//        viewModelScope.launch {
//            try {
//                val response = apiService.deleteDesign(id)
//                if (response.isSuccessful) {
//                    // After successful deletion, fetch the updated list of designs
//                    getDesigns()
//                } else {
//                    errorMessage.postValue("Error: ${response.message()}")
//                }
//            } catch (e: Exception) {
//                errorMessage.postValue("Exception: ${e.message}")
//            }
//        }
//    }

    fun deleteDesign(designId: String) {
        viewModelScope.launch {
            try {
                val response = apiService.deleteDesign(designId)
                if (response.isSuccessful) {
                    deleteSuccess.postValue(true)
                    designs.value = designs.value?.filterNot { it.id == designId }
                } else {
                    errorMessage.postValue("Error: ${response.message()}")
                }
            } catch (e: Exception) {
                errorMessage.postValue("Exception: ${e.localizedMessage}")
            }
        }
    }
}

