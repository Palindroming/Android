package com.example.band8

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface BandApiService {
    @GET("v2/band/posts")
    fun fetchPosts(
        @Query("access_token") accessToken: String,
        @Query("band_key") bandKey: String
    ): Call<PostResponse>

    @POST("v2/band/post/comment/create")
    fun postComment(
        @Query("access_token") accessToken: String,
        @Query("band_key") bandKey: String,
        @Query("post_key") postKey: String,
        @Query("body") body: String
    ): Call<CommentResponse>
}
