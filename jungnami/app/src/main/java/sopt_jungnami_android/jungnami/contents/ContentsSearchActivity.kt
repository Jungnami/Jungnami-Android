package sopt_jungnami_android.jungnami.contents

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_contents_search.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sopt_jungnami_android.jungnami.Get.GetContentSearchResponse
import sopt_jungnami_android.jungnami.Network.ApplicationController
import sopt_jungnami_android.jungnami.Network.NetworkService
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.data.ContentsSearchData
import sopt_jungnami_android.jungnami.db.SharedPreferenceController

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
                        contentsSearchItems = response!!.body()!!.data
                        if (contentsSearchItems.size == 0) {

                        } else {
                            changeConetentsRecyclerViewData()
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
            finish()
        }

        contents_search_act_search_result_tv.setOnClickListener {
            search_nothing_result_act_search_hint_et.visibility = View.VISIBLE
            contents_search_act_search_result_tv.visibility = View.GONE
            startActivity<ContentsSearchActivity>("keyword" to keyword)

        }
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
