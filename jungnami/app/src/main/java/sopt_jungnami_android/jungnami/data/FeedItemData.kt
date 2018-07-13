package sopt_jungnami_android.jungnami.data

data class FeedItemData (
        val content: ArrayList<Content>,
        val user_img_url: String,
        val alarmcnt: Int
)

data class Content (
        val boardid: Int,
        val nickname: String,
        val userimg: String,
        val img: String,
        val writingtime: String,
        val content: String,
        val islike: Int,
        val likecnt: Int,
        val commentcnt: Int
)