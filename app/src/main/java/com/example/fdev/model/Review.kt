package com.example.fdev.model

/**
 * Represents a review made by a user.
 *
 * @param userName Name of the reviewer.
 * @param comment Textual content of the review.
 * @param rating Rating given to the product, on a scale of 1 to 5.
 * @param createdAt Date when the review was posted, typically provided by the server.
 * @param productDetails Details of the product being reviewed (name, price, description).
 */
data class ReviewResponse(
    val userName: String,
    val comment: String,
    val rating: Int,
    val createdAt: String,
    val productDetails: ProductDetails
)

/**
 * Represents details of the product related to a review.
 *
 * @param name Name of the product.
 * @param price Price of the product.
 * @param description Description of the product.
 */
data class ProductDetails(
    val name: String,
    val price: Double,
    val description: String
)

/**
 * Request data structure for submitting a review to the backend.
 *
 * @param productId Unique identifier of the product being reviewed.
 * @param userName Username of the reviewer, typically obtained from authentication context.
 * @param comment Text content of the review.
 * @param rating Rating given by the reviewer, within the range 1 to 5.
 */
data class ReviewRequest(
    val productId: String,
    val userName: String,
    val comment: String,
    val rating: Int
)
