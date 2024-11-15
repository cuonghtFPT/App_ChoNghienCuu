package com.example.fdev.model



data class PaymentResponse(
    val status: Int,
    val message: String,
    val data: List<PaymentData>
)

data class PaymentData(
    val id: String,
    val userId: String,
    val cardNumber: String,
    val nameOnCard: String,
    val expiryMonth: Int,
    val expiryYear: Int,
    val isActive: Boolean,
    val type: String,
    val cardType: String,
    val bankName: String,
    val billingAddress: BillingAddress
)
data class PaymentRequest(
    val userId: String,
    val cardNumber: String,
    val nameOnCard: String,
    val expiryMonth: Int,
    val expiryYear: Int,
    val type: String,
    val cardType: String,
    val bankName: String,
    val billingAddress: BillingAddress,
    val image: String?
)

data class BillingAddress(
    val street: String,
    val city: String,
    val postalCode: String,
    val country: String
)