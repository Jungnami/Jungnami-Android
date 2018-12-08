package sopt_jungnami_android.jungnami.Network

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import sopt_jungnami_android.jungnami.Delete.*
import sopt_jungnami_android.jungnami.Get.*
import sopt_jungnami_android.jungnami.Post.*

interface NetworkService {
    //형민이 라인!
    // 1. 팔로잉 보기 made by 형민
    @GET("user/{f_id}/followinglist")
    fun getFollowing(
            @Header("authorization") tokenValue: String?,
            @Path("f_id") f_id: String
    ): Call<GetFollowingResponse>

    //// 2. 커뮤니티 피드 가져오기 by 형민
    @GET("board/{pre}")
    fun getCommunityFeed(
            @Header("authorization") tokenValue: String?,
            @Path("pre") pre: Int
    ): Call<GetCommunityFeedResponse>

    @GET("ranking/all/search/{l_name}")
    fun getRankingSearchLegislator(
            @Path("l_name") l_name: String
    ): Call<GetRankingSearchLegislatorResponse>

    // 커뮤니티 좋아요 by 탁형민
    @POST("board/like")
    fun postCommunityLike(
            @Header("authorization") tokenValue: String?,
            @Body postCommunityLikeRequset: PostCommunityLikeRequset
    ): Call<postCommunityLikeResponse>

    // 정당에서 검색하기 by Tak
    @GET("ranking/search/party/{p_name}/{l_name}")
    fun getPartySearchLegislator(
            @Path("p_name") p_name: String,
            @Path("l_name") l_name: String
    ): Call<GetRankingSearchLegislatorResponse>

    // 지역에서 검색하기 by Tak
    @GET("ranking/search/city/{city}/{l_name}")
    fun getRegionSearchLegislator(
            @Path("city") city: String,
            @Path("l_name") l_name: String
    ): Call<GetRankingSearchLegislatorResponse>

    // 커뮤니티 댓글 가져오기
    @GET("board/comment/{board_id}")
    fun getCommunityComment(
            @Header("authorization") tokenValue: String?,
            @Path("board_id") board_id: Int
    ): Call<GetCommunityCommentResponse>

    // 커뮤니티 댓글쓰기 by 탁형민
    @FormUrlEncoded
    @POST("board/comment")
    fun postCommunityComment(
            @Header("authorization") tokenValue: String?,
            @Field("board_id") board_id: Int,
            @Field("content") content: String
    ): Call<postCommunityLikeResponse>

    // 커뮤니티 댓글 좋아요 by 탁형민
    @FormUrlEncoded
    @POST("board/comment/like")
    fun postCommunityCommentLike(
            @Header("authorization") tokenValue: String?,
            @Field("comment_id") comment_id: Int
    ): Call<postCommunityLikeResponse>

    // 콘텐츠 댓글 가져오기
    @GET("contents/comment/{contents_id}")
    fun getContentsComment(
            @Header("authorization") tokenValue: String?,
            @Path("contents_id") contents_id: Int
    ): Call<GetContentsCommentResponse>

    // 콘텐츠 댓글쓰기 by 탁형민
    //붙여야될듯?
    @FormUrlEncoded
    @POST("contents/comment")
    fun postContentsComment(
            @Header("authorization") tokenValue: String?,
            @Field("contents_id") contents_id: Int,
            @Field("content") content: String
    ): Call<postCommunityLikeResponse>

    // 콘텐츠 댓글 좋아요 by 탁형민
    @FormUrlEncoded
    @POST("contents/comment/like")
    fun postContentsCommentLike(
            @Header("authorization") tokenValue: String?,
            @Field("comment_id") comment_id: Int
    ): Call<postCommunityLikeResponse>

    // 팔로워 보기 made by 형민
    @GET("user/{f_id}/followerlist")
    fun getFollower(
            @Header("authorization") tokenValue: String?,
            @Path("f_id") f_id: String
    ): Call<GetFollowerResponse>

    // 팔로우 요청하기 by 탁형민
    @FormUrlEncoded
    @POST("user/follow")
    fun postFollow(
            @Header("authorization") tokenValue: String?,
            @Field("following_id") following_id: String
    ): Call<PostFollwResponse>

    // 팔로우 취소하기
    @DELETE("user/follow/{f_id}")
    fun deleteFollow(
            @Header("authorization") tokenValue: String?,
            @Path("f_id") f_id: String
    ): Call<DeleteFollowResponse>

    // 콘텐츠 검색 결과 보기 made by 형민
    @GET("contents/search/{keyword}")
    fun getContentsSearchresult(
            @Header("authorization") tokenValue: String?,
            @Path("keyword") keyword: String
    ): Call<GetContentSearchResponse>

    // 커뮤니티 검색
    @GET("board/search/{keyword}")
    fun getCommunitySearchResult(
            @Header("authorization") tokenValue: String?,
            @Path("keyword") keyword: String
    ): Call<GetCommunitySearchResponse>
//형민이 라인 종료!


    //윤환 라인!
    // 호감/비호감 순위 윤환
    @GET("ranking/all/{islike}/{pre}")
    fun getRanking(
            @Header("authorization") tokenValue: String?,
            @Path("islike") islike: Int,
            @Path("pre") pre: Int
    ): Call<GetRankingResponse>

    // rank탭 투표하기 윤환
    @GET("user/vote")
    fun getMyVotingCount(
            @Header("authorization") tokenValue: String?
    ): Call<GetVotingResponse>

    //투표 완료하기 윤환
    @FormUrlEncoded
    @POST("legislator/vote")
    fun postCompletingVote(
            @Header("authorization") tokenValue: String?,
            @Field("l_id") l_id: Int,
            @Field("islike") islike: Int
    ): Call<PostCompletingVote>

    //마이 페이지 윤환
    @GET("user/mypage/{mypage_id}")
    fun getMyPageResponse(
            @Header("authorization") tokenValue: String?,
            @Path("mypage_id") mypage_id: String
    ): Call<GetMyPageResponse>

    //컨텐츠탭 - recommend 추천!
    @GET("contents/{pre}")
    fun getRecommendContentsResponse(
            @Header("authorization") tokenValue: String?,
            @Path("pre") pre: Int
    ): Call<GetRecommendContentsResponse>

    //    컨텐츠텝 TMI STORY
    @GET("contents/category/{category}/{pre}")
    fun getTmiStoryContentsResponse(
            @Header("authorization") tokenValue: String?,
            @Path("category") category: String,
            @Path("pre") pre: Int
    ): Call<GetTmiStoryContentsResponse>

    //로그인 통신
    @FormUrlEncoded
    @POST("user/login/kakao")
    fun postLoginResponse(
            @Header("authorization") tokenValue: String?,
            @Field("accessToken") accessToken: String,
            @Field("fcmToken") fcmToken: String
    ): Call<PostLoginResponse>

    //코인 페이지
    @GET("user/coin")
    fun getCoinInfoResponse(
            @Header("authorization") tokenValue: String?
    ): Call<GetCoinInfoResponse>

    //투표수 페이지
    @GET("user/vote")
    fun getVotePageInfoResponse(
            @Header("authorization") tokenValue: String?
    ): Call<GetVotePageInfoResponse>

    //코인 충전 확인
    @FormUrlEncoded
    @POST("user/addcoin")
    fun postCoinChargeCompletionResponse(
            @Header("authorization") tokenValue: String?,
            @Field("coin") coin: Int
    ): Call<PostCoinChargeCompletionResponse>

    //내투표권 변환 확인
    @FormUrlEncoded
    @POST("user/vote")
    fun postCoinExchangeResponse(
            @Header("authorization") tokenValue: String?,
            @Field("coin") coin: Int
    ): Call<PostCoinExchangeResponse>

    //카드 컨텐츠 내용물
    @GET("contents/{contents_id}/detail")
    fun getDetailedContentsResponse(
            @Header("authorization") tokenValue: String?,
            @Path("contents_id") contents_id: Int
    ): Call<GetDetailedContentsResponse>

    //컨텐츠 스크랩
    @FormUrlEncoded
    @POST("user/scrap")
    fun postContentsScrapAgreeResponse(
            @Header("authorization") tokenValue: String?,
            @Field("contentsid") contentsid: Int
    ): Call<PostContentsScrapAgreeResponse>

    //스크랩 제거
    @DELETE("user/scrap/{contentsid}")
    fun deleteContentsScrapResponse(
            @Header("authorization") tokenValue: String?,
            @Path("contentsid") contentsid: Int
    ): Call<DeleteContentsScrapResponse>

    //컨텐츠 좋아요
    @FormUrlEncoded
    @POST("contents/like")
    fun postContentsLikeResponse(
            @Header("authorization") tokenValue: String?,
            @Field("contents_id") contents_id: Int
    ): Call<PostContentsLikeResponse>

    //좋아요 삭제
    @DELETE("contents/like/{contentsid}")
    fun deleteContentsLikeResponse(
            @Header("authorization") tokenValue: String?,
            @Path("contentsid") contentsid: Int
    ): Call<DeleteContentsLikeResponse>

    //국회의원 페이지 불러오기
    @GET("legislator/page/{l_id}")
    fun getLegislatorResponse(
            @Header("authorization") tokenValue: String?,
            @Path("l_id") l_id: Int
    ): Call<GetLegislatorResponse>

    //후원하기 전 내 코인 받기
    @GET("user/point")
    fun getSponseBeforeDataResponse(
            @Header("authorization") tokenValue: String?
    ): Call<GetSponseBeforeDataResponse>

    //후원하기
    @FormUrlEncoded
    @POST("legislator/support")
    fun postCompleteSponseCoinResponse(
            @Header("authorization") tokenValue: String?,
            @Field("l_id") l_id: Int,
            @Field("coin") coin: Int
    ): Call<PostCompleteSponseCoinResponse>

    //컨텐츠 댓글 좋아요 삭제
    @DELETE("contents/comment/like/{contentscommentid}")
    fun deleteContentsCommendLikeResponse(
            @Header("authorization") tokenValue: String?,
            @Path("contentscommentid") contentscommentid: Int
    ): Call<DeleteContentsLikeResponse>

    //보드 댓글 좋아요 삭제
    @DELETE("board/comment/like/{boardcommentid}")
    fun deleteCummunityCommendLikeResponse(
            @Header("authorization") tokenValue: String?,
            @Path("boardcommentid") boardcommentid: Int
    ): Call<DeleteContentsLikeResponse>

    @Multipart
    @POST("board")
    fun postFeedPostingResponse(
            @Header("authorization") tokenValue: String?,
            @Part("content") posting_content: RequestBody,
            @Part posting_image: MultipartBody.Part?,
            @Part("shared") posting_shared: Int
    ): Call<PostFeedPostingResponse>

    // 커뮤니티 좋아요 삭제
    @DELETE("board/like/{boardid}")
    fun deleteCommunityLikeResponse(
            @Header("authorization") tokenValue: String?,
            @Path("boardid") boardid: Int
    ): Call<DeleteCommunityLikeResponse>

    //커뮤니티 댓글 삭제
    @DELETE("board/comment/{boardcommentid}")
    fun deleteCommunityCommendResponse(
            @Header("authorization") tokenValue: String?,
            @Path("boardcommentid") boardcommentid: Int
    ): Call<DeleteCommunityCommendResponse>

    //컨텐츠 댓글 삭제
    @DELETE("contents/comment/{contentscommentid}")
    fun deleteContentsCommendResponse(
            @Header("authorization") tokenValue: String?,
            @Path("contentscommentid") contentscommentid: Int
    ): Call<DeleteContentsCommendResponse>

    //보드 글 삭제
    @DELETE("board/{boardid}")
    fun deleteBoardResponse(
            @Header("authorization") tokenValue: String?,
            @Path("boardid") boardid: Int
    ): Call<DeleteBoardResponse>
//윤환 라인 종료!


    //수영 라인!
    // 커뮤니티 글 작성화면 made by SooYoung
    @GET("user/img")
    fun getCommunityPostingResponse(
            @Header("authorization") tokenValue: String?
    ): Call<GetCommunityPostingResponse>

    // 커뮤니티 글 작성 완료 made by SooYoung
    @Multipart
    @POST("board")
    fun postCommunityPostingResponse(
            @Header("authorization") tokenValue: String?,
            @Part("content") posting_content: RequestBody,
            @Part image: MultipartBody.Part?,
            @Part("shared") posting_shared: Int
    ): Call<PostCommunityPostingResponse>

    // 정당별 호감/비호감 의원리스트
    @GET("ranking/party/{p_name}/{islike}/{pre}")
    fun getPartyLegislatorListResponse(
            @Header("authorization") tokenValue: String?,
            @Path("islike") islike: Int,
            @Path("p_name") p_name: String,
            @Path("pre") pre: Int
    ): Call<GetPartyDistrictLegislatorListResponse>

    // 지역별 호감/비호감 의원리스트
    @GET("ranking/city/{city}/{islike}/{pre}")
    fun getDistrictLegislatorListResponse(
            @Header("authorization") tokenValue: String?,
            @Path("islike") islike: Int,
            @Path("city") city: String,
            @Path("pre") pre: Int
    ): Call<GetPartyDistrictLegislatorListResponse>

    // 알림 목록
    @GET("user/push")
    fun getAlarmResponse(
            @Header("authorization") tokenValue: String?
    ): Call<GetAlarmResponse>
//수영 라인 종료!

}