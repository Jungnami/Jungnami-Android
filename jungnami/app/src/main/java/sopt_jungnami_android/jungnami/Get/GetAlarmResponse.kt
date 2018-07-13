package sopt_jungnami_android.jungnami.Get

import sopt_jungnami_android.jungnami.data.AlarmData

data class GetAlarmResponse (
        val message: String,
        val data: ArrayList<AlarmData>
)