package sopt_jungnami_android.jungnami.mypage

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_legislator_list.*
import kotlinx.android.synthetic.main.activity_my_page.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sopt_jungnami_android.jungnami.Alarm
import sopt_jungnami_android.jungnami.Get.GetMyPageResponse
import sopt_jungnami_android.jungnami.MainActivity
import sopt_jungnami_android.jungnami.Network.ApplicationController
import sopt_jungnami_android.jungnami.Network.NetworkService
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.Terms2Activity
import sopt_jungnami_android.jungnami.coinpage.CoinPageActivity
import sopt_jungnami_android.jungnami.contents.ContentsDetail
import sopt_jungnami_android.jungnami.data.*
import sopt_jungnami_android.jungnami.db.SharedPreferenceController

class MyPageActivity : AppCompatActivity(), View.OnClickListener {
    private val REQUEST_CODE_CONTENTS_DETAILED = 1004
    private val REQUEST_CODE_COIN_CHARGE = 7547
    private val REQUEST_CODE_FEED =1002
    override fun onClick(v: View?) {
        if (isSelectScrap){
            val index : Int = mypage_act_recyclerview_list_rv.getChildAdapterPosition(v)
            val contents_id = scrapDataList[index].c_id
            startActivityForResult<ContentsDetail>(REQUEST_CODE_CONTENTS_DETAILED
                    , "contents_id" to contents_id, "isUserMyPage" to true)
        }
    }

    var isSelectScrap : Boolean = true
    lateinit var page_id : String

    lateinit var userAndMyPageScrapRecyclerViewAdapter: UserAndMyPageScrapRecyclerViewAdapter
    lateinit var userAndMyPageFeedRecyclerViewAdapter : UserAndMyPageFeedRecyclerViewAdapter
    lateinit var scrapDataList : ArrayList<Scrap>
    lateinit var boardDataList : ArrayList<Board>

    lateinit var myPageDataList: MyPageData
    lateinit var networkService : NetworkService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)
        setStatusBarColor()

        setClickListener()
        requestMyPageDataToServer("all")

        Log.e("내 아이디", SharedPreferenceController.getMyId(this))
        Log.e("내 토큰", SharedPreferenceController.getAuthorization(this))
    }


    private fun requestMyPageDataToServer(target: String){

        scrapDataList = ArrayList()
        boardDataList = ArrayList()
        val my_id = SharedPreferenceController.getMyId(applicationContext)
        networkService = ApplicationController.instance.networkService

        val getMyPageResponse = networkService.getMyPageResponse(SharedPreferenceController.getAuthorization(context = applicationContext),my_id)
        getMyPageResponse.enqueue(object : Callback<GetMyPageResponse>{
            override fun onFailure(call: Call<GetMyPageResponse>?, t: Throwable?) {
                Log.e("실패", t.toString())

            }

            override fun onResponse(call: Call<GetMyPageResponse>?, response: Response<GetMyPageResponse>?) {
                if (response!!.isSuccessful){
                    myPageDataList = response!!.body()!!.data

                    scrapDataList = myPageDataList.scrap
                    boardDataList = myPageDataList.board

                    page_id = myPageDataList.mypage_id
                    //나중에 백그라운드로
                    if (target == "all"){
                        setMyInfoView()
                        setScrapRecyclerViewAdapter()
                    } else if (target == "info"){
                        setMyInfoView()
                    } else if (target == "feed"){
                        setFeedRecyclerViewAdapter()
                    }
                }
            }
        })
    }

    private fun setMyInfoView(){
        val requestOptions = RequestOptions()
        if (myPageDataList.img != "0") {
            requestOptions.centerCrop()
            Glide.with(applicationContext!!)
                    .setDefaultRequestOptions(requestOptions)
                    .load(myPageDataList.img)
                    .into(mypage_act_my_picture_iv)
        }

        mypage_act_user_name_tv.text = myPageDataList.nickname
        mypage_act_scrap_count_tv.text = myPageDataList.scrapcnt.toString()
        mypage_act_myfeed_count_tv.text = myPageDataList.boardcnt.toString()
        mypage_act_following_count_tv.text = myPageDataList.followingcnt.toString()
        mypage_act_follower_count_tv.text = myPageDataList.followercnt.toString()

        mypage_act_mycoin_count_tv.text = "${myPageDataList.coin} 코인"
        mypage_act_votingcnt_tv.text = "${myPageDataList.votingcnt}개"

        if (myPageDataList.pushcnt > 0){
            mypage_act_bell_img.visibility = View.VISIBLE
            mypage_act_top_bar_new_post_counter_tv.text = myPageDataList.pushcnt.toString()
        }
    }

    private fun setFeedRecyclerViewAdapter(){
        userAndMyPageFeedRecyclerViewAdapter = UserAndMyPageFeedRecyclerViewAdapter(this, dataList = boardDataList)
        userAndMyPageFeedRecyclerViewAdapter.setOnItemClickListener(this)
        mypage_act_recyclerview_list_rv.layoutManager = LinearLayoutManager(applicationContext)
        mypage_act_recyclerview_list_rv.adapter = userAndMyPageFeedRecyclerViewAdapter
    }
    private fun setScrapRecyclerViewAdapter(){
        userAndMyPageScrapRecyclerViewAdapter = UserAndMyPageScrapRecyclerViewAdapter(this, dataList = scrapDataList)
        userAndMyPageScrapRecyclerViewAdapter.setOnItemClickListener(this)
        mypage_act_recyclerview_list_rv.layoutManager = GridLayoutManager(this, 2)
        mypage_act_recyclerview_list_rv.adapter = userAndMyPageScrapRecyclerViewAdapter
    }

    private fun setClickListener(){
        mypage_act_mycoin_check_btn.setOnClickListener {
            startActivityForResult<CoinPageActivity>(REQUEST_CODE_COIN_CHARGE, "target" to "coin")
        }
        mypage_act_myvote_check_btn.setOnClickListener {
            startActivityForResult<CoinPageActivity>(REQUEST_CODE_COIN_CHARGE, "target" to "vote")
        }
        mypage_act_top_bar_back_btn.setOnClickListener {
            finish()
        }
        mypage_act_top_bar_bell_btn.setOnClickListener {
            startActivity<Alarm>()
        }
        mypage_act_private_btn.setOnClickListener {
            startActivity<Terms2Activity>()
        }
        // tab 이동 관련
        mypage_act_scrap_tab_btn.setOnClickListener {
            isSelectScrap = true
            setScrapRecyclerViewAdapter()
            checkSelectedTabView()
        }
        mypage_act_feed_tab_btn.setOnClickListener {
            isSelectScrap = false
            setFeedRecyclerViewAdapter()
            checkSelectedTabView()
        }
        //팔로잉 팔로워
        mypage_act_following_btn.setOnClickListener {
            startActivity<FollowingActivity>("f_id" to page_id)
        }
        mypage_act_follower_btn.setOnClickListener {
            startActivity<FollowerActivity>("f_id" to page_id)
        }

    }
    private fun checkSelectedTabView(){
        if (isSelectScrap) {
            mypage_act_scrap_tab_btn.setTextColor(Color.parseColor("#36C5F1"))
            mypage_act_feed_tab_btn.setTextColor(Color.parseColor("#D6D6D6"))
        } else {
            mypage_act_scrap_tab_btn.setTextColor(Color.parseColor("#D6D6D6"))
            mypage_act_feed_tab_btn.setTextColor(Color.parseColor("#36C5F1"))
        }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_CONTENTS_DETAILED){
            if (resultCode == Activity.RESULT_OK){
                val temp = data!!.getBooleanExtra("isChangeScapState", false)
                if (temp) {
                    requestMyPageDataToServer("all")
                }
            }

        }
        if (requestCode == REQUEST_CODE_COIN_CHARGE){
            if (resultCode == Activity.RESULT_OK){
                val temp = data!!.getBooleanExtra("isStateChange", false)
                if(temp){
                    requestMyPageDataToServer("all")
                }
            }
        }
        if (requestCode == REQUEST_CODE_FEED){
            if (resultCode == Activity.RESULT_OK){
                val temp = data!!.getBooleanExtra("isStateChange", false)
                Log.e("마이페이지", "$temp 값이 무엇인고")
                if(temp){
                    requestMyPageDataToServer("feed")
                }
            }
        }
    }
}
