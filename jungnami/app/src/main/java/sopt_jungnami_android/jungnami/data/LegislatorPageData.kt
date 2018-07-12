package sopt_jungnami_android.jungnami.data

data class LegislatorPageData (
        val l_id: Int,
        val l_name: String,
        val party_name: String,
        val position: String,
        val profileimg: String,
        val likerank: String,
        val unlikerank: String,
        val contents: ArrayList<Scrap>
)


data class MyCoin (
        val user_coin: Int
)
