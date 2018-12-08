package sopt_jungnami_android.jungnami.contents

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_community_search_result.*
import kotlinx.android.synthetic.main.activity_legislator_list.*
import kotlinx.android.synthetic.main.fragment_community.*
import kotlinx.android.synthetic.main.fragment_contents.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sopt_jungnami_android.jungnami.Alarm
import sopt_jungnami_android.jungnami.Get.GetCommunityFeedResponse
import sopt_jungnami_android.jungnami.Get.GetRecommendContentsResponse
import sopt_jungnami_android.jungnami.Get.GetTmiStoryContentsResponse
import sopt_jungnami_android.jungnami.MainActivity
import sopt_jungnami_android.jungnami.Network.ApplicationController
import sopt_jungnami_android.jungnami.Network.NetworkService
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.community.CommunitySearchResultActivity
import sopt_jungnami_android.jungnami.data.Contents
import sopt_jungnami_android.jungnami.db.SharedPreferenceController
import sopt_jungnami_android.jungnami.legislator_list.SearchPartyActivity
import sopt_jungnami_android.jungnami.legislator_list.SearchRigionActivity
import sopt_jungnami_android.jungnami.mypage.MyPageActivity
import java.io.Serializable

//    made by Yunhwan
class ContentsFragment : Fragment(), View.OnClickListener {

    // 추천 탭에서 밑으로 스크롤 시 10개씩 item 더 가져오기 위한 마지막 item의 id
    private var preReal : Int = 0

    // TMI 탭에서 밑으로 스크롤 시 10개씩 item 더 가져오기 위한 마지막 item의 id
    private var preTMIReal : Int = 0

    // Story 탭에서 밑으로 스크롤 시 10개씩 item 더 가져오기 위한 마지막 item의 id
    private var preStoryReal : Int = 0

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
    lateinit var mainContentData : Contents
    var contents_id : Int = 0
    lateinit var networkService : NetworkService
    var alertCount : Int = 0
    lateinit var edittext : EditText

    lateinit var mainRecommendImage : ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_contents, container, false)
        mainRecommendImage = view.findViewById(R.id.contents_frag_main_content_image_iv) as ImageView

        return view
    }
//
//    override fun onSaveInstanceState(outState: Bundle) {
//        outState.putSerializable("recommendDataList", recommendDataList)
////        outState.putSerializable("tmiOrStoryDataList", tmiOrStoryDataList)
//        outState.putInt("alertCount", alertCount)
//        outState.putSerializable("mainContentData", mainContentData)
//        Log.e("컨텐츠 상태저장 시작", "저장 시작")
//        super.onSaveInstanceState(outState)
//    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setClickListener()
//        if (savedInstanceState != null){
//            Log.e("컨텐츠 상태저장 불러오기", "저장 불러오기")
//            recommendDataList = savedInstanceState.getSerializable("recommendDataList") as ArrayList<Contents>
//            alertCount = savedInstanceState.getInt("alertCount")
//            mainContentData = savedInstanceState.getSerializable("mainContentData") as Contents
//            setMainContentView(mainContentData)
//            changeConetentsRecyclerViewData()
//
//        } else {
//            Log.e("컨텐츠 상태저장 내용 없음", "저장된 내용 없음")
//            requestRecommendContentsDataToServer()
//        }

        requestRecommendContentsDataToServer(preReal)
        moreLoadListData()
    }
    private fun setClickListener(){
        contents_frag_refresh_srl.setOnRefreshListener {
            contents_frag_refresh_srl.isRefreshing = false
        }
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

        // 회색 검색박스를 눌렀을 때 검색화면이 나오게 한다.
        contents_frag_top_bar_search_box_search_rl.setOnClickListener {
            contents_frag_is_display_search_box_rl.visibility = View.VISIBLE

            // 에딧텍스트에 포커스 맞춰 바로 키보드올라오게 하는 코드
            contents_frag_top_bar_search_et.requestFocus()
            val imm: InputMethodManager = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(contents_frag_top_bar_search_et, InputMethodManager.SHOW_IMPLICIT)
        }

        // 블라인드 판넬을 건드리면 다시 검색화면을 보여준다.
        contents_frag_is_display_blind_panel_rl.setOnClickListener {

            contents_frag_is_display_search_box_rl.visibility = View.GONE
            val imm: InputMethodManager = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }

        // 취소 버튼을 눌렀을 때
        contents_frag_top_bar_search_cancel_btn.setOnClickListener{
            contents_frag_is_display_search_box_rl.visibility = View.GONE
            val imm: InputMethodManager = context!!.getSystemService( Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }


        // 검색 버튼 눌럿을 때
        contents_frag_search_btn.setOnClickListener {

            var keyword = contents_frag_top_bar_search_et.text.toString()

            // ContentsSearchActivity에서 백버튼을 눌렀을 때 검색화면 다시 GONE
            contents_frag_is_display_search_box_rl.visibility = View.GONE

            startActivity<ContentsSearchActivity>("keyword" to keyword)

        }
        contents_frag_top_bar_search_et.setOnKeyListener(object: View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if ((event!!.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    //Enter키눌렀을떄 처리
                    Log.v("TAG","눌렸다")
                    var keyword = contents_frag_top_bar_search_et.text.toString()

                    // ContentsSearchActivity에서 백버튼을 눌렀을 때 검색화면 다시 GONE
                    contents_frag_is_display_search_box_rl.visibility = View.GONE

                    startActivity<ContentsSearchActivity>("keyword" to keyword)
                    return true;
                }
                return false;
            }
        })
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

    private fun requestRecommendContentsDataToServer(pre: Int){
        recommendDataList = ArrayList()
        networkService = ApplicationController.instance.networkService

        val getRecommendContentsResponse = networkService.getRecommendContentsResponse(SharedPreferenceController.getAuthorization(context = context!!), pre)
        getRecommendContentsResponse.enqueue(object : Callback<GetRecommendContentsResponse>{
            override fun onFailure(call: Call<GetRecommendContentsResponse>?, t: Throwable?) {
            }

            override fun onResponse(call: Call<GetRecommendContentsResponse>?, response: Response<GetRecommendContentsResponse>?) {
                if (response!!.isSuccessful){
                    if (response!!.body()!!.data.content.size != 0){
                        alertCount = response.body()!!.data.alarmcnt
                        setAlertBellView()
                        recommendDataList = response.body()!!.data.content

                        mainContentData = recommendDataList[0]

                        contents_id = recommendDataList[0].contentsid
                        setMainContentView(recommendDataList[0])
                        recommendDataList.removeAt(0)

                        //추천 컨텐츠 뿌리기
                        changeConetentsRecyclerViewData()

//                        preReal = recommendDataList.last().contentsid
                        Log.v("TAGG", preReal.toString())
                    } else {
                        contents_frag_main_content_lr.visibility = View.GONE

                    }
                }
            }
        })
    }

    // 추천 Item 더 가져오기
    fun getMoreCotentsFeed() {
        val getRecommendContentsResponse = networkService.getRecommendContentsResponse(SharedPreferenceController.getAuthorization(context!!), preReal)
        getRecommendContentsResponse.enqueue(object : Callback<GetRecommendContentsResponse> {
            override fun onFailure(call: Call<GetRecommendContentsResponse>?, t: Throwable?) {
                Log.e("more request failed", t.toString())
            }
            override fun onResponse(call: Call<GetRecommendContentsResponse>?, response: Response<GetRecommendContentsResponse>?) {
                if (response!!.isSuccessful) {
                    //Log.e("데이터 더 있나?", response!!.body()!!.data!!.content.size.toString())
                    contentsRecyclerViewAdapter.addItems(response!!.body()!!.data!!.content)
//                    recommendDataList.addAll(response!!.body()!!.data!!.content)
                    preReal = recommendDataList.last().contentsid
                    contents_frag_refresh_srl.isRefreshing = false
                }
            }
        })
    }

    private fun requestTmiOrStoryDataToServer(category : String){
        tmiOrStoryDataList = ArrayList()
        networkService = ApplicationController.instance.networkService
        Log.e("요청 카테고리는? ", category)
        val getTmiStoryContentsResponse = networkService.getTmiStoryContentsResponse(SharedPreferenceController.getAuthorization(context = context!!),category, 0)
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

                        if(category == "TMI"){
                            preTMIReal = tmiOrStoryDataList.last().contentsid
                        }else if(category == "스토리"){
                            preStoryReal = tmiOrStoryDataList.last().contentsid
                        }
                        changeConetentsRecyclerViewData()
                    } else {
                        Log.e("컨텐츠 없넹2", "컨텐츠 없넹2")
                        contents_frag_main_content_lr.visibility = View.GONE
                    }

                }
            }
        })
    }

    // TMI 탭 Item 더 가져오기
    fun getMoreTMICotentsFeed(category: String, pre: Int) {
        val getTmiStoryContentsResponse = networkService.getTmiStoryContentsResponse(SharedPreferenceController.getAuthorization(context!!), category, pre)
        getTmiStoryContentsResponse.enqueue(object : Callback<GetTmiStoryContentsResponse> {
            override fun onFailure(call: Call<GetTmiStoryContentsResponse>?, t: Throwable?) {
                Log.e("more request failed", t.toString())
            }
            override fun onResponse(call: Call<GetTmiStoryContentsResponse>?, response: Response<GetTmiStoryContentsResponse>?) {
                if (response!!.isSuccessful) {
                    //Log.e("데이터 더 있나?", response!!.body()!!.data!!.content.size.toString())
                    contentsRecyclerViewAdapter.addItems(response!!.body()!!.data!!.content)


//                    tmiOrStoryDataList.addAll(response!!.body()!!.data!!.content)
                    if(category == "TMI"){
                        preTMIReal = tmiOrStoryDataList.last().contentsid
                    }else if(category == "스토리"){
                        preStoryReal = tmiOrStoryDataList.last().contentsid
                    }
                    contents_frag_refresh_srl.isRefreshing = false
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

                requestRecommendContentsDataToServer(0)
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

    private fun moreLoadListData() {
        contents_frag_nested_v.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            if (v!!.getChildAt(v!!.childCount - 1) != null) {
                if ((scrollY >= (v.getChildAt(v!!.childCount - 1).measuredHeight) - v.measuredHeight) && scrollY > oldScrollY) {
                    Log.e("끝까지 옴", "스크롤 끝까지!")
                    preReal = recommendDataList.last().contentsid
                    doAsync {
                        community_frag_refresh.isRefreshing = true
                    }
                    // 추천 탭일 경우
                    if(current_tab_idx == 0){
                        getMoreCotentsFeed()
                    } // TMI 탭일 경우
                    else if(current_tab_idx == 1){
                        // TMI 논리처리
                        Log.v("TAGG", "preTMIReal" + preTMIReal.toString())
                        getMoreTMICotentsFeed("TMI", preTMIReal)
                    } // Story 탭일 경우
                    else if(current_tab_idx == 2){
                        Log.v("TAGG", "여기도불림?" + preTMIReal.toString())
                        // Story 논리처리
                        getMoreTMICotentsFeed("스토리", preStoryReal)
                    }

                }
            }
        }
    }
}