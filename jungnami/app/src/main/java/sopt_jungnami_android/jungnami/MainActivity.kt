package sopt_jungnami_android.jungnami

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setStatusBarColor()
        setBottomNavigationClickListener()


    }
    private fun setBottomNavigationClickListener(){
        main_act_rank_btn.setOnClickListener {
            toast("1")
        }
        main_act_list_btn.setOnClickListener {
            toast("2")
        }
        main_act_community_btn.setOnClickListener {
            toast("3")
        }
        main_act_content_btn.setOnClickListener {
            toast("4")
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
