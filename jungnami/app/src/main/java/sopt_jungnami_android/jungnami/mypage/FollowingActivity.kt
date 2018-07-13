package sopt_jungnami_android.jungnami.mypage

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_following.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sopt_jungnami_android.jungnami.Get.GetFollowingResponse
import sopt_jungnami_android.jungnami.Network.ApplicationController
import sopt_jungnami_android.jungnami.Network.NetworkService
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.data.FollowingData

class FollowingActivity : AppCompatActivity()  {


    lateinit var networkService: NetworkService
    lateinit var f_id : String
    lateinit var followingItems : ArrayList<FollowingData>
    var context : Context = this
    lateinit var followingAdapter: FollowingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_following)
        setStatusBarColor()
        networkService = ApplicationController.instance.networkService

        f_id= intent.getStringExtra("f_id")
        getFollowing()


//        followingItems = ArrayList()
//        followingAdapter = FollowingAdapter(followingItems, context)
//
//        following_act_top_bar_rv.layoutManager = LinearLayoutManager(this)
//        following_act_top_bar_rv.adapter = followingAdapter


    }

    fun getFollowing(){
//        f_id = 809075065.toString()
        val getFollowingResponse = networkService.getFollowing(f_id!!)
        getFollowingResponse.enqueue(object : Callback<GetFollowingResponse> {
            override fun onFailure(call: Call<GetFollowingResponse>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<GetFollowingResponse>?, response: Response<GetFollowingResponse>?) {
                if(response!!.isSuccessful){
                    followingItems = response!!.body()!!.data
                    followingAdapter = FollowingAdapter(followingItems, context)
//                  followingAdapter.setOnItemClick(this@FollowingActivity)
                    following_act_top_bar_rv.layoutManager = LinearLayoutManager(context)
                    following_act_top_bar_rv.adapter = followingAdapter
                }
            }

        })
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