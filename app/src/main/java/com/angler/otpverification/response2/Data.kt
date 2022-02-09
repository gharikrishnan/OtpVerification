package com.angler.otpverification.response2

data class Data(
    val _id: String,
    val booking_code: Int,
    val booking_status: Int,
    val created_on: String,
    val customer_id: String,
    val location: Location,
    val modified_on: String,
    val problem_description: String,
    val service_closed_at: String,
    val service_id: String,
    val service_name: String,
    val service_request: List<ServiceRequest>,
    val service_started_at: String,
    val status_updated_on: String
)