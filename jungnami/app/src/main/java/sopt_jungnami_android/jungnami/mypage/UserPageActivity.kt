package sopt_jungnami_android.jungnami.mypage

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_user_page.*
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.data.ContentItemData
import sopt_jungnami_android.jungnami.data.FeedItemData


//made by Yun Hwan
class UserPageActivity : AppCompatActivity() {

    var isSelectScrap : Boolean = true
    lateinit var contentDataList : ArrayList<ContentItemData>
    lateinit var feedDataList : ArrayList<FeedItemData>
    lateinit var userAndMyPageScrapRecyclerViewAdapter: UserAndMyPageScrapRecyclerViewAdapter
    lateinit var userAndMyPageFeedRecyclerViewAdapter : UserAndMyPageFeedRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_page)
        setStatusBarColor()

        setClickListener()
        //스크렙, 피드 리사이클러뷰 세팅
        requestScrapDataToServer()
        requestFeedDataToServer()
        setScrapRecyclerViewAdapter()
    }
    private fun setFeedRecyclerViewAdapter(){
        userAndMyPageFeedRecyclerViewAdapter = UserAndMyPageFeedRecyclerViewAdapter(this, dataList = feedDataList)
        userpage_act_recyclerview_list_rv.layoutManager = LinearLayoutManager(this)
        userpage_act_recyclerview_list_rv.adapter = userAndMyPageFeedRecyclerViewAdapter
    }
    private fun setScrapRecyclerViewAdapter(){
        userAndMyPageScrapRecyclerViewAdapter = UserAndMyPageScrapRecyclerViewAdapter(this, dataList = contentDataList)
        userpage_act_recyclerview_list_rv.layoutManager = GridLayoutManager(this, 2)
        userpage_act_recyclerview_list_rv.adapter = userAndMyPageScrapRecyclerViewAdapter
    }
    private fun requestScrapDataToServer(){
        contentDataList = ArrayList()
        contentDataList.add(ContentItemData("국회의원 아들과 폐지 줍는 부모님???", "image", "스토리"))
        contentDataList.add(ContentItemData("문재인 대통령의\n살아온 일대기와 운명", "image", "스토리"))
        contentDataList.add(ContentItemData("이재명 시장,\n청와대 실세와 오붓한 시간", "image", "TMI"))
        contentDataList.add(ContentItemData("장제원 의원\n아들 인성 논란", "image", "TMI"))
    }
    private fun requestFeedDataToServer(){
        feedDataList = ArrayList()
        feedDataList.add(FeedItemData("문어"))
        feedDataList.add(FeedItemData("오징어"))
        feedDataList.add(FeedItemData("꼴뚜기"))
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
