package sopt_jungnami_android.jungnami.Get

import sopt_jungnami_android.jungnami.data.CommunityCommentData

data class GetCommunityCommentResponse (
        val message: String,
        val data: List<CommunityCommentData>
)