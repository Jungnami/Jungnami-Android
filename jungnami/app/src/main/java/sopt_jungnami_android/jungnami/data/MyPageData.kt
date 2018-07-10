package sopt_jungnami_android.jungnami.data

data class MyPageData (
        val mypageID: String,
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
        val bID: Int,
        val uID: String,
        val uNickname: String,
        val uImg: String,
        val source: ArrayList<Source>,
        val bContent: String? = null,
        val bImg: String? = null,
        val bTime: String,
        val likeCnt: Int,
        val commentCnt: Int
)

data class Source (
        val uID: String,
        val uNickname: String,
        val uImg: String,
        val bContent: String,
        val bImg: String,
        val bTime: String
)

data class Scrap (
        val cID: Int,
        val cTitle: String,
        val thumbnail: String,
        val text: String
)