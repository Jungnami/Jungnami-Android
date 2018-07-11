package sopt_jungnami_android.jungnami.Network

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import sopt_jungnami_android.jungnami.Get.*
import sopt_jungnami_android.jungnami.Post.PostCommunityPostingResponse
import sopt_jungnami_android.jungnami.Post.PostCompletingVote

interface NetworkService {

    // 1. 팔로잉 보기 made by 형민
    @Headers("authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ODA3NDY1MjM5LCJpYXQiOjE1MzA3NzU1MDQsImV4cCI6MTUzMzM2NzUwNH0.DAXcgbHm4gOaJMTFyQW0KCvs64lUZai6Cc_pi5pKu4Q")
    @GET("user/followinglist/{f_id}")
    fun getFollowing(
            @Path("f_id") f_id : String
    ) : Call<GetFollowingResponse>


//// 2. 커뮤니티 피드 가져오기 by 형민
    @Headers("authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ODA3NDY1MjM5LCJpYXQiOjE1MzA3NzU1MDQsImV4cCI6MTUzMzM2NzUwNH0.DAXcgbHm4gOaJMTFyQW0KCvs64lUZai6Cc_pi5pKu4Q")
    @GET("board/boardlist")
    fun getCommunityFeed(
    ) : Call<GetCommunityFeedResponse>

    @GET("search/legislator/{l_name}")
    fun getRankingSearchLegislator(
            @Path("l_name") l_name : String
    ) : Call<GetRankingSearchLegislatorResponse>



    // 호감/비호감 순위 윤환
    @Headers("authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ODA3NDY1MjM5LCJpYXQiOjE1MzA3NzU1MDQsImV4cCI6MTUzMzM2NzUwNH0.DAXcgbHm4gOaJMTFyQW0KCvs64lUZai6Cc_pi5pKu4Q")
    @GET("ranking/list/{islike}")
    fun getRanking(
            @Path("islike") islike : Int
    ) : Call<GetRankingResponse>

    // rank탭 투표하기 윤환
    @Headers("authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ODA3NDY1MjM5LCJpYXQiOjE1MzA3NzU1MDQsImV4cCI6MTUzMzM2NzUwNH0.DAXcgbHm4gOaJMTFyQW0KCvs64lUZai6Cc_pi5pKu4Q")
    @GET("legislator/voting")
    fun getMyVotingCount() : Call<GetVotingResponse>
    //투표 완료하기 윤환
    @FormUrlEncoded
    @Headers("authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ODA3NDY1MjM5LCJpYXQiOjE1MzA3NzU1MDQsImV4cCI6MTUzMzM2NzUwNH0.DAXcgbHm4gOaJMTFyQW0KCvs64lUZai6Cc_pi5pKu4Q")
    @POST("legislator/voting")
    fun postCompletingVote(
            @Field("l_id") l_id : Int,
            @Field("islike") islike : Int
    ) : Call<PostCompletingVote>

    @Headers("authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ODA3NDY1MjM5LCJpYXQiOjE1MzA3NzU1MDQsImV4cCI6MTUzMzM2NzUwNH0.DAXcgbHm4gOaJMTFyQW0KCvs64lUZai6Cc_pi5pKu4Q")
    @GET("user/mypage/{mypage_id}")
    fun getMyPageResponse(
            @Path("mypage_id") mypage_id : String
    ) : Call<GetMyPageResponse>



    // 커뮤니티 글 작성화면 made by SooYoung
    @Headers("authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ODA3NDY1MjM5LCJpYXQiOjE1MzA3NzU1MDQsImV4cCI6MTUzMzM2NzUwNH0.DAXcgbHm4gOaJMTFyQW0KCvs64lUZai6Cc_pi5pKu4Q")
    @GET("board/post")
    fun getCommunityPostingResponse(
    ) : Call<GetCommunityPostingResponse>
    // 커뮤니티 글 작성 완료 made by SooYoung
    @Headers("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ODA5MjUzMzQ0LCJpYXQiOjE1MzExNTMyMzIsImV4cCI6MTUzMzc0NTIzMn0.5qInI1D3Ikz0AHeMAfH8w-tIwFrvKF4TT0vd47zSiQ4")
    @Multipart
    @POST("board/postcomplete")
    fun postCommunityPostingRequest(
            @Part ("content") posting_content: RequestBody,
            @Part posting_image: MultipartBody.Part?,
            @Part ("shared") posting_shared: RequestBody
    ) : Call<PostCommunityPostingResponse>
}