package sopt_jungnami_android.jungnami.data

data class FeedItemData (
        val content: List<Content>,
        val alarmcnt: Int
)

data class Content (
        val boardid: Int,
        val nickname: String,
        val img: String,
        val writingtime: String,
        val content: String,
        val islike: Int,
        val likecnt: Int,
        val commentcnt: Int
)