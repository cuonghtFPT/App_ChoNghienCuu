package com.example.fdev.model


import com.google.gson.annotations.SerializedName
// Data class cho request để thêm sản phẩm vào danh sách yêu thích
data class AddToFavouriteRequest(
    val userName: String,
    val productId: String,
    val quantity: Int
)
// Data class cho phản hồi danh sách yêu thích
data class FavouriteResponse(
    val status: Int,
    val msg: String,
    val data: Favourite
)
data class Favourite(
    val userName: String,
    val products: List<FavouriteProduct>
)
// Data class cho phản hồi sản phẩm trong danh sách yêu thích
data class FavouriteProductResponse(
    @SerializedName("product") val product: Any, // Giữ nguyên kiểu Any để xử lý đa dạng
    @SerializedName("name") val name: String,
    @SerializedName("price") val price: Double,
    @SerializedName("image") val image: String
)
// Data class cho phản hồi danh sách yêu thích của người dùng
data class UserFavouriteResponse(
    @SerializedName("_id") val id: String,
    @SerializedName("userName") val userName: String,
    @SerializedName("favourites") val favourites: List<FavouriteProductResponse>
)
// Chuyển đổi từ UserFavouriteResponse thành List<FavouriteProduct>
fun UserFavouriteResponse.toFavouriteList(): List<FavouriteProduct> {
    return this.favourites.map { it.toFavouriteProduct() }
}
// Chuyển đổi từ FavouriteProductResponse thành FavouriteProduct
fun FavouriteProductResponse.toFavouriteProduct(): FavouriteProduct {
    val productObject = when (product) {
        is ProductResponse -> product.toProduct() // Nếu product là ProductResponse
        is String -> {
            // Xử lý trường hợp nếu product là chuỗi
            Product(id = "", name = product, price = 0.0, description = "", image = "", type = "")
        }
        else -> throw IllegalArgumentException("Unknown product type")
    }
    return FavouriteProduct(
        product = productObject,
        name = this.name,
        price = this.price,
        image = this.image
    )
}
// Data class cho sản phẩm yêu thích
data class FavouriteProduct(
    val product: Product,
    val name: String,
    val price: Double,
    val image: String
)
data class AddAllFromFavouriteRequest(
    val userName: String
)

