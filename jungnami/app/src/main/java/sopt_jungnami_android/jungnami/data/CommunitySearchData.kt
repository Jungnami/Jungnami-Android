package sopt_jungnami_android.jungnami.data

data class CommunitySearchData (
        val id: Int,
        val user_id: String,
        val nickname: String,
        val user_img_url: String,
        val content: String,
        val img_url: String,
        val islike: Int,
        val writingtime: String,
        val likecnt: Int,
        val commentcnt: Int
)