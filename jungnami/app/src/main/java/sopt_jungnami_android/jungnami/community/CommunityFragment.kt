package sopt_jungnami_android.jungnami.community


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_search_result.*
import kotlinx.android.synthetic.main.fragment_community.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.startActivityForResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sopt_jungnami_android.jungnami.*
import sopt_jungnami_android.jungnami.Get.GetCommunityFeedResponse
import sopt_jungnami_android.jungnami.Get.GetCommunitySearchResponse
import sopt_jungnami_android.jungnami.Network.ApplicationController
import sopt_jungnami_android.jungnami.Network.NetworkService
import sopt_jungnami_android.jungnami.data.CommunitySearchData
import sopt_jungnami_android.jungnami.data.Content
import sopt_jungnami_android.jungnami.db.SharedPreferenceController
import sopt_jungnami_android.jungnami.mypage.MyPageActivity

class CommunityFragment : Fragment(), View.OnClickListener {
    override fun onClick(v: View?) {
    }

    private val REQUEST_CODE_WRITE = 1001
    lateinit var feedDataList: ArrayList<Content>
    lateinit var SearchFeedDataList: ArrayList<CommunitySearchData>
    lateinit var communityRecyclerViewAdapter: CommunityRecyclerViewAdapter
    lateinit var communitySearchRecyclerViewAdapter: CommunitySearchRecyclerViewAdapter
    lateinit var networkService: NetworkService
    lateinit var user_img_url : String
    var alarmcnt: Int = 0

    override fun onSaveInstanceState(outState: Bundle) {
        Log.i("상태 저장 중", "feedDataList 저장!!!")
        outState.putSerializable("feedDataList", feedDataList)
        outState.putString("user_img_url", user_img_url)
        outState.putInt("alarmcnt", alarmcnt)
        super.onSaveInstanceState(outState)
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
        setClickedListener()
        networkService = ApplicationController.instance.networkService
        community_frag_refresh.isRefreshing = true
        if (savedInstanceState == null){
            Log.i("상태 저장 없다", "feedDataList 새롭게 받는중")
            getCommunityFeed()
        } else {
            Log.i("상태 저장 꺼내오는중", "feedDataList 꺼내옴!!!!!!")
            feedDataList = savedInstanceState.getSerializable("feedDataList") as ArrayList<Content>
            user_img_url = savedInstanceState.getString("user_img_url")
            alarmcnt = savedInstanceState.getInt("alarmcnt")

            Glide.with(context!!)
                    .load(user_img_url)
                    .into(community_frag_my_picture_iv)

            community_top_bar_new_post_counter_tv.text = alarmcnt.toString()

            setRecyclerViewAdapter()

            community_frag_refresh.isRefreshing = false
        }
    }

    private fun setRecyclerViewAdapter() {
        communityRecyclerViewAdapter = CommunityRecyclerViewAdapter(context!!, feedDataList)
        communityRecyclerViewAdapter.setOnItemClickListener(this)
        community_frag_feed_list_rv.adapter = communityRecyclerViewAdapter
        community_frag_feed_list_rv.layoutManager = LinearLayoutManager(activity)
    }

    private fun setSearchCommunityRecyclerViewAdapter() {
        communitySearchRecyclerViewAdapter = CommunitySearchRecyclerViewAdapter(context!!, SearchFeedDataList)
        communitySearchRecyclerViewAdapter.setOnItemClickListener(this)
        community_frag_feed_list_rv.adapter = communitySearchRecyclerViewAdapter
        community_frag_feed_list_rv.layoutManager = LinearLayoutManager(activity)
    }


    fun getCommunityFeed() {
        val getCommunityFeedResponse = networkService.getCommunityFeed(SharedPreferenceController.getAuthorization(context!!))
        getCommunityFeedResponse.enqueue(object : Callback<GetCommunityFeedResponse> {
            override fun onFailure(call: Call<GetCommunityFeedResponse>?, t: Throwable?) {
            }

            override fun onResponse(call: Call<GetCommunityFeedResponse>?, response: Response<GetCommunityFeedResponse>?) {
                if (response!!.isSuccessful) {
                    user_img_url = response!!.body()!!.data!!.user_img_url

                    Glide.with(context!!)
                            .load(user_img_url)
                            .into(community_frag_my_picture_iv)

                    alarmcnt = response!!.body()!!.data!!.alarmcnt

                    community_top_bar_new_post_counter_tv.text = alarmcnt.toString()

                    feedDataList = response!!.body()!!.data!!.content

                    setRecyclerViewAdapter()

                    community_frag_refresh.isRefreshing = false

                }
            }

        })
    }

    fun getCommunitySearchFeed(keyword : String) {
        val keyword : String = keyword
        community_frag_top_bar_search_et.hint = keyword

        val GetCommunitySearchResponse = networkService.getCommunitySearchResult(SharedPreferenceController.getAuthorization(context!!), keyword)

        GetCommunitySearchResponse.enqueue(object : Callback<GetCommunitySearchResponse> {
            override fun onFailure(call: Call<GetCommunitySearchResponse>?, t: Throwable?) {
            }

            override fun onResponse(call: Call<GetCommunitySearchResponse>?, response: Response<GetCommunitySearchResponse>?) {
                if(response!!.isSuccessful) {
                    // 300 error No data 일 경우 검색결과 없음을 띄움.
                    if(response!!.body()!!.message.equals("No data")){
                        Log.v("xxx","xxx")
                        community_frag_feed_list_rv.visibility = View.GONE
                        community_frag_no_search_result_rl.visibility = View.VISIBLE
                    }else{
                        SearchFeedDataList = response!!.body()!!.data
                        setSearchCommunityRecyclerViewAdapter()
                    }

                }
            }
        })
    }

    private fun setClickedListener() {
        community_frag_refresh.setOnRefreshListener {
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

}
