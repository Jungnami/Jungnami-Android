package sopt_jungnami_android.jungnami

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import sopt_jungnami_android.jungnami.community.CommunityFragment
import sopt_jungnami_android.jungnami.contents.ContentsFragment
import sopt_jungnami_android.jungnami.legislator_list.LegislatorListFragment
import sopt_jungnami_android.jungnami.rank.RankFragment

class MainActivity : AppCompatActivity() {
    var current_tab_idx : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setStatusBarColor()
        main_act_rank_btn.isSelected = true
        setBottomNavigationClickListener()
        addFragment(RankFragment())
        //FirebaseConnection().onTokenRefresh()
    }
    private fun setBottomNavigationClickListener() {
//        activity!!.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
//        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        main_act_rank_btn.setOnClickListener {
            checkSelectedTabView(0)
            replaceFragment(RankFragment())
        }
        main_act_list_btn.setOnClickListener {
            checkSelectedTabView(1)
            replaceFragment(LegislatorListFragment())
        }
        main_act_community_btn.setOnClickListener {
            checkSelectedTabView(2)
            replaceFragment(CommunityFragment())
        }
        main_act_content_btn.setOnClickListener {
            checkSelectedTabView(3)
            replaceFragment(ContentsFragment())
        }
    }

    private fun addFragment(fragment: Fragment): Unit {
        val fm = supportFragmentManager
        val transaction = fm.beginTransaction()
        transaction.add(R.id.main_act_fragment_fl, fragment)
        transaction.commit()
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_act_fragment_fl, fragment)
//        transaction.addToBackStack(null) //백키 눌렀을때 순차대로
        transaction.commit()
    }
    private fun chagneNonSelectedTabView(idx : Int){
        when (idx) {
            0 -> main_act_rank_btn.isSelected = false
            1 -> main_act_list_btn.isSelected = false
            2 -> main_act_community_btn.isSelected = false
            3 -> main_act_content_btn.isSelected = false
        }
    }

    private fun chagneSelectedTabView(idx : Int){
        when (idx) {
            0 -> main_act_rank_btn.isSelected = true
            1 -> main_act_list_btn.isSelected = true
            2 -> main_act_community_btn.isSelected = true
            3 -> main_act_content_btn.isSelected = true
        }
    }
    private fun checkSelectedTabView(selected_idx : Int){
        when (selected_idx) {
            0 -> {
                chagneNonSelectedTabView(current_tab_idx)
                current_tab_idx = 0
                chagneSelectedTabView(current_tab_idx)
            }
            1 -> {
                chagneNonSelectedTabView(current_tab_idx)
                current_tab_idx = 1
                chagneSelectedTabView(current_tab_idx)
            }
            2 -> {
                chagneNonSelectedTabView(current_tab_idx)
                current_tab_idx = 2
                chagneSelectedTabView(current_tab_idx)
            }
            3 -> {
                chagneNonSelectedTabView(current_tab_idx)
                current_tab_idx = 3
                chagneSelectedTabView(current_tab_idx)
            }
        }
    }



    private fun setStatusBarColor() {
        val view: View? = window.decorView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (view != null) {
                view.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                window.statusBarColor = Color.parseColor("#FFFFFF")
            }
        }
    }
}
