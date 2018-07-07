package sopt_jungnami_android.jungnami

import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_alarm.*
import sopt_jungnami_android.jungnami.data.AlarmData

class Alarm : AppCompatActivity(), View.OnClickListener {

    lateinit var alarmItems : ArrayList<AlarmData>
    lateinit var alarmAdapter : AlarmViewAdapter

    override fun onClick(v: View?) {
        when (v) {
            alarm_back_btn -> {
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)
        setStatusBarColor()

        alarmItems = ArrayList()

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
