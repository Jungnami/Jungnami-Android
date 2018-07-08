package sopt_jungnami_android.jungnami.mypage

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_following.*
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.data.FollowingData

class FollowingActivity : AppCompatActivity()  {

    lateinit var followingItems : ArrayList<FollowingData>
    var context : Context = this

    lateinit var followingAdapter: FollowingAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_following)
        setStatusBarColor()

        followingItems = ArrayList()
        followingItems.add(FollowingData(R.drawable.arealist_legislator_profile_circle, "탁형민", "윤핑퐁","팔로잉"))
        followingItems.add(FollowingData(R.drawable.arealist_legislator_profile_circle, "박지원", "원지박","팔로잉"))
        followingItems.add(FollowingData(R.drawable.arealist_legislator_profile_circle, "윤호황", "윤황호","팔로잉"))
        followingItems.add(FollowingData(R.drawable.arealist_legislator_profile_circle, "김만수", "만수킴", "팔로잉"))
        followingItems.add(FollowingData(R.drawable.arealist_legislator_profile_circle, "고길동", "킬동고", "팔로잉"))
        followingItems.add(FollowingData(R.drawable.arealist_legislator_profile_circle, "김둘리", "둘김리", "팔로잉"))
        followingItems.add(FollowingData(R.drawable.arealist_legislator_profile_circle, "신짱구", "구짱신", "팔로잉"))
        followingItems.add(FollowingData(R.drawable.arealist_legislator_profile_circle, "짱구신", "구짱신","팔로잉"))
        followingItems.add(FollowingData(R.drawable.arealist_legislator_profile_circle, "구신짱", "윤핑퐁", "팔로잉"))
        followingItems.add(FollowingData(R.drawable.arealist_legislator_profile_circle, "구짱신", "윤핑퐁", "팔로잉"))

        followingAdapter = FollowingAdapter(followingItems, context)

        following_act_top_bar_rv.layoutManager = LinearLayoutManager(this)
        following_act_top_bar_rv.adapter = followingAdapter


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