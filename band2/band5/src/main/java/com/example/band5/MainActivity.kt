package com.example.band5

import android.os.Bundle
import android.security.identity.ResultData
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.Callback
import retrofit2.Response

interface BandApiService{
    @GET("/v2/band/posts")
    fun fetchPosts(
        @Query("access_token") accessToken: String,
        @Query("band_key") bandKey: String
    ): Call<PostsResponse>

    @POST("/v2/band/post/comment/create")
    fun postComment(
        @Query("access_token") accessToken: String,
        @Query("band_key") bandKey: String,
        @Query("post_key") postKey: String,
        @Query("body") body: String
    ): Call<CommentResponse>
}

object RetrofitClient {
    private const val BASE_URL = "https://openapi.band.us"

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService : BandApiService = retrofit.create(BandApiService::class.java)
}


data class PostsResponse(
    val result_data: ResultData?
)

data class ResultData(
    val posts: List<PostItem>?
)

data class PostItem(
    val post_key: String
)

data class CommentResponse (

    val result_code: String,
    val result_message : String
)
class MainActivity : AppCompatActivity() {

    private val accessToken = "ZQAAAZWzHq9KcSLxOT3AatmkPY7aOIb6eH_pye6bGQzMhFFFg19ie7dENad7EHTcxCJm7thWqoXIYLKNZnMTXCEdv8TTRiKaq2jIGnp-KBKRP-mH"
    private val bandKey = "AACsu29d6yYC7v3fNw_ggj_o"
    private val body = "좋아요"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fetchPosts()
    }

    private fun fetchPosts() {
        RetrofitClient.apiService.fetchPosts(accessToken, bandKey)
            .enqueue(object : Callback<PostsResponse> {
                override fun onResponse(call: Call<PostsResponse>, response: Response<PostsResponse>) {
                    if (response.isSuccessful) {
                        // 응답 본문을 로깅하여 확인
                        val responseBody = response.body()
                        Log.d("MainActivity", "Response body: ${responseBody.toString()}")

                        val posts = responseBody?.result_data?.items
                        if (!posts.isNullOrEmpty()) {
                            val postKey = posts[0].post_key
                            Log.d("MainActivity", "First post key: $postKey")
                            postComment(postKey)
                        } else {
                            Log.d("MainActivity", "No posts found")
                        }
                    } else {
                        Log.e("MainActivity", "Error response: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<PostsResponse>, t: Throwable) {
                    Log.e("MainActivity", "Error request: ${t.message}")
                }
            })
    }

    private fun postComment(postKey: String) {
        RetrofitClient.apiService.postComment(accessToken, bandKey, postKey, body)
            .enqueue(object : Callback<CommentResponse> {
                override fun onResponse(call: Call<CommentResponse>, response: Response<CommentResponse>) {
                    if (response.isSuccessful) {
                        // 응답 본문을 로깅하여 확인
                        val commentResponse = response.body()
                        Log.d("MainActivity", "Comment posted: ${commentResponse?.result_message}")
                    } else {
                        Log.e("MainActivity", "Error response: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<CommentResponse>, t: Throwable) {
                    Log.e("MainActivity", "Error posting comment: ${t.message}")
                }
            })
    } }