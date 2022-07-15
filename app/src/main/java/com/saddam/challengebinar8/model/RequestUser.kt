package com.saddam.challengebinar8.model

import com.google.gson.annotations.SerializedName

data class RequestUser(
    @SerializedName("email")
    val email: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("tanggal_lahir")
    val tanggal_lahir: String,
    @SerializedName("username")
    val username: String
)