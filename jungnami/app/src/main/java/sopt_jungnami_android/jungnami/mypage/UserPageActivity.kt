package sopt_jungnami_android.jungnami.mypage

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
import kotlinx.android.synthetic.main.activity_my_page.*
import kotlinx.android.synthetic.main.activity_user_page.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sopt_jungnami_android.jungnami.Get.GetMyPageResponse
import sopt_jungnami_android.jungnami.MainActivity
import sopt_jungnami_android.jungnami.Network.ApplicationController
import sopt_jungnami_android.jungnami.Network.NetworkService
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.contents.ContentsDetail
import sopt_jungnami_android.jungnami.data.*
import sopt_jungnami_android.jungnami.db.SharedPreferenceController


//made by Yun Hwan
class UserPageActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        if (isSelectScrap){
            val index : Int = userpage_act_recyclerview_list_rv.getChildAdapterPosition(v)
            val contents_id = scrapDataList[index].c_id
            startActivity<ContentsDetail>("contents_id" to contents_id)
        }
    }

    var isSelectScrap : Boolean = true

    lateinit var networkService : NetworkService

    lateinit var myPageDataList: MyPageData
    lateinit var scrapDataList : ArrayList<Scrap>
    lateinit var boardDataList : ArrayList<Board>
    lateinit var userAndMyPageScrapRecyclerViewAdapter: UserAndMyPageScrapRecyclerViewAdapter
    lateinit var userAndMyPageFeedRecyclerViewAdapter : UserAndMyPageFeedRecyclerViewAdapter

    lateinit var mypage_id : String
    lateinit var page_id : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_page)
        setStatusBarColor()
        mypage_id = intent.getStringExtra("mypage_id")
        Log.e("프로필 상세보기 - 유저페이지에서", mypage_id.toString())
        setClickListener()

        requestPageDataToServer()

    }
    private fun setFeedRecyclerViewAdapter(){
        userAndMyPageFeedRecyclerViewAdapter = UserAndMyPageFeedRecyclerViewAdapter(this, dataList = boardDataList)
        userAndMyPageFeedRecyclerViewAdapter.setOnItemClickListener(this)

        userpage_act_recyclerview_list_rv.layoutManager = LinearLayoutManager(this)
        userpage_act_recyclerview_list_rv.adapter = userAndMyPageFeedRecyclerViewAdapter
    }
    private fun setScrapRecyclerViewAdapter(){
        userAndMyPageScrapRecyclerViewAdapter = UserAndMyPageScrapRecyclerViewAdapter(this, dataList = scrapDataList)
        userAndMyPageScrapRecyclerViewAdapter.setOnItemClickListener(this)

        userpage_act_recyclerview_list_rv.layoutManager = GridLayoutManager(this, 2)
        userpage_act_recyclerview_list_rv.adapter = userAndMyPageScrapRecyclerViewAdapter
    }
    private fun requestPageDataToServer(){
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

        scrapDataList = ArrayList()
        boardDataList = ArrayList()

        networkService = ApplicationController.instance.networkService

        val getMyPageResponse = networkService.getMyPageResponse(SharedPreferenceController.getAuthorization(context = applicationContext!!),
                mypage_id.toString())
        getMyPageResponse.enqueue(object : Callback<GetMyPageResponse> {
            override fun onFailure(call: Call<GetMyPageResponse>?, t: Throwable?) {
                Log.e("실패", t.toString())
                window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

            }

            override fun onResponse(call: Call<GetMyPageResponse>?, response: Response<GetMyPageResponse>?) {
                if (response!!.isSuccessful){
                    Log.e("유저페이지 통신!!! ", response!!.body()!!.data.toString())
                    myPageDataList = response!!.body()!!.data
                    page_id = myPageDataList.mypage_id
                    scrapDataList = myPageDataList.scrap
                    boardDataList = myPageDataList.board
                    //나중에 백그라운드로
                    setPageInfoView()
                    setScrapRecyclerViewAdapter()
                }
                window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

            }
        })

    }
    private fun setPageInfoView(){
        val requestOptions = RequestOptions()
        if (myPageDataList.img != "0") {
            requestOptions.centerCrop()
            Glide.with(applicationContext!!)
                    .setDefaultRequestOptions(requestOptions)
                    .load(myPageDataList.img)
                    .into(userpage_act_user_picture)
        }

        userpage_act_user_id_tv.text = myPageDataList.nickname
        userpage_act_scrap_count_tv.text = myPageDataList.scrapcnt.toString()
        userpage_act_myfeed_count_tv.text = myPageDataList.boardcnt.toString()
        userpage_act_following_count_tv.text = myPageDataList.followingcnt.toString()
        userpage_act_follower_count_tv.text = myPageDataList.followercnt.toString()
    }
    private fun setClickListener(){
        userpage_act_top_bar_back_btn.setOnClickListener {
            finish()
        }
        //tab 이동 관련
        userpage_act_scrap_tab_btn.setOnClickListener {
            isSelectScrap = true
            setScrapRecyclerViewAdapter()
            checkSelectedTabView()
        }
        userpage_act_feed_tab_btn.setOnClickListener {
            isSelectScrap = false
            setFeedRecyclerViewAdapter()
            checkSelectedTabView()
        }
        //팔로잉 팔로워
        userpage_act_user_setting_btn.setOnClickListener {
            toast("팔로우 달기")
        }

        userpage_act_following_btn.setOnClickListener {
            startActivity<FollowingActivity>("f_id" to page_id)
        }
        userpage_act_follower_btn.setOnClickListener {
            startActivity<FollowerActivity>("f_id" to page_id)
        }
    }

    private fun checkSelectedTabView(){
        if (isSelectScrap) {
            userpage_act_scrap_tab_btn.setTextColor(Color.parseColor("#36C5F1"))
            userpage_act_feed_tab_btn.setTextColor(Color.parseColor("#D6D6D6"))
        } else {
            userpage_act_scrap_tab_btn.setTextColor(Color.parseColor("#D6D6D6"))
            userpage_act_feed_tab_btn.setTextColor(Color.parseColor("#36C5F1"))
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
}
