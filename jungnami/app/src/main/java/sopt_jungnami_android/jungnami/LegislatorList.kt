package sopt_jungnami_android.jungnami

import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import kotlinx.android.synthetic.main.activity_legislator_list.*

//Written by SooYoung

class LegislatorList : AppCompatActivity(), View.OnClickListener {

    var isFavorableSelected: Boolean = true

    override fun onClick(v: View?) {
        when(v) {
            legislator_list_act_back_btn -> { // 백버튼
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_legislator_list)
        setStatusBarColor()
        setOnClickListener()
    }

    private fun setOnClickListener(){
        legislator_list_act_likeable_tab_btn.setOnClickListener {
            isFavorableSelected = true
            isSelectedTabView()
        }
        legislator_list_act_unlikeable_tab_btn.setOnClickListener {
            isFavorableSelected = false
            isSelectedTabView()
        }
        legislator_list_act_search.setOnClickListener {
            legislator_list_act_legislator_search_rl.visibility = View.VISIBLE
        }
        legislator_list_act_is_display_blind_panel_rl.setOnClickListener {
            legislator_list_act_legislator_search_rl.visibility = View.GONE
        }
    }

    private fun isSelectedTabView(){
        if (isFavorableSelected){
            legislator_list_act_likeable_title_tv.setTextColor(Color.parseColor("#6b6b6b"))
            legislator_list_act_likable_underbar_line.visibility = View.VISIBLE
            legislator_list_act_unlikeable_title_tv.setTextColor(Color.parseColor("#d8d8d8"))
            legislator_list_act_unlikeable_underbar_line.visibility = View.INVISIBLE
        }
        else {
            legislator_list_act_likeable_title_tv.setTextColor(Color.parseColor("#d8d8d8"))
            legislator_list_act_likable_underbar_line.visibility = View.INVISIBLE
            legislator_list_act_unlikeable_title_tv.setTextColor(Color.parseColor("#6b6b6b"))
            legislator_list_act_unlikeable_underbar_line.visibility = View.VISIBLE
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

    private fun addFragment(){

    }

    private fun replaceFragment() {

    }
}
