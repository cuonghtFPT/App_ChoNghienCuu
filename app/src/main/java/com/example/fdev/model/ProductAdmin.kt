package com.example.fdev.model

data class ProductAdminRequest (
    val name: String,
    val price: Number,
    val description: String,
    val image: String,
    val type: String
)

data class ProductAdminResponse (
    val id: String,
    val name: String,
    val price: Number,
    val description: String,
    val image: String,
    val type: String
)