package com.example.fdev.model

import com.google.gson.annotations.SerializedName

// Data class cho đối tượng ContactMail
data class ContactMail(
    val name: String,
    val email: String,
    val content: String
)

// Data class cho request body gửi lên server
data class ContactMailRequest(
    val name: String,
    val email: String,
    val content: String
)


data class ContactMailResponse(
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("content") val content: String
)
fun ContactMailResponse.toContactMail(): ContactMail {
    return ContactMail(
        name = this.name,
        email = this.email,
        content = this.content
    )
}

// Form data cho UI để tương tác với người dùng
data class ContactMailFormData(
    var name: String = "",
    var email: String = "",
    var content: String = ""
)

// Chuyển từ FormData thành Request để gửi lên server
fun ContactMailFormData.toContactMailRequest(): ContactMailRequest {
    return ContactMailRequest(
        name = this.name,
        email = this.email,
        content = this.content
    )
}

// Chuyển đổi từ ContactMail sang FormData
fun ContactMail?.toContactMailFormData() = this?.let {
    ContactMailFormData(
        name = this.name,
        email = this.email,
        content = this.content
    )
}
data class ContactResponse(
    val status: String,
    val data: List<ContactMailResponse>
)
