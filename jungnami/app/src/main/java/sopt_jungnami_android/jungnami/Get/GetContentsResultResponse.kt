package sopt_jungnami_android.jungnami.Get

import sopt_jungnami_android.jungnami.data.ContentsSearchData

data class GetContentSearchResponse (
        val data: List<ContentsSearchData>,
        val message: String
)

