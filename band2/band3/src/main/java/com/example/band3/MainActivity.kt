import android.app.Notification
import android.content.Intent
import android.os.Build.HOST
import android.provider.Telephony.TextBasedSmsColumns.BODY
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.eclipsesource
import com.eclipsesource.v8.V8Object
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

class NotificationService : NotificationListenerService() {

    // Band API 인터페이스 정의
    interface BandService {
        @GET("/v2/band/posts")
        fun getPosts(@Query("access_token") accessToken: String, @Query("band_key") bandKey: String): Call<PostResponse>

        @POST("/v2/band/post/comment/create")
        fun postComment(@Query("access_token") accessToken: String, @Query("band_key") bandKey: String, @Query("post_key") postKey: String, @Query("body") body: String): Call<Void>
    }

    // 데이터 클래스
    data class PostResponse(val result_data: ResultData)
    data class ResultData(val items: List<Post>)
    data class Post(val post_key: String)

    // Retrofit 인스턴스 생성
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://openapi.band.us")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val bandService = retrofit.create(BandService::class.java)

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        if (sbn.packageName == "com.nhn.android.band") {
            val notification = sbn.notification
            val extras = notification.extras
            val title = extras.getString(Notification.EXTRA_TITLE)
            val text = extras.getCharSequence(Notification.EXTRA_TEXT)?.toString()

            if (text?.contains("부산광역시교육청행정지원본부") == true) {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        // Band API 호출
                        val response = bandService.getPosts(ACCESS_TOKEN, BAND_KEY).execute()
                        if (response.isSuccessful) {
                            val postKey = response.body()?.result_data?.items?.firstOrNull()?.post_key
                            if (postKey != null) {
                                bandService.postComment(ACCESS_TOKEN, BAND_KEY, postKey, BODY).enqueue(object : Callback<Void> {
                                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                        // 성공 시 처리
                                    }

                                    override fun onFailure(call: Call<Void>, t: Throwable) {
                                        // 실패 시 처리
                                    }
                                })
                            }
                        } else {
                            // 에러 처리
                        }
                    } catch (e: Exception) {
                        // 예외 처리
                    }
                }
            }
        }
    }
}