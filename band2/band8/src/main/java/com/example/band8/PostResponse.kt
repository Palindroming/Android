package com.example.band8

data class PostResponse(
    val result_data: ResultData
) {
    data class ResultData(
        val items: List<PostItem>
    ) {
        data class PostItem(
            val post_key: String
        )
    }
}
