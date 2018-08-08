package sopt_jungnami_android.jungnami.data

data class MyPageData (
        val mypage_id: String,
        val nickname: String,
        val img: String,
        val scrapcnt: Int,
        val boardcnt: Int,
        val followingcnt: Int,
        val followercnt: Int,
        val coin: Int,
        val votingcnt: Int,
        val pushcnt: Int,
        val scrap: ArrayList<Scrap>,
        val board: ArrayList<Board>
)
//소스의 랭스가 0면 내가 쓴 글
data class Board (
        val b_id: Int,
        val u_id: String,
        val u_nickname: String,
        val u_img: String,
        val source: ArrayList<Source>,
        val b_content: String? = null,
        val b_img: String? = null,
        val b_time: String,
        var islike: Int,
        val like_cnt: Int,
        val comment_cnt: Int
)

data class Source (
        val u_id: String,
        val u_nickname: String,
        val u_img: String,
        val b_content: String,
        val b_img: String,
        val b_time: String
)

data class Scrap (
        val c_id: Int,
        val c_title: String,
        val thumbnail: String,
        val text: String
)