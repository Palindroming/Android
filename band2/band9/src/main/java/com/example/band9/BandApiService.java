package com.example.band9;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BandApiService {
    @GET("v2/band/posts")
    Call<PostResponse> fetchPosts(
            @Query("access_token") String accessToken,
            @Query("band_key") String bandKey
    );

    @POST("v2/band/post/comment/create")
    Call<CommentResponse> postComment(
            @Query("access_token") String accessToken,
            @Query("band_key") String bandKey,
            @Query("post_key") String postKey,
            @Query("body") String body
    );
}