package com.example.baker_street.models

import com.google.gson.annotations.SerializedName

data class UserModel(

    @SerializedName("username")
    val name: String? = null,

    @SerializedName("email")
    val email: String? = null,

    @SerializedName("admno")
    val admno: String? = null,

    @SerializedName("password")
    val password: String? = null,

    @SerializedName("token")
    val jwtToken: String? = null,

    @SerializedName("message")
    val message: String? = null,

    @SerializedName("role")
    val role: String? = null,
)

