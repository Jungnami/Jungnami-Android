package sopt_jungnami_android.jungnami.mypage

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_follower.*
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.data.FollowerData

class FollowerActivity : AppCompatActivity() {
    lateinit var followerItems : ArrayList<FollowerData>
    var context : Context = this

    lateinit var followerAdapter: FollowerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_follower)
        setStatusBarColor()

        followerItems = ArrayList()
        followerItems.add(FollowerData(R.drawable.arealist_legislator_profile_circle, "윤민수", "윤핑퐁"))
        followerItems.add(FollowerData(R.drawable.arealist_legislator_profile_circle, "박지원", "원지박"))
        followerItems.add(FollowerData(R.drawable.arealist_legislator_profile_circle, "윤호황", "윤황호"))
        followerItems.add(FollowerData(R.drawable.arealist_legislator_profile_circle, "김만수", "만수킴"))
        followerItems.add(FollowerData(R.drawable.arealist_legislator_profile_circle, "고길동", "킬동고"))
        followerItems.add(FollowerData(R.drawable.arealist_legislator_profile_circle, "김둘리", "둘김리"))
        followerItems.add(FollowerData(R.drawable.arealist_legislator_profile_circle, "신짱구", "구짱신"))
        followerItems.add(FollowerData(R.drawable.arealist_legislator_profile_circle, "짱구신", "구짱신"))
        followerItems.add(FollowerData(R.drawable.arealist_legislator_profile_circle, "구신짱", "윤핑퐁"))
        followerItems.add(FollowerData(R.drawable.arealist_legislator_profile_circle, "구짱신", "윤핑퐁"))

        followerAdapter = FollowerAdapter(followerItems, context)

        follower_act_top_bar_rv.layoutManager = LinearLayoutManager(this)
        follower_act_top_bar_rv.adapter = followerAdapter



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