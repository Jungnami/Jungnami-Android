package sopt_jungnami_android.jungnami.Get

import sopt_jungnami_android.jungnami.data.RankItemData


data class GetRankingResponse (
        val message: String,
        val data: ArrayList<RankItemData>
)