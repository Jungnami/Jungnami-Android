package sopt_jungnami_android.jungnami.Get

import sopt_jungnami_android.jungnami.data.RankingSearchLegislatorData

data class GetRankingSearchLegislatorResponse (
        val data: List<RankingSearchLegislatorData>,
        val message: String
)