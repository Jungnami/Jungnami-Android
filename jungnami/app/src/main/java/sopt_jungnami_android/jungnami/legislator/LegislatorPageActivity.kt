package sopt_jungnami_android.jungnami.legislator

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_legislator_page.*
import kotlinx.android.synthetic.main.activity_user_page.*
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sopt_jungnami_android.jungnami.Get.GetLegislatorResponse
import sopt_jungnami_android.jungnami.Network.ApplicationController
import sopt_jungnami_android.jungnami.Network.NetworkService
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.contents.ContentsDetail
import sopt_jungnami_android.jungnami.data.Content
import sopt_jungnami_android.jungnami.data.LegislatorPageData
import sopt_jungnami_android.jungnami.data.Scrap
import sopt_jungnami_android.jungnami.db.SharedPreferenceController

class LegislatorPageActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        val index: Int = legislator_frag_recyclerview_rv.getChildAdapterPosition(v)
        val contents_id = contentsDataList[index].c_id
        startActivity<ContentsDetail>("contents_id" to contents_id)
    }

    lateinit var legislatorRecyclerViewAdapter: LegislatorRecyclerViewAdapter
    lateinit var networkService: NetworkService
    var l_id: Int = 0
    lateinit var legislatorData: LegislatorPageData
    lateinit var contentsDataList : ArrayList<Scrap>

//    lateinit var legislator_image : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_legislator_page)
        l_id = intent.getIntExtra("l_id", 0)
        setStatusBarColor()
        setClickListener()



        requestLegislatorPageDataToServer()

    }
    private fun setLikeableTop3Image(lanking : String){
        when (lanking) {
            "1" -> legislator_page_act_like_top3_iv.setImageResource(R.drawable.main_gold_medal_icon)
            "2" -> legislator_page_act_like_top3_iv.setImageResource(R.drawable.main_silver_medal_icon)
            "3" -> legislator_page_act_like_top3_iv.setImageResource(R.drawable.main_bronze_medal_icon)
            else -> legislator_page_act_like_top3_iv.visibility = View.GONE
        }
    }
    private fun setUnlikeableTop3Image(lanking : String){
        when (lanking) {
            "1" -> legislator_page_act_unlike_top3_iv.setImageResource(R.drawable.main_bomb_red)
            "2" -> legislator_page_act_unlike_top3_iv.setImageResource(R.drawable.main_bomb_orange)
            "3" -> legislator_page_act_unlike_top3_iv.setImageResource(R.drawable.main_bomb_yellow)
            else -> legislator_page_act_unlike_top3_iv.visibility = View.GONE
        }
    }

    private fun setView() {
        val requestOptions = RequestOptions()
        requestOptions.placeholder(R.drawable.legislator_noneprofile_woman_image)
        Glide.with(applicationContext!!)
                .setDefaultRequestOptions(requestOptions)
                .load(legislatorData.profileimg)
                .into(legislator_page_act_profile_img_iv)
        setCircleBackgoundImage(legislatorData.party_name)
        legislator_frag_name_tv.text = legislatorData.l_name
        setLikeableTop3Image(legislatorData.likerank)
        setUnlikeableTop3Image(legislatorData.unlikerank)
        legislator_frag_likeable_ranking_tv.text = "호감 ${legislatorData.likerank}위 "
        legislator_frag_unlikeable_ranking_tv.text = "비호감 ${legislatorData.unlikerank}위 "
        legislator_frag_party_name_tv.text = legislatorData.party_name
        legislator_frag_position_tv.text = legislatorData.position
    }
    private fun setCircleBackgoundImage(partyName : String){
        when(partyName){
            "더불어민주당" -> legislator_frag_party_color_circle_v.setBackgroundResource(R.drawable.party_blue_circle_box)
            "자유한국당" -> legislator_frag_party_color_circle_v.setBackgroundResource(R.drawable.party_red_circle_box)
            "바른미래당" -> legislator_frag_party_color_circle_v.setBackgroundResource(R.drawable.party_mint_circle_box)
            "정의당" -> legislator_frag_party_color_circle_v.setBackgroundResource(R.drawable.party_yellow_circle_box)
            "민중당" -> legislator_frag_party_color_circle_v.setBackgroundResource(R.drawable.party_orange_circle_box)
            "대한애국당" -> legislator_frag_party_color_circle_v.setBackgroundResource(R.drawable.party_indigo_circle_box)
            "민주평화당" -> legislator_frag_party_color_circle_v.setBackgroundResource(R.drawable.party_green_circle_box)
            "무소속" -> legislator_frag_party_color_circle_v.setBackgroundResource(R.drawable.party_blue_circle_box)
        }
    }

    private fun setClickListener() {
        legislator_page_act_back_btn.setOnClickListener {
            finish()
        }
    }

    private fun setRecyclerViewAdapter() {
        legislatorRecyclerViewAdapter = LegislatorRecyclerViewAdapter(this, contentsDataList)
        legislatorRecyclerViewAdapter.setOnItemClickListener(this)
        legislator_frag_recyclerview_rv.layoutManager = GridLayoutManager(this, 2)
        legislator_frag_recyclerview_rv.adapter = legislatorRecyclerViewAdapter

    }

    private fun requestLegislatorPageDataToServer() {
        networkService = ApplicationController.instance.networkService
        val getLegislatorResponse = networkService.getLegislatorResponse(SharedPreferenceController.getAuthorization(this),
                l_id)
        getLegislatorResponse.enqueue(object : Callback<GetLegislatorResponse> {
            override fun onFailure(call: Call<GetLegislatorResponse>?, t: Throwable?) {
            }

            override fun onResponse(call: Call<GetLegislatorResponse>?, response: Response<GetLegislatorResponse>?) {
                if (response!!.isSuccessful) {
                    legislatorData = response.body()!!.data

                    Log.e("받아오는 것들", legislatorData.toString())
                    contentsDataList = legislatorData.contents

                    setView()

                    setRecyclerViewAdapter()
                }
            }
        })
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
}