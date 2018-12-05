package sopt_jungnami_android.jungnami.legislator_list

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_contents_search.*
import kotlinx.android.synthetic.main.activity_legislator_list.*
import kotlinx.android.synthetic.main.activity_search_result.*
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sopt_jungnami_android.jungnami.Get.GetRankingSearchLegislatorResponse
import sopt_jungnami_android.jungnami.Network.ApplicationController
import sopt_jungnami_android.jungnami.Network.NetworkService
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.contents.ContentsSearchActivity
import sopt_jungnami_android.jungnami.data.RankingSearchLegislatorData

class SearchActivity : AppCompatActivity(){


    fun setOnClickListener() {
        search_result_act_back_btn.setOnClickListener {
            // 재검색을 위한 뷰 곤처리
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

            val intent = Intent(this, SearchActivity::class.java)
            intent.putExtra("keyword", keyword2)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(intent)
//            startActivity<ContentsSearchActivity>("keyword" to keyword2)
        }

        // 엔터리스너
        search_result_act_search_hint_et.setOnKeyListener(object: View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if ((event!!.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    //Enter키눌렀을떄 처리
                    var keyword2 = search_result_act_top_bar_search_et.text.toString()

                    val intent = Intent(applicationContext, SearchActivity::class.java)
//                    val intent = Intent(this, SearchActivity::class.java)
                    intent.putExtra("keyword", keyword2)
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                    startActivity(intent)
//            startActivity<ContentsSearchActivity>("keyword" to keyword2)
                    return true;
                }
                return false;
            }
        })

    }

    lateinit var networkService: NetworkService
    lateinit var legislatorResultItems : ArrayList<RankingSearchLegislatorData>
    var context : Context = this
    lateinit var searchResultRecyclerAdapter: SearchResultRecyclerAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)
        setStatusBarColor()
        networkService = ApplicationController.instance.networkService

        getRankingSearchLegislator()
        setOnClickListener()



    }

    fun getRankingSearchLegislator(){


        val keyword = intent.getStringExtra("keyword")
        search_result_act_search_reult_tv.text = keyword
        val getRankingSearchLegislatorResponse =  networkService.getRankingSearchLegislator(keyword!!)

        getRankingSearchLegislatorResponse.enqueue(object  : Callback<GetRankingSearchLegislatorResponse>{
            override fun onFailure(call: Call<GetRankingSearchLegislatorResponse>?, t: Throwable?) {
            }

            override fun onResponse(call: Call<GetRankingSearchLegislatorResponse>?, response: Response<GetRankingSearchLegislatorResponse>?) {
                if(response!!.isSuccessful){
                    var str = response!!.body()!!.message
                    Log.v("10101011110", "들어왔당")
                    if(!(str.equals("No data"))){
                        legislatorResultItems = response!!.body()!!.data as ArrayList<RankingSearchLegislatorData>
                        search_result_act_search_rv.visibility = View.VISIBLE
                        search_result_act_search_rl.visibility = View.GONE
                        Log.v("101010",legislatorResultItems.toString())
                        searchResultRecyclerAdapter = SearchResultRecyclerAdapter(legislatorResultItems, context)
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

