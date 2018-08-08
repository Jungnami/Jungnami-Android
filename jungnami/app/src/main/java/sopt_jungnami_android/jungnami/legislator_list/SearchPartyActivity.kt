package sopt_jungnami_android.jungnami.legislator_list

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
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


    fun setOnClickListener() {
        search_result_act_back_btn.setOnClickListener {
            finish()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)
        setStatusBarColor()
        getPartySearchLegislator()
        setOnClickListener()
    }

    fun getPartySearchLegislator() {
        var p_name = intent.getStringExtra("p_name")
        var l_name = intent.getStringExtra("l_name")
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
