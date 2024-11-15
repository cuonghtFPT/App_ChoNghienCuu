package com.example.fdev.model

// Data GET Review từ API
data class MyReviewRespone(
    val _id: String,
    val productId: String,
    val userId: String,
    val rate: String,
    val comment: String,
    val time: String?
)