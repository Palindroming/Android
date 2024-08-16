package com.example.band8

data class CommentResponse(
    val result_code: Int,
    val result_data: ResultData
) {
    data class ResultData(
        val message: String
    )
}
