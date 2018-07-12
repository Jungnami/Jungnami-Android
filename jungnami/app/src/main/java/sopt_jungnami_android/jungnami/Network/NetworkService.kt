package sopt_jungnami_android.jungnami.Network

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.*
import sopt_jungnami_android.jungnami.Delete.DeleteContentsLikeResponse
import sopt_jungnami_android.jungnami.Delete.DeleteContentsScrapResponse
import sopt_jungnami_android.jungnami.Get.*
import sopt_jungnami_android.jungnami.Post.*

interface NetworkService {
//형민이 라인!
    // 1. 팔로잉 보기 made by 형민
    @Headers("authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ODA3NDY1MjM5LCJpYXQiOjE1MzA3NzU1MDQsImV4cCI6MTUzMzM2NzUwNH0.DAXcgbHm4gOaJMTFyQW0KCvs64lUZai6Cc_pi5pKu4Q")
    @GET("user/followinglist/{f_id}")
    fun getFollowing(
            @Path("f_id") f_id : String
    ) : Call<GetFollowingResponse>

//// 2. 커뮤니티 피드 가져오기 by 형민
    @GET("board/boardlist")
    fun getCommunityFeed(
        @Header("authorization") tokenValue : String?
    ) : Call<GetCommunityFeedResponse>

    @GET("search/legislator/{l_name}")
    fun getRankingSearchLegislator(
            @Path("l_name") l_name : String
    ) : Call<GetRankingSearchLegislatorResponse>

    // 커뮤니티 좋아요 by 탁형민
    @POST("board/likeboard")
    fun postCommunityLike(
            @Header("authorization") tokenValue : String?,
            @Body postCommunityLikeRequset: PostCommunityLikeRequset
    ) : Call<postCommunityLikeResponse>
    // 정당에서 검색하기 by Tak
    @GET("search/legislatorparty/{p_name}/{l_name}")
    fun getPartySearchLegislator(
            @Path("p_name") p_name : String,
            @Path("l_name") l_name : String
    ) : Call<GetRankingSearchLegislatorResponse>
    // 지역에서 검색하기 by Tak
    @GET("search/legislatorregion/{city}/{l_name}")
    fun getRegionSearchLegislator(
            @Path("city") city : String,
            @Path("l_name") l_name : String
    ) : Call<GetRankingSearchLegislatorResponse>
    // 커뮤니티 댓글 가져오기
    @Headers("authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ODA3NDY1MjM5LCJpYXQiOjE1MzA3NzU1MDQsImV4cCI6MTUzMzM2NzUwNH0.DAXcgbHm4gOaJMTFyQW0KCvs64lUZai6Cc_pi5pKu4Q")
    @GET("board/commentlist/{board_id}")
    fun getCommunityComment(
            @Path("board_id") board_id: Int
    ) : Call<GetCommunityCommentResponse>

    // 커뮤니티 댓글쓰기 by 탁형민
    @Headers("authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ODA3NDY1MjM5LCJpYXQiOjE1MzA3NzU1MDQsImV4cCI6MTUzMzM2NzUwNH0.DAXcgbHm4gOaJMTFyQW0KCvs64lUZai6Cc_pi5pKu4Q")
    @POST("board/makecomment")
    fun postCommunityComment(
            @Body postCommunityCommentRequest: PostCommunityCommentRequest
    ) : Call<postCommunityLikeResponse>

    // 커뮤니티 댓글 좋아요 by 탁형민
    @Headers("authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ODA3NDY1MjM5LCJpYXQiOjE1MzA3NzU1MDQsImV4cCI6MTUzMzM2NzUwNH0.DAXcgbHm4gOaJMTFyQW0KCvs64lUZai6Cc_pi5pKu4Q")
    @POST("board/likecomment")
    fun postCommunityCommentLike(
            @Body postCommunityCommentLikeRequset: PostCommunityCommentLikeRequset
    ) : Call<postCommunityLikeResponse>
    // 콘텐츠 댓글 가져오기
    @Headers("authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ODA3NDY1MjM5LCJpYXQiOjE1MzA3NzU1MDQsImV4cCI6MTUzMzM2NzUwNH0.DAXcgbHm4gOaJMTFyQW0KCvs64lUZai6Cc_pi5pKu4Q")
    @GET("contents/commentlist/{contents_id}")
    fun getContentsComment(
            @Path("contents_id") contents_id: Int
    ) : Call<GetContentsCommentResponse>

    // 콘텐츠 댓글쓰기 by 탁형민
    @Headers("authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ODA3NDY1MjM5LCJpYXQiOjE1MzA3NzU1MDQsImV4cCI6MTUzMzM2NzUwNH0.DAXcgbHm4gOaJMTFyQW0KCvs64lUZai6Cc_pi5pKu4Q")
    @POST("contents/makecomment")
    fun postContentsComment(
            @Body postContentsCommentRequest: PostContentsCommentRequest
    ) : Call<postCommunityLikeResponse>

    // 콘텐츠 댓글 좋아요 by 탁형민
    @Headers("authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ODA3NDY1MjM5LCJpYXQiOjE1MzA3NzU1MDQsImV4cCI6MTUzMzM2NzUwNH0.DAXcgbHm4gOaJMTFyQW0KCvs64lUZai6Cc_pi5pKu4Q")
    @POST("contents/likecomment")
    fun postContentsCommentLike(
            @Body postContentsCommentLikeRequset: PostCommunityCommentLikeRequset
    ) : Call<postCommunityLikeResponse>
//형민이 라인 종료!



//윤환 라인!
    // 호감/비호감 순위 윤환
    @GET("ranking/list/{islike}")
    fun getRanking(
            @Header("authorization") tokenValue : String?,
            @Path("islike") islike : Int
    ) : Call<GetRankingResponse>

    // rank탭 투표하기 윤환
    @GET("legislator/voting")
    fun getMyVotingCount(
            @Header("authorization") tokenValue : String?
    ) : Call<GetVotingResponse>

    //투표 완료하기 윤환
    @FormUrlEncoded
    @POST("legislator/voting")
    fun postCompletingVote(
            @Header("authorization") tokenValue : String?,
            @Field("l_id") l_id : Int,
            @Field("islike") islike : Int
    ) : Call<PostCompletingVote>

    //마이 페이지 윤환
    @GET("user/mypage/{mypage_id}")
    fun getMyPageResponse(
            @Header("authorization") tokenValue : String?,
            @Path("mypage_id") mypage_id : String
    ) : Call<GetMyPageResponse>

    //컨텐츠탭 - recommend 추천!
    @GET("contents/recommend")
    fun getRecommendContentsResponse(
            @Header("authorization") tokenValue : String?
    ) : Call<GetRecommendContentsResponse>

//    컨텐츠텝 TMI STORY
    @GET("contents/main/{category}")
    fun getTmiStoryContentsResponse(
            @Header("authorization") tokenValue : String?,
            @Path("category") category : String
    ) : Call<GetTmiStoryContentsResponse>

    //로그인 통신
    @FormUrlEncoded
    @POST("user/kakaologin")
    fun postLoginResponse(
            @Header("authorization") tokenValue : String?,
            @Field("accessToken") accessToken : String,
            @Field("fcmToken") fcmToken : String
    ) : Call<PostLoginResponse>
    //코인 페이지
    @GET("user/coin")
    fun getCoinInfoResponse(
            @Header("authorization") tokenValue : String?
    ) : Call<GetCoinInfoResponse>
    //투표수 페이지
    @GET("user/vote")
    fun getVotePageInfoResponse(
            @Header("authorization") tokenValue : String?
    ) : Call<GetVotePageInfoResponse>
    //코인 충전 확인
    @FormUrlEncoded
    @POST("user/addcoin")
    fun postCoinChargeCompletionResponse(
            @Header("authorization") tokenValue : String?,
            @Field("coin") coin : Int
    ): Call<PostCoinChargeCompletionResponse>
    //내투표권 변환 확인
    @FormUrlEncoded
    @POST("user/addvote")
    fun postCoinExchangeResponse(
            @Header("authorization") tokenValue : String?,
            @Field("coin") coin : Int
    ): Call<PostCoinExchangeResponse>
    //카드 컨텐츠 내용물
    @GET("contents/cardnews/{contents_id}")
    fun getDetailedContentsResponse(
            @Header("authorization") tokenValue : String?,
            @Path("contents_id") contents_id : Int
    ): Call<GetDetailedContentsResponse>
    //컨텐츠 스크랩
    @FormUrlEncoded
    @POST("contents/scrap")
    fun postContentsScrapAgreeResponse(
            @Header("authorization") tokenValue : String?,
            @Field("contentsid") contentsid : Int
    ) : Call<PostContentsScrapAgreeResponse>
    //스크랩 제거
    @DELETE("delete/scrap/{contentsid}")
    fun deleteContentsScrapResponse(
            @Header("authorization") tokenValue : String?,
            @Path("contentsid") contentsid : Int
    ) : Call<DeleteContentsScrapResponse>
    //컨텐츠 좋아요
    @FormUrlEncoded
    @POST("contents/like")
    fun postContentsLikeResponse(
            @Header("authorization") tokenValue : String?,
            @Field("contents_id") contents_id : Int
    ) : Call<PostContentsLikeResponse>
    //좋아요 삭제
    @DELETE("delete/contentslike/{contentsid}")
    fun deleteContentsLikeResponse(
            @Header("authorization") tokenValue : String?,
            @Path("contentsid") contentsid : Int
    ) : Call<DeleteContentsLikeResponse>
    //국회의원 페이지 불러오기
    @GET("legislator/page/{l_id}")
    fun getLegislatorResponse(
            @Header("authorization") tokenValue : String?,
            @Path("l_id") l_id : Int
    ) : Call<GetLegislatorResponse>
//윤환 라인 종료!




//수영 라인!
    // 커뮤니티 글 작성화면 made by SooYoung
    @Headers("authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ODA3NDY1MjM5LCJpYXQiOjE1MzA3NzU1MDQsImV4cCI6MTUzMzM2NzUwNH0.DAXcgbHm4gOaJMTFyQW0KCvs64lUZai6Cc_pi5pKu4Q")
    @GET("board/post")
    fun getCommunityPostingResponse(
    ) : Call<GetCommunityPostingResponse>

    // 커뮤니티 글 작성 완료 made by SooYoung
    @Multipart
    @Headers("authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ODA3NDY1MjM5LCJpYXQiOjE1MzA3NzU1MDQsImV4cCI6MTUzMzM2NzUwNH0.DAXcgbHm4gOaJMTFyQW0KCvs64lUZai6Cc_pi5pKu4Q")
    @POST("board/postcomplete")
    fun postCommunityPostingResponse(
            @Part ("content") posting_content: String,
            @Part posting_image: MultipartBody.Part?,
            @Part("shared") posting_shared: Int
    ) : Call<PostCommunityPostingResponse>
    //수영 라인!
    // 커뮤니티 글 작성화면 made by SooYoung
    @GET("board/post")
    fun getCommunityPostingResponse(
            @Header("authorization") tokenValue : String?
    ) : Call<GetCommunityPostingResponse>

    // 커뮤니티 글 작성 완료 made by SooYoung
    @Multipart
    @POST("board/postcomplete")
    fun postCommunityPostingResponse(
            @Header("authorization") tokenValue : String?,
            @Part ("content") posting_content: String,
            @Part posting_image: MultipartBody.Part?,
            @Part("shared") posting_shared: Int
    ) : Call<PostCommunityPostingResponse>

    // 정당별 호감/비호감 의원리스트
    @GET("legislatorlist/groupbyparty/{islike}/{p_name}")
    fun getPartyLegislatorListResponse(
            @Header("authorization") tokenValue : String?,
            @Path("islike") islike: Int,
            @Path ("p_name") p_name: String
    ) : Call<GetPartyDistrictLegislatorListResponse>

    // 지역별 호감/비호감 의원리스트
    @GET("legislatorlist/groupbyregion/{islike}/{city}")
    fun getDistrictLegislatorListResponse(
            @Header("authorization") tokenValue : String?,
            @Path("islike") islike: Int,
            @Path("city") city: String
    ) : Call<GetPartyDistrictLegislatorListResponse>
//수영 라인 종료!
//수영 라인 종료!

}