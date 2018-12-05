package sopt_jungnami_android.jungnami.contents

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_community_search_result.*
import kotlinx.android.synthetic.main.activity_contents_search.*
import kotlinx.android.synthetic.main.activity_legislator_list.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sopt_jungnami_android.jungnami.Get.GetContentSearchResponse
import sopt_jungnami_android.jungnami.Network.ApplicationController
import sopt_jungnami_android.jungnami.Network.NetworkService
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.community.CommunitySearchResultActivity
import sopt_jungnami_android.jungnami.data.ContentsSearchData
import sopt_jungnami_android.jungnami.db.SharedPreferenceController
import sopt_jungnami_android.jungnami.legislator_list.SearchPartyActivity
import sopt_jungnami_android.jungnami.legislator_list.SearchRigionActivity

class ContentsSearchActivity : AppCompatActivity(), View.OnClickListener {


    lateinit var keyword : String
    lateinit var networkService: NetworkService
    lateinit var contentsSearchItems : ArrayList<ContentsSearchData>
    var context : Context = this
    lateinit var contentsSearchRecyclerViewAdapter: ContentsSearchRecyclerViewAdapter

    override fun onClick(v: View?) {
        val index : Int = contents_search_act_rv.getChildAdapterPosition(v)
        val contents_id : Int = contentsSearchItems[index].contentsid
        //toast("${contents_id} = 컨텐츠 아이디, ${tmiOrStoryDataList[index].title} = 제목" )
        startActivity<ContentsDetail> ("contents_id" to contents_id)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contents_search)
        setStatusBarColor()
        keyword = intent.getStringExtra("keyword")
        contentsSearchItems = ArrayList()
        setClickListener()
        getContentsSearchresult(keyword)
        contents_search_act_search_result_tv.setText(keyword)
    }

    fun getContentsSearchresult(keyword : String){
        if (keyword.isEmpty()){
            contents_search_commend_tv.visibility = View.VISIBLE
        } else {
            contents_search_commend_tv.visibility = View.GONE
            contentsSearchItems = ArrayList()
            networkService = ApplicationController.instance.networkService
            val getContentSearchResponse = networkService.getContentsSearchresult(SharedPreferenceController.getAuthorization(context),
                    keyword)

            getContentSearchResponse.enqueue(object : Callback<GetContentSearchResponse>{

                override fun onFailure(call: Call<GetContentSearchResponse>?, t: Throwable?) {

                }

                override fun onResponse(call: Call<GetContentSearchResponse>?, response: Response<GetContentSearchResponse>?) {
                    if (response!!.isSuccessful){

                        val str = response!!.body()!!.message

                        if (!(str.equals("No data"))){
                            contentsSearchItems = response!!.body()!!.data
                            changeConetentsRecyclerViewData()
                        }else {
                            // No data일 경우 "검색 결과 없음 출력"
                            contents_search_commend_tv.visibility = View.VISIBLE
//                            // No data일 경우 recycler view gone 처리
//                            contents_search_act_rv.visibility = View.GONE
                        }
                    }
                }

            })
        }
    }

    private fun changeConetentsRecyclerViewData(){
        contentsSearchRecyclerViewAdapter = ContentsSearchRecyclerViewAdapter(context, contentsSearchItems)
        Log.v("통신안", "들어오긴옴")
        contents_search_act_rv.layoutManager = GridLayoutManager(context!!,2)
        Log.v("통신안", "들어오긴옴2")
        contents_search_act_rv.adapter = contentsSearchRecyclerViewAdapter
        Log.v("통신안", "들어오긴옴3")
        contentsSearchRecyclerViewAdapter.setOnItemClickListener(this)
    }

    fun setClickListener(){
        contents_search_act_back_btn.setOnClickListener {

//            // 검색 버튼 GONE
//            contents_search_act_search_btn.visibility = View.GONE
//
//            // searchResult VISIBLE
//            contents_search_act_search_result_rl.visibility = View.VISIBLE

            contents_search_act_is_display_search_box_rl.visibility = View.GONE

            finish()
        }



        // 재검색을 위해 상단 바를 눌렀을 때
        contents_search_act_search_bar.setOnClickListener {

            // 에딧 텍스트와 검색 버튼이 있는 새로운 뷰를 visible로 만든다.
            contents_search_act_is_display_search_box_rl.visibility = View.VISIBLE

            // 에딧텍스트에 포커스 맞춰 바로 키보드올라오게 하는 코드
            contents_search_act_top_bar_search_et.requestFocus()
            val imm: InputMethodManager = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(contents_search_act_top_bar_search_et, InputMethodManager.SHOW_IMPLICIT)

        }

        // 블라인드 판넬을 건드리면 다시 검색화면을 보여준다.
        contents_search_act_is_display_blind_panel_rl.setOnClickListener {

            contents_search_act_is_display_search_box_rl.visibility = View.GONE
            val imm: InputMethodManager = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }

        // 검색 버튼을 눌렀을 때
        contents_search_act_search_btn.setOnClickListener {

            var keyword2 = contents_search_act_top_bar_search_et.text.toString()

            val intent = Intent(this, ContentsSearchActivity::class.java)
            intent.putExtra("keyword", keyword2)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(intent)
//            startActivity<ContentsSearchActivity>("keyword" to keyword2)
        }

        // 취소 버튼을 눌렀을 때
        contents_search_act_top_bar_search_cancel_btn.setOnClickListener{
            contents_search_act_is_display_search_box_rl.visibility = View.GONE
            val imm: InputMethodManager = context!!.getSystemService( Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }

        contents_search_act_top_bar_search_et.setOnKeyListener(object: View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if ((event!!.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    //Enter키눌렀을떄 처리
                    Log.v("TAG","눌렸다")
                    var keyword2 = contents_search_act_top_bar_search_et.text.toString()

                    val intent = Intent(applicationContext, ContentsSearchActivity::class.java)
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
