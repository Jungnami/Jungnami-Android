package sopt_jungnami_android.jungnami.data

import java.io.Serializable

data class RankItemData (val l_id : Int,
                         val l_name : String,
                         val party_name : String,
                         val position : String,
                         val score : Int,
                         val scoretext : String,
                         val profileimg : String?,
                         val mainimg : String?,
                         val ranking : String,
                         val width: Double) : Serializable


//"l_id": 6,
//"l_name": "추미애",
//"party_name": "더불어민주당",
//"position": "당 대표, 서울 광진구 을",
//"score": 4,
//"scoretext": "4 표",
//"profileimg": null,
//"mainimg": null,
//"ranking": "1",
//"width": 1