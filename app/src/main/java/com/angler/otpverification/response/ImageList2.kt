package com.angler.otpverification.response

data class ImageList2(
    val data: List<DataList>,
    val message: String,
    val pagination: Pagination,
    val success: Boolean
){


    data class DataList(

        val id: Int,
        val _id: String,
        val brand_id: String,
        val created_on: String,
        val description: String,
        val image_dir: String,
        val image_name: String,
        val modified_on: String,
        val service_id: String,
        val service_name: String
    )


    data class Pagination(
        val hasNextPage: Boolean,
        val hasPrevPage: Boolean,
        val limit: Int,
        val page: Int,
        val pagingCounter: Int,
        val totalDocs: Int,
        val totalPages: Int
    )
}
