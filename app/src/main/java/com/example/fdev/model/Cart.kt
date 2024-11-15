package com.example.fdev.model

import com.google.gson.annotations.SerializedName

// Data class cho request để thêm sản phẩm vào giỏ hàng
data class AddToCartRequest(
    val userName: String,
    val productId: String,
    val quantity: Int
)

// Data class cho phản hồi giỏ hàng
data class CartResponse(
    val status: Int,
    val msg: String,
    val data: Cart
)

// Data class cho phản hồi sản phẩm trong giỏ hàng
data class CartProductResponse(
    @SerializedName("product") val product: Any, // Giữ nguyên kiểu Any để xử lý đa dạng
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("price") val price: Double,
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: String
)

// Data class cho phản hồi người dùng giỏ hàng
data class UserCartResponse(
    @SerializedName("_id") val id: String,
    @SerializedName("userName") val userName: String,
    @SerializedName("products") val products: List<CartProductResponse>
)

// Data class cho giỏ hàng
data class Cart(
    val userName: String,
    val products: List<CartProduct>
)

// Data class cho sản phẩm trong giỏ hàng
data class CartProduct(
    val product: Product,
    val quantity: Int,
    val price: Double,
    val name: String,
    val image: String
)

// Chuyển đổi từ UserCartResponse thành Cart
fun UserCartResponse.toCart(): Cart {
    return Cart(
        userName = this.userName,
        products = this.products.map { it.toCartProduct() }
    )
}

// Chuyển đổi từ CartProductResponse thành CartProduct
fun CartProductResponse.toCartProduct(): CartProduct {
    val productObject = when (product) {
        is ProductResponse -> product.toProduct() // Nếu product là ProductResponse
        is String -> {
            // Xử lý trường hợp nếu product là chuỗi
            Product(id = "", name = product, price = 0.0, description = "", image = "", type = "")
        }
        else -> throw IllegalArgumentException("Unknown product type")
    }

    return CartProduct(
        product = productObject,
        quantity = this.quantity,
        price = this.price,
        name = this.name,
        image = this.image
    )
}
