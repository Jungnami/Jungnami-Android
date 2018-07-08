package sopt_jungnami_android.jungnami.mypage

import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_my_page.*
import org.jetbrains.anko.startActivity
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.coinpage.CoinPageActivity

class MyPageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)
        setStatusBarColor()

        setClickListener()

    }

    private fun setClickListener(){
        mypage_act_mycoin_check_btn.setOnClickListener {
            startActivity<CoinPageActivity>("target" to "coin")
        }
        mypage_act_myvote_check_btn.setOnClickListener {
            startActivity<CoinPageActivity>("target" to "vote")
        }
        mypage_act_top_bar_back_btn.setOnClickListener {
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
