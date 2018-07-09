package sopt_jungnami_android.jungnami.userpage

import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_user_page.*
import sopt_jungnami_android.jungnami.R

class UserPageActivity : AppCompatActivity() {

    var isSelectScrap : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_page)
        setStatusBarColor()

        setClickListener()

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

    private fun setClickListener(){
        userpage_act_top_bar_back_btn.setOnClickListener {
            finish()
        }
        //tab 이동 관련
        userpage_act_scrap_tab_btn.setOnClickListener {
            isSelectScrap = true
            checkSelectedTabView()
        }
        userpage_act_feed_tab_btn.setOnClickListener {
            isSelectScrap = false
            checkSelectedTabView()
        }
    }

    private fun checkSelectedTabView(){
        if (isSelectScrap) {
            userpage_act_scrap_tab_btn.setTextColor(Color.parseColor("#36C5F1"))
            userpage_act_feed_tab_btn.setTextColor(Color.parseColor("#D6D6D6"))
        } else {
            userpage_act_scrap_tab_btn.setTextColor(Color.parseColor("#D6D6D6"))
            userpage_act_feed_tab_btn.setTextColor(Color.parseColor("#36C5F1"))
        }
    }
}
