package com.example.band9;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String ACCESS_TOKEN = "ZQAAAZWzHq9KcSLxOT3AatmkPY7aOIb6eH_pye6bGQzMhFFFg19ie7dENad7EHTcxCJm7thWqoXIYLKNZnMTXCEdv8TTRiKaq2jIGnp-KBKRP-mH";
    private static final String BAND_KEY = "AACsu29d6yYC7v3fNw_ggj_o";
    private static final String BODY = "좋아요";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BandApiService apiService = ApiClient.getClient().create(BandApiService.class);
        fetchPosts(apiService);
    }

    private void fetchPosts(BandApiService apiService) {
        Call<PostResponse> call = apiService.fetchPosts(ACCESS_TOKEN, BAND_KEY);
        call.enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String postKey = response.body().result_data.items.get(0).post_key;
                    postComment(apiService, postKey);
                } else {
                    Log.e("Error", "No posts found");
                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });
    }

    private void postComment(BandApiService apiService, String postKey) {
        Call<CommentResponse> call = apiService.postComment(ACCESS_TOKEN, BAND_KEY, postKey, BODY);
        call.enqueue(new Callback<CommentResponse>() {
            @Override
            public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("Success", "Comment posted successfully");
                } else {
                    Log.e("Error", "Failed to post comment");
                }
            }

            @Override
            public void onFailure(Call<CommentResponse> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });
    }
}
