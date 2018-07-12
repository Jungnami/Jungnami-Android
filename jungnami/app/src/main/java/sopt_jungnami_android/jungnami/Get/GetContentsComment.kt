package sopt_jungnami_android.jungnami.Get

import sopt_jungnami_android.jungnami.data.CommunityCommentData

data class GetContentsCommentResponse (
        val message: String,
        val data: ArrayList<CommunityCommentData>
)