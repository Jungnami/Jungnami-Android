package sopt_jungnami_android.jungnami.Get

import sopt_jungnami_android.jungnami.data.CommunitySearchData

data class GetCommunitySearchResponse(
        val message: String,
        val data: ArrayList<CommunitySearchData>
)