package sopt_jungnami_android.jungnami.Get

import sopt_jungnami_android.jungnami.data.FollowerData


data class GetFollowerResponse (
        val message: String,
        val data: ArrayList<FollowerData>
)
