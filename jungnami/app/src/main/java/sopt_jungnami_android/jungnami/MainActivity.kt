package sopt_jungnami_android.jungnami

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.longToast
import sopt_jungnami_android.jungnami.community.CommunityFragment
import sopt_jungnami_android.jungnami.contents.ContentsFragment
import sopt_jungnami_android.jungnami.db.SharedPreferenceController
import sopt_jungnami_android.jungnami.legislator_list.LegislatorListFragment
import sopt_jungnami_android.jungnami.rank.LikeableTab
import sopt_jungnami_android.jungnami.rank.RankFragment
import sopt_jungnami_android.jungnami.rank.UnlikeableTab

class MainActivity : AppCompatActivity() {
    var current_tab_idx: Int = 0
    private val FINISH_INTERVAL_TIME: Long = 2000
    private var backPressedTime: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        Log.e("현재 유저 토큰", SharedPreferenceController.getAuthorization(context = this))

        setStatusBarColor()
        main_act_rank_btn.isSelected = true
        setBottomNavigationClickListener()
        addFragment(RankFragment())
        main_act_likeable_icon_with_animation_iv.visibility = View.INVISIBLE
        main_act_unlikeable_icon_with_animation_iv.visibility = View.INVISIBLE
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

    fun setAnimRankTabIcon(isLikeable: Boolean) {
        val interpolator = MyBounceInterpolator(0.2, 20.0)
        val anim: Animation = AnimationUtils.loadAnimation(this, R.anim.expand_anim)
        anim.interpolator = interpolator
        if (isLikeable) {
            anim.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {
                }

                override fun onAnimationEnd(animation: Animation?) {
                    main_act_likeable_icon_with_animation_iv.visibility = View.GONE
                    val fragment = supportFragmentManager.findFragmentById(R.id.main_act_fragment_fl)
                    (fragment as RankFragment).connectionLikeableTab()
                }

                override fun onAnimationStart(animation: Animation?) {
                    main_act_likeable_icon_with_animation_iv.visibility = View.VISIBLE
                }
            })
            main_act_likeable_icon_with_animation_iv.startAnimation(anim)
        } else {
            anim.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {
                }

                override fun onAnimationEnd(animation: Animation?) {
                    main_act_unlikeable_icon_with_animation_iv.visibility = View.GONE
                    val fragment = supportFragmentManager.findFragmentById(R.id.main_act_fragment_fl)
                    (fragment as RankFragment).connectionUnLikeableTab()
                }

                override fun onAnimationStart(animation: Animation?) {
                    main_act_unlikeable_icon_with_animation_iv.visibility = View.VISIBLE
                }
            })
            main_act_unlikeable_icon_with_animation_iv.startAnimation(anim)
        }
    }

    fun addFragment(fragment: Fragment): Unit {
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



    private fun chagneNonSelectedTabView(idx: Int) {
        when (idx) {
            0 -> main_act_rank_btn.isSelected = false
            1 -> main_act_list_btn.isSelected = false
            2 -> main_act_community_btn.isSelected = false
            3 -> main_act_content_btn.isSelected = false
        }
    }

    private fun chagneSelectedTabView(idx: Int) {
        when (idx) {
            0 -> main_act_rank_btn.isSelected = true
            1 -> main_act_list_btn.isSelected = true
            2 -> main_act_community_btn.isSelected = true
            3 -> main_act_content_btn.isSelected = true
        }
    }

    private fun checkSelectedTabView(selected_idx: Int) {
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

    internal inner class MyBounceInterpolator(amplitude: Double, frequency: Double) : android.view.animation.Interpolator {
        private var mAmplitude = 1.0
        private var mFrequency = 10.0

        init {
            mAmplitude = amplitude
            mFrequency = frequency
        }

        override fun getInterpolation(time: Float): Float {
            return (-1.0 * Math.pow(Math.E, -time / mAmplitude) *
                    Math.cos(mFrequency * time) + 1).toFloat()
        }
    }

    override fun onBackPressed() {
        var tempTime: Long = System.currentTimeMillis()
        var intervalTime: Long = tempTime - backPressedTime
        if (intervalTime in 0..FINISH_INTERVAL_TIME) {
            super.onBackPressed()
        } else {
            backPressedTime = tempTime
            longToast("한번 더 뒤로가기를 누르면 종료됩니다.")
        }
    }
}
