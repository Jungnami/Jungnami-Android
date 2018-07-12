package sopt_jungnami_android.jungnami

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_legislator_list.*

// Written by SooYoung

class LegislatorList : AppCompatActivity(), View.OnClickListener {

    var isParty : Boolean = true

    var context : Context = this
    var isFavorableSelected: Boolean = true

    var party_name : String? = "party_name"
    var region_name : String = "region_name"
    lateinit var p_name : String
    lateinit var city: String

    override fun onClick(v: View?) {
        when(v) {
            legislator_list_act_back_btn -> { // 백버튼
                finish()
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_legislator_list)
        setStatusBarColor()
        setOnClickListener()
        // 프래그 먼트 넘어가는 단
        replaceFragment(LegislatorPartyListFragment())
        if (isParty == true){
            p_name = intent.getStringExtra("party_name")
        }else{
            region_name = intent.getStringExtra("region_name")
        }


//        getPartyLegislatorLikableListResponse()
//        getDistrictLegislatorLikableListResponse()
        isParty = intent.getBooleanExtra("isParty", true)
        if (isParty) {
            party_name = intent.getStringExtra("party_name")
            isPartyRegionSelected()
        } else {
            region_name = intent.getStringExtra("region_name")
            isPartyRegionSelected()
        }
    }

    public fun getp_name() : String{
        Log.v("이것", p_name)
        return p_name
    }

    public fun getregion_name() : String{
        return region_name
    }

    public fun getisParty() : Boolean{
        return isParty
    }



    fun isPartyRegionSelected() {
        when (party_name) {
            "blue" -> {
                partyPage()
            }
            "red" -> {
                partyPage()
            }
            "mint" -> {
                partyPage()
            }
            "yellow" -> {
                partyPage()
            }
            "orange" -> {
                partyPage()
            }
            "navy" -> {
                partyPage()
            }
            "green" -> {
                partyPage()
            }
            "gray" -> {
                partyPage()
            }
        }
        when (region_name){
            "서울" -> {
                legislator_list_act_party_category.text = "지역"
            }
            "인천" -> {
                legislator_list_act_party_category.text = "지역"
            }
            "경기" -> {
                legislator_list_act_party_category.text = "지역"
            }
            "강원" -> {
                legislator_list_act_party_category.text = "지역"
            }
            "충북" -> {
                legislator_list_act_party_category.text = "지역"
            }
            "충남" -> {
                legislator_list_act_party_category.text = "지역"
            }
            "세종" -> {
                legislator_list_act_party_category.text = "지역"
            }
            "대전" -> {
                legislator_list_act_party_category.text = "지역"
            }
            "경북" -> {
                legislator_list_act_party_category.text = "지역"
            }
            "대구" -> {
                legislator_list_act_party_category.text = "지역"
            }
            "울산" -> {
                legislator_list_act_party_category.text = "지역"
            }
            "부산" -> {
                legislator_list_act_party_category.text = "지역"
            }
            "전북" -> {
                legislator_list_act_party_category.text = "지역"
            }
            "광주" -> {
                legislator_list_act_party_category.text = "지역"
            }
            "경남" -> {
                legislator_list_act_party_category.text = "지역"
            }
            "전남" -> {
                legislator_list_act_party_category.text = "지역"
            }
            "제주" -> {
                legislator_list_act_party_category.text = "지역"
            }
        }
    }

    fun partyPage(){
        legislator_list_act_party_category.visibility = View.VISIBLE
        legislator_list_act_district_category.visibility = View.GONE
        replaceFragment(LegislatorPartyListFragment())
    }

    private fun partyBarColor(party_name: String, viewItem: View) {
        when (party_name) {
            "blue" -> viewItem.background.setColorFilter(Color.parseColor("#1783DC"), PorterDuff.Mode.SRC_IN)
            "red" -> viewItem.background.setColorFilter(Color.parseColor("#E1241A"), PorterDuff.Mode.SRC_IN)
            "mint" -> viewItem.background.setColorFilter(Color.parseColor("#14CDCC"), PorterDuff.Mode.SRC_IN)
            "yellow" -> viewItem.background.setColorFilter(Color.parseColor("#FCDC00"), PorterDuff.Mode.SRC_IN)
            "orange" -> viewItem.background.setColorFilter(Color.parseColor("#EC8C0D"), PorterDuff.Mode.SRC_IN)
            "navy" -> viewItem.background.setColorFilter(Color.parseColor("#123167"), PorterDuff.Mode.SRC_IN)
            "green" -> viewItem.background.setColorFilter(Color.parseColor("#3EA437"), PorterDuff.Mode.SRC_IN)
            "gray" -> viewItem.background.setColorFilter(Color.parseColor("#B7B7B7"), PorterDuff.Mode.SRC_IN)
            else -> viewItem.background.setColorFilter(Color.parseColor("#B7B7B7"), PorterDuff.Mode.SRC_IN)
        }
    }

    private fun setOnClickListener(){
        legislator_list_act_likeable_tab_btn.setOnClickListener {
            isFavorableSelected = true
            isSelectedTabView()
            replaceFragment(LegislatorPartyListFragment())
        }
        legislator_list_act_unlikeable_tab_btn.setOnClickListener {
            isFavorableSelected = false
            isSelectedTabView()
            replaceFragment(CutLegislatorDistrictListFragment())
        }
        legislator_list_act_search.setOnClickListener {
            legislator_list_act_search.visibility = View.GONE
            legislator_list_act_legislator_search_rl.visibility = View.VISIBLE
        }
        legislator_list_act_is_display_blind_panel_rl.setOnClickListener {
            legislator_list_act_legislator_search_rl.visibility = View.GONE
            legislator_list_act_search.visibility = View.VISIBLE
        }
    }

    private fun isSelectedTabView(){
        // 호감
        if (isFavorableSelected){
            legislator_list_act_likeable_title_tv.setTextColor(Color.parseColor("#6b6b6b"))
            legislator_list_act_likable_underbar_line.visibility = View.VISIBLE
            legislator_list_act_unlikeable_title_tv.setTextColor(Color.parseColor("#d8d8d8"))
            legislator_list_act_unlikeable_underbar_line.visibility = View.INVISIBLE
            if (legislator_list_act_party_category.visibility == View.VISIBLE && legislator_list_act_district_category.visibility == View.GONE){
                Log.v("123", "123")
                // 정당의 호감도
//                getPartyLegislatorLikableListResponse()
            }
            if(legislator_list_act_party_category.visibility == View.GONE && legislator_list_act_district_category.visibility == View.VISIBLE){
                Log.v("123", "123")
                // 지역의 호감도
//                getDistrictLegislatorLikableListResponse()
            }
        }

        // 비호감
        else {
            legislator_list_act_likeable_title_tv.setTextColor(Color.parseColor("#d8d8d8"))
            legislator_list_act_likable_underbar_line.visibility = View.INVISIBLE
            legislator_list_act_unlikeable_title_tv.setTextColor(Color.parseColor("#6b6b6b"))
            legislator_list_act_unlikeable_underbar_line.visibility = View.VISIBLE
            if (legislator_list_act_party_category.visibility == View.VISIBLE && legislator_list_act_district_category.visibility == View.GONE){
                // 정당의 비호감도
//                getPartyLegislatorUnlikableListResponse()
            }
            if(legislator_list_act_party_category.visibility == View.GONE && legislator_list_act_district_category.visibility == View.VISIBLE){
                // 지역의 비호감도
//                getDistrictLegislatorUnlikableListResponse()
            }
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

    private fun replaceFragment(fragment: Fragment){

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.legislator_list_frag_list, fragment)
        transaction.commit()
    }
}


//    private fun getPartyLegislatorLikableListResponse(){
//        Log.v("123", "123")
////        legislatorItems = ArrayList()
//        val getPartyLegislatorListResponse = networkService.getPartyLegislatorListResponse(SharedPreferenceController.getMyId(context),1, "더불어민주당")
//        getPartyLegislatorListResponse.enqueue(object : Callback<GetPartyDistrictLegislatorListResponse>{
//            override fun onFailure(call: Call<GetPartyDistrictLegislatorListResponse>?, t: Throwable?) {
//                Log.v("error", "통신 오류")
//            }
//            override fun onResponse(call: Call<GetPartyDistrictLegislatorListResponse>?, response: Response<GetPartyDistrictLegislatorListResponse>?) {
//                Log.v("success1", "통신1")
////                p_name = "더불어민주당"
//                var str = response!!.message()
//                Log.v("success!",str)
//                legislatorItems = response!!.body()!!.data
//                Log.v("success!",legislatorItems[0].name)
//                if(!(str.equals("No data"))){
//                    replaceFragment(LegislatorPartyListFragment())
//                    Log.v("success2", "통신2")
//                    partyDistrictLegislatorListViewAdapter = PartyDistrictLegislatorListViewAdapter(context, legislatorItems, "더불어민주당", "이상한 값",1)
//                    rv_legislator.layoutManager = LinearLayoutManager(context)
//                    rv_legislator.adapter = partyDistrictLegislatorListViewAdapter
//                }
//            }
//        })
//    }

//    private fun getPartyLegislatorUnlikableListResponse(){
//        var p_name : String = "더불어민주당"
//        val getPartyLegislatorListResponse = networkService.getPartyLegislatorListResponse(SharedPreferenceController.getMyId(context),0, "민주평화당")
//        getPartyLegislatorListResponse.enqueue(object : Callback<GetPartyDistrictLegislatorListResponse>{
//            override fun onFailure(call: Call<GetPartyDistrictLegislatorListResponse>?, t: Throwable?) {
//                Log.v("error", "통신 오류")
//            }
//            override fun onResponse(call: Call<GetPartyDistrictLegislatorListResponse>?, response: Response<GetPartyDistrictLegislatorListResponse>?) {
//                Log.v("success1", "통신1")
//                var str = response!!.message()
//                legislatorItems = response!!.body()!!.data
//                if(!(str.equals("No data"))){
//                    replaceFragment(LegislatorPartyListFragment())
//                    Log.v("success2", "통신2")
//                    partyDistrictLegislatorListViewAdapter = PartyDistrictLegislatorListViewAdapter(context, legislatorItems, p_name,"이상한 값", 0)
//                    rv_legislator.layoutManager = LinearLayoutManager(context)
//                    rv_legislator.adapter = partyDistrictLegislatorListViewAdapter
//                }
//            }
//        })
//    }

//    private fun getDistrictLegislatorLikableListResponse(){
//        var city : String = "seoul"
//        val getDistrictLegislatorListResponse = networkService.getDistrictLegislatorListResponse(SharedPreferenceController.getMyId(context),1, city)
//        getDistrictLegislatorListResponse.enqueue(object : Callback<GetPartyDistrictLegislatorListResponse>{
//            override fun onFailure(call: Call<GetPartyDistrictLegislatorListResponse>?, t: Throwable?) {
//                Log.v("error", "통신 오류")
//            }
//            override fun onResponse(call: Call<GetPartyDistrictLegislatorListResponse>?, response: Response<GetPartyDistrictLegislatorListResponse>?) {
//                Log.v("success3", "통신3")
//                var str = response!!.message()
//                legislatorItems = response!!.body()!!.data
//                if(!(str.equals("No data"))){
//                    replaceFragment(LegislatorPartyListFragment())
//                    Log.v("success4", "통신4")
//                    partyDistrictLegislatorListViewAdapter = PartyDistrictLegislatorListViewAdapter(context, legislatorItems, "이상한 값", city, 1)
//                    rv_legislator.layoutManager = LinearLayoutManager(context)
//                    rv_legislator.adapter = partyDistrictLegislatorListViewAdapter
//                }
//            }
//        })
//    }


//    private fun getDistrictLegislatorUnlikableListResponse(){
//        var city : String = "seoul"
//        val getDistrictLegislatorListResponse = networkService.getDistrictLegislatorListResponse(SharedPreferenceController.getMyId(context),0, city)
//        getDistrictLegislatorListResponse.enqueue(object : Callback<GetPartyDistrictLegislatorListResponse>{
//            override fun onFailure(call: Call<GetPartyDistrictLegislatorListResponse>?, t: Throwable?) {
//                Log.v("error", "통신 오류")
//            }
//            override fun onResponse(call: Call<GetPartyDistrictLegislatorListResponse>?, response: Response<GetPartyDistrictLegislatorListResponse>?) {
//                Log.v("success3", "통신3")
//                var str = response!!.message()
//                legislatorItems = response!!.body()!!.data
//                if(!(str.equals("No data"))){
//                    replaceFragment(LegislatorPartyListFragment())
//                    Log.v("success4", "통신4")
//                    partyDistrictLegislatorListViewAdapter = PartyDistrictLegislatorListViewAdapter(context, legislatorItems, "이상한 값", city,0)
//                    rv_legislator.layoutManager = LinearLayoutManager(context)
//                    rv_legislator.adapter = partyDistrictLegislatorListViewAdapter
//                }
//            }
//        })
//    }
