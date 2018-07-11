package sopt_jungnami_android.jungnami

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_alarm.*
import sopt_jungnami_android.jungnami.Network.NetworkService
import sopt_jungnami_android.jungnami.data.AlarmData

class Alarm : AppCompatActivity(){

    lateinit var networkService: NetworkService

    lateinit var alarmItems : ArrayList<AlarmData>
    lateinit var alarmAdapter : AlarmViewAdapter
    var context : Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)
        setStatusBarColor()
        setClickListener()

        alarmItems = ArrayList()
        alarmItems.add(AlarmData(R.drawable.alarm_none_profile_man_image, R.drawable.alarm_community, "남윤환", "님이 회원님의 게시글을 좋아합니다.", "2시간", "팔로우"))
        alarmItems.add(AlarmData(R.drawable.alarm_none_profile_woman_image, R.drawable.alarm_contents, "탁형민", "님이 댓글에 좋아요를 누르셨습니다.", "2시간", "팔로잉"))
        alarmItems.add(AlarmData(R.drawable.alarm_none_profile_woman_image, R.drawable.alarm_follow, "임수영", "님이 팔로우 했습니다.", "1시간", "팔로잉"))

        alarmAdapter = AlarmViewAdapter(alarmItems)
        rv_alarm.layoutManager = LinearLayoutManager(this)
        rv_alarm.adapter = alarmAdapter
    }

    private fun setClickListener() {
        alarm_back_btn.setOnClickListener {
            finish()
        }
    }

    private fun setStatusBarColor(){
        val view : View? = window.decorView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (view != null){
                view.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                window.statusBarColor = Color.parseColor("#FFFFFF")
            }
        }
    }
}
