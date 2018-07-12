package sopt_jungnami_android.jungnami.data

data class ContentsCardViewData(
        val title: String,
        val thumbnail: String,
        val text: String,
        val subtitle: String,
        val type: Int,
        val imagearray: ArrayList<CardViewImage>,
        val youtube: String,
        val likeCnt: Int,
        val commentCnt: Int
)

data class CardViewImage (
        val imgURL: String
)