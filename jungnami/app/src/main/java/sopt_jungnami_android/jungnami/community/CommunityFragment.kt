package sopt_jungnami_android.jungnami.community


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_legislator_list.*
import kotlinx.android.synthetic.main.activity_search_result.*
import kotlinx.android.synthetic.main.fragment_community.*
import kotlinx.android.synthetic.main.fragment_legislator_party_list.*
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.startActivityForResult
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sopt_jungnami_android.jungnami.*
import sopt_jungnami_android.jungnami.Delete.DeleteBoardResponse
import sopt_jungnami_android.jungnami.Get.GetCommunityFeedResponse
import sopt_jungnami_android.jungnami.Get.GetCommunitySearchResponse
import sopt_jungnami_android.jungnami.Network.ApplicationController
import sopt_jungnami_android.jungnami.Network.NetworkService
import sopt_jungnami_android.jungnami.data.CommunitySearchData
import sopt_jungnami_android.jungnami.data.Content
import sopt_jungnami_android.jungnami.db.SharedPreferenceController
import sopt_jungnami_android.jungnami.legislator_list.SearchPartyActivity
import sopt_jungnami_android.jungnami.legislator_list.SearchRigionActivity
import sopt_jungnami_android.jungnami.mypage.MyPageActivity

class CommunityFragment : Fragment(), View.OnClickListener, View.OnLongClickListener {
    override fun onClick(v: View?) {
    }

    override fun onLongClick(v: View?): Boolean {
        val position : Int = community_frag_feed_list_rv.getChildAdapterPosition(v)
        alert ("삭제하시겠습니까?"){
            yesButton {
                deleteCommunityRequest(position)
            }
            noButton {
                toast("취소")
            }
        }.show()
        return true
    }

    private val REQUEST_CODE_WRITE = 1001
    val feedDataList: ArrayList<Content> by lazy {
        ArrayList<Content>()
    }
    lateinit var SearchFeedDataList: ArrayList<CommunitySearchData>
    val communityRecyclerViewAdapter: CommunityRecyclerViewAdapter by lazy {
        CommunityRecyclerViewAdapter(context!!, feedDataList)
    }
    lateinit var communitySearchRecyclerViewAdapter: CommunitySearchRecyclerViewAdapter
    lateinit var networkService: NetworkService
    lateinit var user_img_url : String
    var alarmcnt: Int = 0

    var currentItemsCount : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        networkService = ApplicationController.instance.networkService
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_community, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (SharedPreferenceController.getAuthorization(context!!) == "") {
            community_frag_what_do_u_think_box.visibility = View.GONE
        } else {
            community_frag_no_login_status_rl.visibility = View.GONE
        }

        setRecyclerViewAdapter()


        setClickedListener()


        getCommunityFeed()

        moreLoadListData()
    }
    
    private fun setRecyclerViewAdapter() {
        communityRecyclerViewAdapter.setOnItemClickListener(this)
        communityRecyclerViewAdapter.setOnItemLongClickListener(this)
        community_frag_feed_list_rv.adapter = communityRecyclerViewAdapter
        community_frag_feed_list_rv.layoutManager = LinearLayoutManager(activity)
    }

    fun getCommunityFeed() {
        community_frag_refresh.isRefreshing = true
        val getCommunityFeedResponse = networkService.getCommunityFeed(SharedPreferenceController.getAuthorization(context!!), currentItemsCount)
        getCommunityFeedResponse.enqueue(object : Callback<GetCommunityFeedResponse> {
            override fun onFailure(call: Call<GetCommunityFeedResponse>?, t: Throwable?) {
            }

            override fun onResponse(call: Call<GetCommunityFeedResponse>?, response: Response<GetCommunityFeedResponse>?) {
                if (response!!.isSuccessful) {
                    if (feedDataList.size != 0){
                        feedDataList.clear()
                        communityRecyclerViewAdapter.dataList.clear()
                    }
                    feedDataList.addAll(response!!.body()!!.data!!.content)
                    communityRecyclerViewAdapter.addItems(feedDataList)

                    currentItemsCount = feedDataList.size
                    community_frag_refresh.isRefreshing = false
                    //리사이클러뷰 제외한 다른 UI 데이터 셋팅
                    if(!response!!.body()!!.data!!.user_img_url.isNullOrEmpty()){
                        user_img_url = response!!.body()!!.data!!.user_img_url
                        Glide.with(context!!)
                                .load(user_img_url)
                                .into(community_frag_my_picture_iv)
                    }
                    alarmcnt = response!!.body()!!.data!!.alarmcnt
                    community_top_bar_new_post_counter_tv.text = alarmcnt.toString()

                }
            }
        })
    }
    fun getMoreCommunityFeed() {
        val getCommunityFeedResponse = networkService.getCommunityFeed(SharedPreferenceController.getAuthorization(context!!), currentItemsCount)
        getCommunityFeedResponse.enqueue(object : Callback<GetCommunityFeedResponse> {
            override fun onFailure(call: Call<GetCommunityFeedResponse>?, t: Throwable?) {
                Log.e("more request failed", t.toString())
            }
            override fun onResponse(call: Call<GetCommunityFeedResponse>?, response: Response<GetCommunityFeedResponse>?) {
                if (response!!.isSuccessful) {
                    //Log.e("데이터 더 있나?", response!!.body()!!.data!!.content.size.toString())
                    communityRecyclerViewAdapter.addItems(response!!.body()!!.data!!.content)
                    feedDataList.addAll(response!!.body()!!.data!!.content)
                    currentItemsCount = feedDataList.size
                    community_frag_refresh.isRefreshing = false
                }
            }
        })
    }
    fun deleteCommunityRequest(position : Int){
        val deleteBoardResponse = networkService.deleteBoardResponse(
                SharedPreferenceController.getAuthorization(context!!), feedDataList[position].boardid)
        deleteBoardResponse.enqueue(object : Callback<DeleteBoardResponse>{
            override fun onFailure(call: Call<DeleteBoardResponse>?, t: Throwable?) {
            }

            override fun onResponse(call: Call<DeleteBoardResponse>?, response: Response<DeleteBoardResponse>?) {
                if (response!!.isSuccessful){
                    getCommunityFeed()
                    toast("글 삭제")
                } else {
                    toast("본인 글이 아닙니다.")
                }
            }
        })
    }
    private fun setClickedListener() {
        community_frag_refresh.setOnRefreshListener {
            currentItemsCount = 0
            getCommunityFeed()
        }
        //종
        community_frag_top_bar_bell_btn.setOnClickListener {
            startActivity<Alarm>()
        }
        //마이페이지
        community_frag_top_bar_my_page_btn.setOnClickListener {
            startActivity<MyPageActivity>()
        }
        //내 피드 작성
        community_frag_write_feed_btn.setOnClickListener {
            startActivityForResult<CommunityWritePage>(REQUEST_CODE_WRITE, "isShared" to 0)
        }

        // 검색을 위해 상단 검색 바를 눌렀을 때
        community_frag_top_bar_search_et.setOnClickListener {

            // 에딧텍스트에 포커스 맞춰 바로 키보드올라오게 하는 코드
            community_frag_top_bar_search_et.requestFocus()
            val imm: InputMethodManager = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(community_frag_top_bar_search_et, InputMethodManager.SHOW_IMPLICIT)

            // 검색 버튼
            community_frag_top_bar_search_btn.visibility = View.VISIBLE
            // 상단 검색 바 밑에 어두운 부분 보이게 하기
            community_frag_is_display_search_rl.visibility = View.VISIBLE


        }

        // 상단 검색 바 밑에 어두운 부분 GONE 처리
        community_frag_is_display_search_rl.setOnClickListener {

            community_frag_is_display_search_rl.visibility = View.GONE
            community_frag_top_bar_search_btn.visibility = View.GONE

            val imm: InputMethodManager = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)

        }
        // 검색 버튼 눌렀을 때
        community_frag_top_bar_search_btn.setOnClickListener {
            community_frag_is_display_search_rl.visibility = View.GONE
            var keyword : String = community_frag_top_bar_search_et.text.toString()
            Log.v("1001000100010", "start")
            startActivity<CommunitySearchResultActivity>("keyword" to keyword)
        }

        // 엔터리스너
        community_frag_top_bar_search_et.setOnKeyListener(object: View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if ((event!!.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    //Enter키눌렀을떄 처리
                    community_frag_is_display_search_rl.visibility = View.GONE
                    var keyword : String = community_frag_top_bar_search_et.text.toString()
                    Log.v("1001000100010", "start")
                    startActivity<CommunitySearchResultActivity>("keyword" to keyword)
                    return true;
                }
                return false;
            }
        })


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_WRITE) {
            if (resultCode == Activity.RESULT_OK) {
                val isComplete = data!!.getBooleanExtra("isComplete", false)
                if (isComplete) {
                    getCommunityFeed()
                }
            }
        }
    }
    private fun moreLoadListData() {
        ns_community_frag_list.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            if (v!!.getChildAt(v!!.childCount - 1) != null) {
                if ((scrollY >= (v.getChildAt(v!!.childCount - 1).measuredHeight) - v.measuredHeight) && scrollY > oldScrollY) {
                    Log.e("끝까지 옴", "스크롤 끝까지!")
                    currentItemsCount = feedDataList.size
                    doAsync {
                        community_frag_refresh.isRefreshing = true
                    }
                    getMoreCommunityFeed()

                }
            }
        }
    }
}
