package sopt_jungnami_android.jungnami

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import sopt_jungnami_android.jungnami.community.CommunityFragment
import sopt_jungnami_android.jungnami.contents.ContentsFragment
import sopt_jungnami_android.jungnami.legislator_list.LegislatorListFragment
import sopt_jungnami_android.jungnami.rank.RankFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setStatusBarColor()
        setBottomNavigationClickListener()
        addFragment(RankFragment())


    }
    private fun setBottomNavigationClickListener(){
        main_act_rank_btn.setOnClickListener {
            replaceFragment(RankFragment())
        }
        main_act_list_btn.setOnClickListener {
            replaceFragment(LegislatorListFragment())
        }
        main_act_community_btn.setOnClickListener {
            replaceFragment(CommunityFragment())
        }
        main_act_content_btn.setOnClickListener {
            replaceFragment(ContentsFragment())
        }
    }
    fun addFragment(fragment: Fragment) : Unit{
        val fm = supportFragmentManager
        val transaction = fm.beginTransaction()
        transaction.add(R.id.main_act_fragment_fl, fragment)
        transaction.commit()
    }

    fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_act_fragment_fl, fragment)
//        transaction.addToBackStack(null) //백키 눌렀을때 순차대로
        transaction.commit()
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
