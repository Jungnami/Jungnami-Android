package sopt_jungnami_android.jungnami

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_community_write_page.*

class CommunityWritePage : AppCompatActivity(), View.OnClickListener {

    override fun onClick(v: View?) {
        when(v) {
            community_act_writepage_back_iv -> {
                finish()
            }
            community_act_writepage_complete_iv -> {
                community_act_writepage_complete_iv.isSelected = false

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_write_page)
    }
}
