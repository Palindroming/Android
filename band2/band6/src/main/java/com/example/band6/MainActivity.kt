import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

fun fetchPostsAndComment() {
    val client = OkHttpClient()

    val accessToken = "ZQAAAZWzHq9KcSLxOT3AatmkPY7aOIb6eH_pye6bGQzMhFFFg19ie7dENad7EHTcxCJm7thWqoXIYLKNZnMTXCEdv8TTRiKaq2jIGnp-KBKRP-mH"
    val host = "https://openapi.band.us"
    val postApi = "/v2/band/posts"

    val url = "$host$postApi"

    val request = Request.Builder()
        .url(url)
        .header("Authorization", "Bearer $accessToken")
        .build()

    client.newCall(request).enqueue(object : okhttp3.Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
        }

        override fun onResponse(call: Call, response: Response) {
            if (response.isSuccessful) {
                val responseData = response.body?.string()
                println("Response: $responseData")
                // JSON 응답을 파싱하여 게시물 목록에서 postKey를 추출합니다.
                val jsonResponse = JSONObject(responseData)
                val postsArray = jsonResponse.optJSONArray("posts") // 응답 JSON에 "posts" 배열이 있다고 가정
                postsArray?.let { array ->
                    if (array.length() > 0) {
                        val post = array.getJSONObject(0) // 첫 번째 게시물 선택
                        val postKey = post.optString("post_key") // 응답 JSON에서 "post_key" 필드를 가져옵니다
                        println("Post Key: $postKey")
                        // "좋아요"라는 댓글을 달기 위해 postComment 함수 호출
                        postComment(postKey, "좋아요")
                    } else {
                        println("No posts found")
                    }
                }
            } else {
                println("Request failed with status code ${response.code}")
            }
        }
    })
}

fun postComment(postKey: String, body: String) {
    val client = OkHttpClient()

    val accessToken = "ZQAAAZWzHq9KcSLxOT3AatmkPY7aOIb6eH_pye6bGQzMhFFFg19ie7dENad7EHTcxCJm7thWqoXIYLKNZnMTXCEdv8TTRiKaq2jIGnp-KBKRP-mH"
    val host = "https://openapi.band.us"
    val commentApi = "/v2/band/post/comment/create"

    val url = "$host$commentApi"

    val jsonBody = """
        {
            "access_token": "$accessToken",
            "band_key": "AACsu29d6yYC7v3fNw_ggj_o",
            "post_key": "$postKey",
            "body": "$body"
        }
    """.trimIndent()

    val requestBody = jsonBody.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

    val request = Request.Builder()
        .url(url)
        .post(requestBody)
        .header("Authorization", "Bearer $accessToken")
        .build()

    client.newCall(request).enqueue(object : okhttp3.Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
        }

        override fun onResponse(call: Call, response: Response) {
            if (response.isSuccessful) {
                val responseData = response.body?.string()
                println("Response: $responseData")
            } else {
                println("Request failed with status code ${response.code}")
            }
        }
    })
}

fun main() {
    fetchPostsAndComment()
}
