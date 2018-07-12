package sopt_jungnami_android.jungnami.contents

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_contents_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sopt_jungnami_android.jungnami.CommunityWritePage
import sopt_jungnami_android.jungnami.Get.GetDetailedContentsResponse
import sopt_jungnami_android.jungnami.Network.ApplicationController
import sopt_jungnami_android.jungnami.Network.NetworkService
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.data.ContentsCardViewData
import sopt_jungnami_android.jungnami.db.SharedPreferenceController

class ContentsDetail : AppCompatActivity() {

    lateinit var networkService: NetworkService
    lateinit var contentsDetailViewPagerAdapter: ContentsDetailViewPagerAdapter
    lateinit var cardViewItemData: ContentsCardViewData
    var contents_id: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contents_detail)
        setStatusBarColor()
        setClickListener()

        requestDataToServer()
        setViewPagerChangeListener()

        contents_id = intent.getIntExtra("content_id", 0)

    }


    private fun requestDataToServer() {
        networkService = ApplicationController.instance.networkService
        val getDetailedContentsResponse = networkService.getDetailedContentsResponse(
                SharedPreferenceController.getMyId(context = applicationContext), contents_id)
        getDetailedContentsResponse.enqueue(object : Callback<GetDetailedContentsResponse>{
            override fun onFailure(call: Call<GetDetailedContentsResponse>?, t: Throwable?) {
                Log.e("상세 컨텐츠 보기 데이터 요청 실패", t.toString())
            }
            override fun onResponse(call: Call<GetDetailedContentsResponse>?, response: Response<GetDetailedContentsResponse>?) {
                if (response!!.isSuccessful){
                    cardViewItemData = response.body()!!.data
                    setViewPagerAdapter()
                }
            }
        })

    }

    private fun setViewPagerAdapter() {
        contentsDetailViewPagerAdapter = ContentsDetailViewPagerAdapter(this, cardViewItemData)
        contents_detail_act_viewpager_vp.adapter = contentsDetailViewPagerAdapter

    }

    private fun setClickListener() {
        contents_act_detail_back_btn.setOnClickListener {
            finish()
        }
        contents_act_detail_like_btn.setOnClickListener {
            contents_act_detail_like_btn.isSelected = true
            // 좋아요++ 은 나중에
        }
        contents_act_detail_comment_btn.setOnClickListener {
            val intent = Intent(applicationContext, CommunityWritePage::class.java) // contents_comment 댓글 창 .kt 파일명
            startActivity(intent)
        }
        contents_act_detail_share_btn.setOnClickListener {

        }
        contents_act_detail_scrap_btn.setOnClickListener {
            contents_act_detail_scrap_btn.isSelected = true
        }
    }

    private fun setViewPagerChangeListener() {
        contents_detail_act_viewpager_vp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                contents_act_detail_image_count_tv.text = "${position + 1}/${cardViewItemData.imagearray.size}"
            }
        })
    }

    private fun setStatusBarColor() {
        val view: View? = window.decorView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (view != null) {
                window.statusBarColor = Color.parseColor("#000000")
            }
        }
    }
}
