package sopt_jungnami_android.jungnami.mypage

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_follower.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sopt_jungnami_android.jungnami.Get.GetFollowerResponse
import sopt_jungnami_android.jungnami.Network.ApplicationController
import sopt_jungnami_android.jungnami.Network.NetworkService
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.data.FollowerData
import sopt_jungnami_android.jungnami.db.SharedPreferenceController

class FollowerActivity : AppCompatActivity() {
    lateinit var followerItems : ArrayList<FollowerData>
    var context : Context = this

    lateinit var networkService: NetworkService
    lateinit var f_id : String
    lateinit var followerAdapter: FollowerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_follower)
        setStatusBarColor()
        Log.v("이곳은", "setStatusBarColor")
        networkService = ApplicationController.instance.networkService

        f_id= intent.getStringExtra("f_id")
        getFollower()

        follower_act_top_bar_close_btn.setOnClickListener {
            finish()
        }
    }

    fun getFollower(){
        val getFollowerResponse = networkService.getFollower(SharedPreferenceController.getAuthorization(context),f_id)
        getFollowerResponse.enqueue(object : Callback<GetFollowerResponse> {
            override fun onFailure(call: Call<GetFollowerResponse>?, t: Throwable?) {
            }

            override fun onResponse(call: Call<GetFollowerResponse>?, response: Response<GetFollowerResponse>?) {
                if(response!!.isSuccessful){
                    followerItems = response!!.body()!!.data
                    followerAdapter = FollowerAdapter(followerItems, context)
                    follower_act_top_bar_rv.layoutManager = LinearLayoutManager(context)
                    follower_act_top_bar_rv.adapter = followerAdapter
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