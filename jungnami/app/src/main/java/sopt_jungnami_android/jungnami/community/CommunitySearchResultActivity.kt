package sopt_jungnami_android.jungnami.community

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_community_search_result.*
import kotlinx.android.synthetic.main.fragment_community.*
import kotlinx.android.synthetic.main.fragment_legislator_list.*
import kotlinx.android.synthetic.main.fragment_rank.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sopt_jungnami_android.jungnami.Get.GetCommunitySearchResponse
import sopt_jungnami_android.jungnami.Network.ApplicationController
import sopt_jungnami_android.jungnami.Network.NetworkService
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.contents.ContentsSearchActivity
import sopt_jungnami_android.jungnami.data.CommunitySearchData
import sopt_jungnami_android.jungnami.db.SharedPreferenceController
import sopt_jungnami_android.jungnami.legislator_list.SearchActivity

class CommunitySearchResultActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {

    }


    private lateinit var onItemClick: View.OnClickListener
    lateinit var networkService: sopt_jungnami_android.jungnami.Network.NetworkService
    lateinit var SearchFeedDataList: ArrayList<CommunitySearchData>
    lateinit var communitySearchRecyclerViewAdapter: CommunitySearchRecyclerViewAdapter
    var context: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_search_result)
        setStatusBarColor()
        setOnClickListener()
        var keyword = intent.getStringExtra("keyword")
        getCommunitySearchFeed(keyword)
    }

    fun setOnClickListener() {

        // 백버튼 눌렀을 때
        community_search_result_act_back_btn.setOnClickListener {
            finish()
        }

        // 재검색을 위해 상단 바를 눌렀을 때
        community_search_result_act_search_bar.setOnClickListener {

            // 에딧 텍스트와 검색 버튼이 있는 새로운 뷰를 visible로 만든다.
            community_search_act_is_display_search_box_rl.visibility = View.VISIBLE

            // 에딧텍스트에 포커스 맞춰 바로 키보드올라오게 하는 코드
            community_search_act_top_bar_search_et.requestFocus()
            val imm: InputMethodManager = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(community_search_act_top_bar_search_et, InputMethodManager.SHOW_IMPLICIT)

        }

        // 블라인드 판넬을 건드리면 다시 검색화면을 보여준다.
        community_search_act_is_display_blind_panel_rl.setOnClickListener {

            community_search_act_is_display_search_box_rl.visibility = View.GONE
            val imm: InputMethodManager = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }

        // 검색 버튼을 눌렀을 때
        community_search_result_act_search_btn.setOnClickListener {

            var keyword2 = community_search_act_top_bar_search_et.text.toString()

            val intent = Intent(this, CommunitySearchResultActivity::class.java)
            intent.putExtra("keyword", keyword2)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(intent)


//            startActivity<CommunitySearchResultActivity>("keyword" to keyword2)
        }

        // 취소 버튼을 눌렀을 때
        community_search_act_top_bar_search_cancel_btn.setOnClickListener {
            community_search_act_is_display_search_box_rl.visibility = View.GONE
            val imm: InputMethodManager = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
        // 엔터리스너
        community_search_act_top_bar_search_et.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if ((event!!.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    var keyword2 = community_search_act_top_bar_search_et.text.toString()

                    val intent = Intent(applicationContext, CommunitySearchResultActivity::class.java)
                    intent.putExtra("keyword", keyword2)
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                    startActivity(intent)
                    return true;
                }
                return false;
            }
        })


    }

    fun getCommunitySearchFeed(keyword: String) {

        community_search_result_act_search_reult_tv.text = keyword
        networkService = ApplicationController.instance.networkService

        val GetCommunitySearchResponse = networkService.getCommunitySearchResult(SharedPreferenceController.getAuthorization(context!!), keyword)

        GetCommunitySearchResponse.enqueue(object : Callback<GetCommunitySearchResponse> {
            override fun onFailure(call: Call<GetCommunitySearchResponse>?, t: Throwable?) {
            }

            override fun onResponse(call: Call<GetCommunitySearchResponse>?, response: Response<GetCommunitySearchResponse>?) {
                if (response!!.isSuccessful) {
                    // 300 error No data 일 경우 검색결과 없음을 띄움.
                    if (response!!.body()!!.message.equals("No data")) {

                        // 검색 결과 없음 텍스트 띄움
                        community_search_no_result_act_search_rl.visibility = View.VISIBLE

                        // 리사이클러 뷰를 GONE 처리
                        community_search_result_act_search_rv.visibility = View.GONE

                    } else {
                        SearchFeedDataList = response!!.body()!!.data

                        // 어뎁터 단
                        setSearchCommunityRecyclerViewAdapter()
                    }

                }
            }
        })
    }

    private fun setSearchCommunityRecyclerViewAdapter() {
        communitySearchRecyclerViewAdapter = CommunitySearchRecyclerViewAdapter(context!!, SearchFeedDataList)
        communitySearchRecyclerViewAdapter.setOnItemClickListener(this)
        community_search_result_act_search_rv.adapter = communitySearchRecyclerViewAdapter
        community_search_result_act_search_rv.layoutManager = LinearLayoutManager(context)
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
