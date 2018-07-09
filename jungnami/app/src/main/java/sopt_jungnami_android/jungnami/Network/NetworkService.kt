package sopt_jungnami_android.jungnami.Network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import sopt_jungnami_android.jungnami.Get.GetFollowingResponse

interface NetworkService {

    // 1. 팔로잉 보기
    @Headers("authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ODA3NDY1MjM5LCJpYXQiOjE1MzA3NzU1MDQsImV4cCI6MTUzMzM2NzUwNH0.DAXcgbHm4gOaJMTFyQW0KCvs64lUZai6Cc_pi5pKu4Q")
    @GET("user/followinglist/{f_id}")
    fun getFollowing(
            @Path("f_id") f_id : String
    ) : Call<GetFollowingResponse>

}