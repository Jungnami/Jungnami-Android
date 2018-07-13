package sopt_jungnami_android.jungnami

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_alarm.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sopt_jungnami_android.jungnami.Get.GetAlarmResponse
import sopt_jungnami_android.jungnami.Network.ApplicationController
import sopt_jungnami_android.jungnami.Network.NetworkService
import sopt_jungnami_android.jungnami.data.AlarmData
import sopt_jungnami_android.jungnami.db.SharedPreferenceController

class Alarm : AppCompatActivity(){

    lateinit var alarmItems : ArrayList<AlarmData>
    lateinit var alarmAdapter : AlarmViewAdapter

    lateinit var networkService: NetworkService
    var context : Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)
        setStatusBarColor()
        setClickListener()

        networkService = ApplicationController.instance.networkService
        getAlarmResponse()

//        Log.e("내 아이디", SharedPreferenceController.getMyId(this))
//        Log.e("내 토큰", SharedPreferenceController.getAuthorization(this))

//        alarmItems.add(AlarmData("", "810982172","https://myrubysbucket.s3.ap-northeast-2.amazonaws.com/legislator_profile/%EB%B0%94%EB%AF%B8%EB%8B%B9%20%ED%94%84%EB%A1%9C%ED%95%84_%EB%B0%94%EB%AF%B8%EB%8B%B9%20%EC%9D%B4%ED%95%99%EC%9E%AC.png", "남윤환", "님이 회원님의 게시글을 좋아합니다.", "2시간", 0))
//        alarmItems.add(AlarmData("팔로잉", "407144669799202","https://myrubysbucket.s3.ap-northeast-2.amazonaws.com/legislator_profile/%EB%B0%94%EB%AF%B8%EB%8B%B9%20%ED%94%84%EB%A1%9C%ED%95%84_%EB%B0%94%EB%AF%B8%EB%8B%B9%20%EC%9D%B4%ED%95%99%EC%9E%AC.png", "남윤환", "님이 팔로우 했습니다.", "2시간", 1))
//        alarmItems.add(AlarmData("", "810982172","https://myrubysbucket.s3.ap-northeast-2.amazonaws.com/legislator_profile/%EB%B0%94%EB%AF%B8%EB%8B%B9%20%ED%94%84%EB%A1%9C%ED%95%84_%EB%B0%94%EB%AF%B8%EB%8B%B9%20%EC%9D%B4%ED%95%99%EC%9E%AC.png", "남윤환", "님이 회원님의 게시글을 좋아합니다.", "2시간", 1))

    }

    fun getAlarmResponse(){
        val my_id = SharedPreferenceController.getMyId(applicationContext)
        alarmItems = ArrayList()
        val getAlarmResponse = networkService.getAlarmResponse(SharedPreferenceController.getAuthorization(context = applicationContext))
        getAlarmResponse.enqueue(object : Callback<GetAlarmResponse>{
            override fun onFailure(call: Call<GetAlarmResponse>?, t: Throwable?) {
                Log.v("통신 실패", "통신 실패")
            }
            override fun onResponse(call: Call<GetAlarmResponse>?, response: Response<GetAlarmResponse>?) {
                Log.v("통신 접근", "통신 접근")
                alarmItems = response!!.body()!!.data
                if(response!!.isSuccessful){
                    Log.v("통신 성공?", "통신 성공?")
                    alarmAdapter = AlarmViewAdapter(context, alarmItems)
                    rv_alarm.layoutManager = LinearLayoutManager(context)
                    rv_alarm.adapter = alarmAdapter
                }
            }
        })
    }

    private fun setClickListener(){
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
