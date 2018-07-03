package sopt_jungnami_android.jungnami.contents

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_contents_detail.*
import sopt_jungnami_android.jungnami.CommunityWritePage
import sopt_jungnami_android.jungnami.R

class ContentsDetail : AppCompatActivity(), View.OnClickListener {

    override fun onClick(v: View?) {
        when(v) {
            contents_act_detail_back_btn -> {
                finish()
            }
            contents_act_detail_like_btn -> {
                contents_act_detail_like_btn.isSelected = true
                // 좋아요++ 은 나중에
            }
            contents_act_detail_comment_btn -> {
                val intent = Intent(applicationContext, CommunityWritePage::class.java) // contents_comment 댓글 창 .kt 파일명
                startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contents_detail)
        setStatusBarColor()
    }

    private fun setStatusBarColor(){
        val view : View? = window.decorView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (view != null){
                window.statusBarColor = Color.parseColor("#000000")
            }
        }
    }
}
