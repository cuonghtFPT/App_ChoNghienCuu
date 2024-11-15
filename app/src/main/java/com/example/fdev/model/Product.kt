package com.example.fdev.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

// Data class cho phản hồi sản phẩm
data class ProductResponse(
    @SerializedName("_id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("price") val price: Number,
    @SerializedName("description") val description: String,
    @SerializedName("image") val image: String,
    @SerializedName("type") val type: String,
)

// Data class cho request body gửi lên server
data class ProductRequest(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("price") val price: Number,
    @SerializedName("description") val description: String,
    @SerializedName("image") val image: String,
    @SerializedName("type") val type: String,
)

// Data class cho đối tượng Product
data class Product(
    val id: String,
    val name: String,
    val price: Number,
    val description: String,
    val image: String,
    val type: String,
) : Serializable

// Chuyển từ ProductResponse sang Product
fun ProductResponse.toProduct(): Product {
    require(id.isNotEmpty()) { "Product ID cannot be empty" }
    return Product(
        id = this.id,
        name = this.name,
        price = this.price,
        description = this.description,
        image = this.image,
        type = this.type,
    )
}

// Form data cho UI để tương tác với người dùng
data class ProductFormData(
    var id: String = "",
    var name: String = "",
    var price: Number = 0,
    var description: String = "",
    var image: String = "",
    var type: String = "",
)

// Chuyển từ FormData thành Request để gửi lên server
fun ProductFormData.toProductRequest(): ProductRequest {
    return ProductRequest(
        id = this.id,
        name = this.name,
        price = this.price,
        description = this.description,
        image = this.image,
        type = this.type
    )
}

// Chuyển đổi từ Product sang FormData
fun Product?.toProductFormData() = this?.let {
    ProductFormData(
        id = this.id,
        name = this.name,
        price = this.price,
        description = this.description,
        image = this.image,
        type = this.type
    )
}

// Data class cho yêu cầu thêm tất cả sản phẩm vào giỏ hàng
data class AddAllToCartRequest(
    val userName: String,
    val products: List<ProductRequest>
) {
    init {
        require(products.isNotEmpty()) { "Product list cannot be empty" }
    }
}
