package com.angler.otpverification.response

data class ResponseData(
    val data: DataOTP,
    val message: String,
    val success: Boolean
)

data class DataOTP(
    val authorization: String,
    val customer_id: String,
    val is_profile_completed: Boolean,
    val mobile: Long,
    val refreshToken: String
)