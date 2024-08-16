package com.example.band8

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val ACCESS_TOKEN = "ZQAAAZWzHq9KcSLxOT3AatmkPY7aOIb6eH_pye6bGQzMhFFFg19ie7dENad7EHTcxCJm7thWqoXIYLKNZnMTXCEdv8TTRiKaq2jIGnp-KBKRP-mH"
    private val BAND_KEY = "AADLoh5xfXA4h7PlycWNmYFT"
    private val BODY = ".."

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apiService = ApiClient.getClient().create(BandApiService::class.java)
        fetchPosts(apiService)
    }

    private fun fetchPosts(apiService: BandApiService) {
        val call = apiService.fetchPosts(ACCESS_TOKEN, BAND_KEY)
        call.enqueue(object : Callback<PostResponse> {
            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val postKey = response.body()!!.result_data.items[0].post_key
                    postComment(apiService, postKey)
                } else {
                    Log.e("Error", "No posts found")
                }
            }

            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                Log.e("Error", t.message.toString())
            }
        })
    }

    private fun postComment(apiService: BandApiService, postKey: String) {
        val call = apiService.postComment(ACCESS_TOKEN, BAND_KEY, postKey, BODY)
        call.enqueue(object : Callback<CommentResponse> {
            override fun onResponse(call: Call<CommentResponse>, response: Response<CommentResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val resultCode = response.body()!!.result_code
                    val message = response.body()!!.result_data.message
                    if (resultCode == 1) {
                        Log.d("Success", message) // "success" 출력
                    } else {
                        Log.e("Error", "Failed to post comment")
                    }
                } else {
                    Log.e("Error", "Failed to post comment")
                }
            }

            override fun onFailure(call: Call<CommentResponse>, t: Throwable) {
                Log.e("Error", t.message.toString())
            }
        })
    }
}