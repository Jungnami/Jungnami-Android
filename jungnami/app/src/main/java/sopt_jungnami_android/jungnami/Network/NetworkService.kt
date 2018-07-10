package sopt_jungnami_android.jungnami.Network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import sopt_jungnami_android.jungnami.Get.GetFollowingResponse
import sopt_jungnami_android.jungnami.Get.GetRankingResponse

interface NetworkService {

    // 1. 팔로잉 보기 made by 형민
    @Headers("authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ODA3NDY1MjM5LCJpYXQiOjE1MzA3NzU1MDQsImV4cCI6MTUzMzM2NzUwNH0.DAXcgbHm4gOaJMTFyQW0KCvs64lUZai6Cc_pi5pKu4Q")
    @GET("user/followinglist/{f_id}")
    fun getFollowing(
            @Path("f_id") f_id : String
    ) : Call<GetFollowingResponse>


//// 2. 커뮤니티 피드 가져오기 by 형민
//    @Headers("authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ODA3NDY1MjM5LCJpYXQiOjE1MzA3NzU1MDQsImV4cCI6MTUzMzM2NzUwNH0.DAXcgbHm4gOaJMTFyQW0KCvs64lUZai6Cc_pi5pKu4Q")
//    @GET("board/boardlist")
//    fun getCommunityFeed(
//    ) : Call<GetCommunityFeedResponse>

    // 호감/비호감 순위
    @GET("ranking/list/{islike}")
    fun getRanking(
            @Path("islike") islike : Int
    ) : Call<GetRankingResponse>
}