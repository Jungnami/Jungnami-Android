package sopt_jungnami_android.jungnami.legislator_list

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_search_result.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sopt_jungnami_android.jungnami.Get.GetRankingSearchLegislatorResponse
import sopt_jungnami_android.jungnami.Network.ApplicationController
import sopt_jungnami_android.jungnami.Network.NetworkService
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.data.RankingSearchLegislatorData

class SearchPartyActivity : AppCompatActivity() {

    lateinit var networkService: NetworkService
    lateinit var rankingSearchLegislatorItem : ArrayList<RankingSearchLegislatorData>
    var context : Context = this
    lateinit var searchResultRecyclerAdapter : SearchResultRecyclerAdapter


    fun setOnClickListener(p_name: String) {

        search_result_act_back_btn.setOnClickListener {
            search_result_act_is_display_search_box_rl.visibility = View.GONE
            finish()
        }


        // 상단 서치 바 누르면 재검색을 위한 뷰 visible
        search_result_act_search_bar.setOnClickListener {
            search_result_act_is_display_search_box_rl.visibility = View.VISIBLE

            // 에딧텍스트에 포커스 맞춰 바로 키보드올라오게 하는 코드
            search_result_act_top_bar_search_et.requestFocus()
            val imm: InputMethodManager = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(search_result_act_top_bar_search_et, InputMethodManager.SHOW_IMPLICIT)
        }

        // 블라인드 판넬을 건드리면 다시 검색화면을 보여준다.
        search_result_act_is_display_blind_panel_rl.setOnClickListener {

            search_result_act_is_display_search_box_rl.visibility = View.GONE
            val imm: InputMethodManager = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }

        // 취소 버튼을 눌렀을 때
        search_result_act_top_bar_search_cancel_btn.setOnClickListener{
            search_result_act_is_display_search_box_rl.visibility = View.GONE
            val imm: InputMethodManager = context!!.getSystemService( Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }

        // 검색 버튼을 눌렀을 때
        search_result_act_search_btn.setOnClickListener {

            var keyword2 = search_result_act_top_bar_search_et.text.toString()

            val intent = Intent(this, SearchRigionActivity::class.java)
            intent.putExtra("p_name", p_name)
            intent.putExtra("l_name", keyword2)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(intent)
//            startActivity<ContentsSearchActivity>("keyword" to keyword2)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)
        var p_name = intent.getStringExtra("p_name")
        var l_name = intent.getStringExtra("l_name")
        setStatusBarColor()
        getPartySearchLegislator(p_name, l_name )
        setOnClickListener(p_name)
    }

    fun getPartySearchLegislator(p_name: String, l_name: String) {
        search_result_act_search_reult_tv.text = l_name
        networkService = ApplicationController.instance.networkService
        var getRankingSearchLegislatorResponse = networkService.getPartySearchLegislator(p_name, l_name)
        getRankingSearchLegislatorResponse.enqueue(object : Callback<GetRankingSearchLegislatorResponse>{
            override fun onFailure(call: Call<GetRankingSearchLegislatorResponse>?, t: Throwable?) {
                Log.v("111","통신실패")

            }

            override fun onResponse(call: Call<GetRankingSearchLegislatorResponse>?, response: Response<GetRankingSearchLegislatorResponse>?) {
                Log.v("310","통신성공")
                if (response!!.isSuccessful){
//                    Log.v("300","통신성공")
//                    rankingSearchLegislatorItem = ArrayList(response.body()!!.data)
//                    Log.v("300",rankingSearchLegislatorItem[0].name)
//                    searchResultRecyclerAdapter = SearchResultRecyclerAdapter(rankingSearchLegislatorItem, context)
//                    search_result_act_search_rv.layoutManager = LinearLayoutManager(context)
//                    search_result_act_search_rv.adapter = searchResultRecyclerAdapter
//
                    var str = response!!.body()!!.message
                    Log.v("10101011110", "들어왔당")

                    if(!(str.equals("No data"))){
                        rankingSearchLegislatorItem = response!!.body()!!.data as ArrayList<RankingSearchLegislatorData>
                        search_result_act_search_rv.visibility = View.VISIBLE
                        search_result_act_search_rl.visibility = View.GONE
                        Log.v("101010",rankingSearchLegislatorItem.toString())
                        searchResultRecyclerAdapter = SearchResultRecyclerAdapter(rankingSearchLegislatorItem, context)
                        search_result_act_search_rv.layoutManager = LinearLayoutManager(context)
                        search_result_act_search_rv.adapter = searchResultRecyclerAdapter
                    }else {
                        //검색 결과 없음 뷰륿 보여줘야한다.
                        search_result_act_search_rv.visibility = View.GONE
                        search_result_act_search_rl.visibility = View.VISIBLE
                    }
                }
            }

        })
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
