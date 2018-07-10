package sopt_jungnami_android.jungnami.data

data class FollowingData (
        val followingID: String,
        val followingNickname: String,
        val followingImgURL: String? = null,
        val isMyFollowing: String
)