package sopt_jungnami_android.jungnami

import android.app.ProgressDialog
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.longToast
import org.jetbrains.anko.progressDialog
import sopt_jungnami_android.jungnami.main.MainTabAdapter

class MainActivity : AppCompatActivity() {
    private val FINISH_INTERVAL_TIME: Long = 2000
    private var backPressedTime: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        Log.e("현재 유저 토큰", SharedPreferenceController.getAuthorization(context = this))

        setStatusBarColor()

        //FirebaseConnection().onTokenRefresh()

        configureMainTab()
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
//                    val fragment = supportFragmentManager.findFragmentById(R.id.main_act_fragment_fl)
//                    (fragment as RankFragment).connectionLikeableTab()
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
                    //갱신하려고
//                    val fragment = supportFragmentManager.findFragmentById(R.id.main_act_fragment_fl)
//                    (fragment as RankFragment).connectionUnLikeableTab()
                }

                override fun onAnimationStart(animation: Animation?) {
                    main_act_unlikeable_icon_with_animation_iv.visibility = View.VISIBLE
                }
            })
            main_act_unlikeable_icon_with_animation_iv.startAnimation(anim)
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

    private fun configureMainTab() {
        main_act_fragment_view_pager.adapter = MainTabAdapter(supportFragmentManager)
        main_act_fragment_view_pager.offscreenPageLimit = 4
        main_act_bottom_tab.setupWithViewPager(main_act_fragment_view_pager)

        val bottomNaviTabBar: View = (this.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
                .inflate(R.layout.tablayout_main_act_bottom, null, false)
        val rank = bottomNaviTabBar.findViewById(R.id.main_act_bottom_navi_rank_btn) as RelativeLayout
        val list = bottomNaviTabBar.findViewById<RelativeLayout>(R.id.main_act_bottom_navi_list_btn)
        val community = bottomNaviTabBar.findViewById<RelativeLayout>(R.id.main_act_bottom_navi_community_btn)
        val contents = bottomNaviTabBar.findViewById<RelativeLayout>(R.id.main_act_bottom_navi_content_btn)

        main_act_bottom_tab.getTabAt(0)!!.customView = rank
        main_act_bottom_tab.getTabAt(1)!!.customView = list
        main_act_bottom_tab.getTabAt(2)!!.customView = community
        main_act_bottom_tab.getTabAt(3)!!.customView = contents

    }
}
