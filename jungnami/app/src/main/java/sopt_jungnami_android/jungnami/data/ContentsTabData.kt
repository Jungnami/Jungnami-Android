package sopt_jungnami_android.jungnami.data

import java.io.Serializable

//
//data class ContentItemData(val title : String,
//                           val image_url : String,
//                           val category : String)
//

data class ContentsDataList (
        val content: ArrayList<Contents>,
        val alarmcnt: Int
)

data class Contents (
        val contentsid: Int,
        val title: String,
        val thumbnail: String? = null,
        val text: String,
        val type: Int
) : Serializable
