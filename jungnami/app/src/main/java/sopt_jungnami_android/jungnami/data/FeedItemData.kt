package sopt_jungnami_android.jungnami.data

import java.io.Serializable

data class FeedItemData (
        val content: ArrayList<Content>,
        val user_img_url: String,
        val alarmcnt: Int
) : Serializable

data class Content (
        val boardid: Int,
        val user_id : String,
        val nickname: String,
        val userimg: String,
        val img: String,
        val writingtime: String,
        val content: String,
        val islike: Int,
        val likecnt: Int,
        val commentcnt: Int
) : Serializable