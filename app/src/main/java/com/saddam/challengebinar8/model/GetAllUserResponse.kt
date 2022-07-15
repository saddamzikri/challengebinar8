package com.saddam.challengebinar8.model

data class GetAllUserResponseItem(
    val alamat: String,
    val email: String,
    val id: String,
    val image: String,
    val name: String,
    val password: String,
    val tanggal_lahir: String,
    val username: String
)