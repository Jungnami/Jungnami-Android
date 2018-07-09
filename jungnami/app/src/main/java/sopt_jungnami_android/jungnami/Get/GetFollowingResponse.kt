package sopt_jungnami_android.jungnami.Get

import sopt_jungnami_android.jungnami.data.FollowingData

data class GetFollowingResponse (
        var message : String,
        var data : ArrayList<FollowingData>
)