package com.example.bandnotificationlistener;

import android.annotation.SuppressLint;

import android.app.Notification;

import android.service.notification.NotificationListenerService;

import android.service.notification.StatusBarNotification;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;

import java.util.concurrent.Executor;

import java.util.concurrent.Executors;

import okhttp3.Call;

import okhttp3.Callback;

import okhttp3.MediaType;

import okhttp3.OkHttpClient;

import okhttp3.Request;

import okhttp3.RequestBody;

import okhttp3.Response;

public class MyNotificationListenerService extends NotificationListenerService {

    private static final String TAG = "MyNotificationService";

    private static final String TARGET_TEXT = "부산광역시교육청학교행정지원본부";

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    @Override

    public void onNotificationPosted(StatusBarNotification sbn) {

        Notification notification = sbn.getNotification();

        if (notification == null) {

            return;

        }

        CharSequence title = notification.extras.getCharSequence("android.title");

        CharSequence text = notification.extras.getCharSequence("android.text");

        if (title != null && text != null && text.toString().contains(TARGET_TEXT)) {

            Log.d(TAG, "Target notification detected: " + title);

// HTTP 요청을 보낼 Executor 준비

            Executor executor = Executors.newSingleThreadExecutor();

            executor.execute(this::runScript);

        }

    }

    @SuppressLint("LongLogTag")

    private void runScript() {

// axios POST 요청을 OkHttp로 구현

        OkHttpClient client = new OkHttpClient();

        Request postRequest = new Request.Builder()

                .url("https://openapi.band.us/v2/band/post/comment/create?access_token=ZQAAAZWzHq9KcSLxOT3AatmkPY7aOIb6eH_pye6bGQzMhFFFg19ie7dENad7EHTcxCJm7thWqoXIYLKNZnMTXCEdv8TTRiKaq2jIGnp-KBKRP-mH&band_key=AADLoh5xfXA4h7PlycWNmYFT&post_key=AAAZt_bgGQCzKNIRUrtiYMVj&body=좋아요")

                .header("Content-Type", "application/json")

                .post(RequestBody.create("", JSON))

                .build();

        client.newCall(postRequest).enqueue(new Callback() {

            @Override

            public void onFailure(@NonNull Call call, @NonNull IOException e) {

                Log.e(TAG, "HTTP request failed: " + e.getMessage());

            }

            @Override

            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.isSuccessful()) {

                    Log.d(TAG, "HTTP request succeeded: " + response.body().string());

                } else {

                    Log.e(TAG, "HTTP request failed: " + response.message());

                }

            }

        });

    }

}
