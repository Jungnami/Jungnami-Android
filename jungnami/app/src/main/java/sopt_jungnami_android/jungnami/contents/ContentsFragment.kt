package sopt_jungnami_android.jungnami.contents

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_contents.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sopt_jungnami_android.jungnami.Alarm
import sopt_jungnami_android.jungnami.Get.GetRecommendContentsResponse
import sopt_jungnami_android.jungnami.Get.GetTmiStoryContentsResponse
import sopt_jungnami_android.jungnami.Network.ApplicationController
import sopt_jungnami_android.jungnami.Network.NetworkService
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.data.Contents
import sopt_jungnami_android.jungnami.db.SharedPreferenceController
import sopt_jungnami_android.jungnami.mypage.MyPageActivity

//    made by Yunhwan
class ContentsFragment : Fragment(), View.OnClickListener {

    override fun onClick(v: View?) {
        when(current_tab_idx){
            0 -> {
                val index : Int = contents_frag_sub_contents_recycler_rv.getChildAdapterPosition(v)
                val contents_id : Int = recommendDataList[index].contentsid
                //toast("${contents_id} = 컨텐츠 아이디, ${recommendDataList[index].title} = 제목" )
                startActivity<ContentsDetail>("contents_id" to contents_id)
            }
            1,2 -> {
                val index : Int = contents_frag_sub_contents_recycler_rv.getChildAdapterPosition(v)
                val contents_id : Int = tmiOrStoryDataList[index].contentsid
                //toast("${contents_id} = 컨텐츠 아이디, ${tmiOrStoryDataList[index].title} = 제목" )
                startActivity<ContentsDetail>("contents_id" to contents_id)
            }
        }

    }

    var current_tab_idx : Int = 0
    lateinit var contentsRecyclerViewAdapter: ContentsRecyclerViewAdapter
    lateinit var recommendDataList: ArrayList<Contents>
    lateinit var tmiOrStoryDataList : ArrayList<Contents>
    lateinit var storyDataList : ArrayList<Contents>
    var mainContentData : Contents? = null
    var contents_id : Int = 0
    lateinit var networkService : NetworkService
    var alertCount : Int = 0

    lateinit var mainRecommendImage : ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_contents, container, false)
        mainRecommendImage = view.findViewById(R.id.contents_frag_main_content_image_iv) as ImageView
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setClickListener()

        requestRecommendContentsDataToServer()



    }
    private fun setClickListener(){
        contents_frag_top_bar_my_page_btn.setOnClickListener {
            startActivity<MyPageActivity>()
        }
        contents_frag_top_bar_bell_btn.setOnClickListener {
            startActivity<Alarm>()
        }
        //Tab 클릭 리스터
        contents_frag_recommend_btn.setOnClickListener {
            checkSelectedTabView(0)
        }
        contents_frag_tmi_btn.setOnClickListener {
            checkSelectedTabView(1)
        }
        contents_frag_story_btn.setOnClickListener {
            checkSelectedTabView(2)
        }
        //메인 컨텐츠 클릭 리스터
        contents_frag_main_content_lr.setOnClickListener {
            startActivity<ContentsDetail>("contents_id" to contents_id)
        }

    }

    private fun setMainContentView(mainContent : Contents?){
        if (mainContent != null){
            val requestOptions = RequestOptions()
            requestOptions.centerCrop()
            Glide.with(context!!)
                    .setDefaultRequestOptions(requestOptions)
                    .load(mainContent!!.thumbnail)
                    .into(mainRecommendImage)
            contents_frag_main_content_title_tv.text = mainContent!!.title
            contents_frag_main_content_info_tv.text = mainContent!!.text

            contents_frag_main_content_lr.visibility = View.VISIBLE
        }

    }

    private fun requestRecommendContentsDataToServer(){
        recommendDataList = ArrayList()
        networkService = ApplicationController.instance.networkService

        val getRecommendContentsResponse = networkService.getRecommendContentsResponse(SharedPreferenceController.getAuthorization(context = context!!))
        getRecommendContentsResponse.enqueue(object : Callback<GetRecommendContentsResponse>{
            override fun onFailure(call: Call<GetRecommendContentsResponse>?, t: Throwable?) {
                Log.e("추천 컨텐츠 요청 실패", "추천 컨텐츠 요청 실패!!!!")
            }

            override fun onResponse(call: Call<GetRecommendContentsResponse>?, response: Response<GetRecommendContentsResponse>?) {
                if (response!!.isSuccessful){
                    if (response!!.body()!!.data.content.size != 0){
                        alertCount = response.body()!!.data.alarmcnt
                        setAlertBellView()
                        recommendDataList = response.body()!!.data.content

                        contents_id = recommendDataList[0].contentsid
                        setMainContentView(recommendDataList[0])
                        recommendDataList.removeAt(0)

                        //추천 컨텐츠 뿌리기
                        changeConetentsRecyclerViewData()
                    } else {
                        contents_frag_main_content_lr.visibility = View.GONE
                    }
                }
            }
        })
    }
    private fun requestTmiOrStoryDataToServer(category : String){
        tmiOrStoryDataList = ArrayList()
        networkService = ApplicationController.instance.networkService
        Log.e("요청 카테고리는? ", category)
        val getTmiStoryContentsResponse = networkService.getTmiStoryContentsResponse(SharedPreferenceController.getAuthorization(context = context!!),category)
        getTmiStoryContentsResponse.enqueue(object : Callback<GetTmiStoryContentsResponse>{
            override fun onFailure(call: Call<GetTmiStoryContentsResponse>?, t: Throwable?) {
                Log.e("컨텐츠 요청 실패", "컨텐츠 요청 실패!!!!")
            }
            override fun onResponse(call: Call<GetTmiStoryContentsResponse>?, response: Response<GetTmiStoryContentsResponse>?) {
                if (response!!.isSuccessful){
                    if (response!!.body()!!.data.content.size != 0){
                        Log.e("TMI or STORY 컨텐츠 ", "${response!!.body()!!.data.content}")
                        alertCount = response.body()!!.data.alarmcnt
                        setAlertBellView()
                        tmiOrStoryDataList = response.body()!!.data.content

                        setMainContentView(tmiOrStoryDataList[0])
                        contents_id = tmiOrStoryDataList[0].contentsid
                        tmiOrStoryDataList.removeAt(0)

                        changeConetentsRecyclerViewData()
                    } else {
                        Log.e("컨텐츠 없넹2", "컨텐츠 없넹2")
                        contents_frag_main_content_lr.visibility = View.GONE
                    }

                }
            }
        })
    }

    private fun setAlertBellView(){
        if (alertCount >0){
            contents_act_bell_circle_img.visibility = View.VISIBLE
            contents_act_top_bar_new_post_counter_tv.text = alertCount.toString()
        }
    }


    private fun changeConetentsRecyclerViewData(){
        if (current_tab_idx == 0){
            contentsRecyclerViewAdapter = ContentsRecyclerViewAdapter(context!!, recommendDataList)
        } else {
            contentsRecyclerViewAdapter = ContentsRecyclerViewAdapter(context!!, tmiOrStoryDataList)
        }

        contentsRecyclerViewAdapter.setOnItemClickListener(this)
        contents_frag_sub_contents_recycler_rv.layoutManager = GridLayoutManager(context!!,2) as RecyclerView.LayoutManager?
        contents_frag_sub_contents_recycler_rv.adapter = contentsRecyclerViewAdapter
    }


    private fun changeNonSelectedTabView(idx : Int){
        when (idx) {
            0 -> {
                contents_frag_recommend_tv.setTextColor(Color.parseColor("#D8D8D8"))
                contents_frag_recommend_underline.visibility = View.INVISIBLE
            }
            1 -> {
                contents_frag_tmi_tv.setTextColor(Color.parseColor("#D8D8D8"))
                contents_frag_tmi_underline.visibility = View.INVISIBLE
            }
            2 -> {
                contents_frag_story_tv.setTextColor(Color.parseColor("#D8D8D8"))
                contents_frag_story_underline.visibility = View.INVISIBLE
            }
        }
    }

    private fun changeSelectedTabView(idx : Int){
        when (idx) {
            0 -> {
                contents_frag_recommend_tv.setTextColor(Color.parseColor("#36C5F1"))
                contents_frag_recommend_underline.visibility = View.VISIBLE
            }
            1 -> {
                contents_frag_tmi_tv.setTextColor(Color.parseColor("#36C5F1"))
                contents_frag_tmi_underline.visibility = View.VISIBLE
            }
            2 -> {
                contents_frag_story_tv.setTextColor(Color.parseColor("#36C5F1"))
                contents_frag_story_underline.visibility = View.VISIBLE
            }
        }
    }


    private fun checkSelectedTabView(selected_tab_idx : Int){
        when (selected_tab_idx){
            0 -> {
                changeNonSelectedTabView(current_tab_idx)
                current_tab_idx = 0
                changeSelectedTabView(current_tab_idx)

                requestRecommendContentsDataToServer()
            }
            1 -> {
                changeNonSelectedTabView(current_tab_idx)
                current_tab_idx = 1
                changeSelectedTabView(current_tab_idx)

                requestTmiOrStoryDataToServer("TMI")
            }
            2 -> {
                changeNonSelectedTabView(current_tab_idx)
                current_tab_idx = 2
                changeSelectedTabView(current_tab_idx)

                requestTmiOrStoryDataToServer("스토리")
            }
        }
    }
}
