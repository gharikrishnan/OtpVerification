package com.angler.otpverification.response


data class Example(
    val data: DataLogin,
    val message: String,
    val success: Boolean
)

data class DataLogin(
    val id: String,
    val mobile: Long,
    val otp: String
)