package sopt_jungnami_android.jungnami.data

data class FollowingData (
        val following_id: String,
        val following_nickname: String,
        val following_img_url: String? = null,
        val isMyFollowing: String
)