package sopt_jungnami_android.jungnami

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_legislator_list.*
import kotlinx.android.synthetic.main.fragment_legislator_list.*
import kotlinx.android.synthetic.main.fragment_legislator_party_list.*
import org.jetbrains.anko.backgroundDrawable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sopt_jungnami_android.jungnami.Get.GetPartyDistrictLegislatorListResponse
import sopt_jungnami_android.jungnami.Network.ApplicationController
import sopt_jungnami_android.jungnami.Network.NetworkService
import sopt_jungnami_android.jungnami.R.color.*
import sopt_jungnami_android.jungnami.data.PartyDistrictLegistlatorListData
import sopt_jungnami_android.jungnami.db.SharedPreferenceController

// Written by SooYoung

class LegislatorList : AppCompatActivity(), View.OnClickListener {

    lateinit var networkService: NetworkService
    lateinit var legislatorItems : ArrayList<PartyDistrictLegistlatorListData>
    lateinit var partyDistrictLegislatorListViewAdapter: PartyDistrictLegislatorListViewAdapter

    var isParty : Boolean = true

    var context : Context = this
    var isFavorableSelected: Boolean = true

    var party_name : String? = "party_name"
    var region_name : String? = "region_name"

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

        networkService = ApplicationController.instance.networkService

        isParty = intent.getBooleanExtra("isParty", true)
        if (isParty) {
            party_name = intent.getStringExtra("party_name")
            isPartyRegionSelected()
            setOnClickListener()
        } else {
            region_name = intent.getStringExtra("region_name")
            isPartyRegionSelected()
            setOnClickListener()
        }
    }

    private fun getPartyLegislatorLikableListResponse(){
        var p_name : String = "더불어민주당"
        val getPartyLegislatorListResponse = networkService.getPartyLegislatorListResponse(SharedPreferenceController.getMyId(context),1, p_name)
        getPartyLegislatorListResponse.enqueue(object : Callback<GetPartyDistrictLegislatorListResponse>{
            override fun onFailure(call: Call<GetPartyDistrictLegislatorListResponse>?, t: Throwable?) {
                Log.v("error", "통신 오류")
            }
            override fun onResponse(call: Call<GetPartyDistrictLegislatorListResponse>?, response: Response<GetPartyDistrictLegislatorListResponse>?) {
                Log.v("success1", "통신1")
                var str = response!!.message()
                legislatorItems = response!!.body()!!.data as ArrayList<PartyDistrictLegistlatorListData>
                if(!(str.equals("No data"))){
                    Log.v("success2", "통신2")
                    partyDistrictLegislatorListViewAdapter = PartyDistrictLegislatorListViewAdapter(context, legislatorItems)
                    rv_legislator.layoutManager = LinearLayoutManager(context)
                    rv_legislator.adapter = partyDistrictLegislatorListViewAdapter
                }
            }
        })
    }

    private fun getPartyLegislatorUnlikableListResponse(){
        var p_name : String = "더불어민주당"
        val getPartyLegislatorListResponse = networkService.getPartyLegislatorListResponse(SharedPreferenceController.getMyId(context),0, p_name)
        getPartyLegislatorListResponse.enqueue(object : Callback<GetPartyDistrictLegislatorListResponse>{
            override fun onFailure(call: Call<GetPartyDistrictLegislatorListResponse>?, t: Throwable?) {
                Log.v("error", "통신 오류")
            }
            override fun onResponse(call: Call<GetPartyDistrictLegislatorListResponse>?, response: Response<GetPartyDistrictLegislatorListResponse>?) {
                Log.v("success1", "통신1")
                var str = response!!.message()
                legislatorItems = response!!.body()!!.data as ArrayList<PartyDistrictLegistlatorListData>
                if(!(str.equals("No data"))){
                    Log.v("success2", "통신2")
                    partyDistrictLegislatorListViewAdapter = PartyDistrictLegislatorListViewAdapter(context, legislatorItems)
                    rv_legislator.layoutManager = LinearLayoutManager(context)
                    rv_legislator.adapter = partyDistrictLegislatorListViewAdapter
                }
            }
        })
    }

    private fun getDistrictLegislatorLikableListResponse(){
        var city : String = "seoul"
        val getDistrictLegislatorListResponse = networkService.getDistrictLegislatorListResponse(SharedPreferenceController.getMyId(context),1, city)
        getDistrictLegislatorListResponse.enqueue(object : Callback<GetPartyDistrictLegislatorListResponse>{
            override fun onFailure(call: Call<GetPartyDistrictLegislatorListResponse>?, t: Throwable?) {
                Log.v("error", "통신 오류")
            }
            override fun onResponse(call: Call<GetPartyDistrictLegislatorListResponse>?, response: Response<GetPartyDistrictLegislatorListResponse>?) {
                Log.v("success3", "통신3")
                var str = response!!.message()
                legislatorItems = response!!.body()!!.data as ArrayList<PartyDistrictLegistlatorListData>
                if(!(str.equals("No data"))){
                    Log.v("success4", "통신4")
                    partyDistrictLegislatorListViewAdapter = PartyDistrictLegislatorListViewAdapter(context, legislatorItems)
                    rv_legislator.layoutManager = LinearLayoutManager(context)
                    rv_legislator.adapter = partyDistrictLegislatorListViewAdapter
                }
            }
        })
    }

    private fun getDistrictLegislatorUnlikableListResponse(){
        var city : String = "seoul"
        val getDistrictLegislatorListResponse = networkService.getDistrictLegislatorListResponse(SharedPreferenceController.getMyId(context),0, city)
        getDistrictLegislatorListResponse.enqueue(object : Callback<GetPartyDistrictLegislatorListResponse>{
            override fun onFailure(call: Call<GetPartyDistrictLegislatorListResponse>?, t: Throwable?) {
                Log.v("error", "통신 오류")
            }
            override fun onResponse(call: Call<GetPartyDistrictLegislatorListResponse>?, response: Response<GetPartyDistrictLegislatorListResponse>?) {
                Log.v("success3", "통신3")
                var str = response!!.message()
                legislatorItems = response!!.body()!!.data as ArrayList<PartyDistrictLegistlatorListData>
                if(!(str.equals("No data"))){
                    Log.v("success4", "통신4")
                    partyDistrictLegislatorListViewAdapter = PartyDistrictLegislatorListViewAdapter(context, legislatorItems)
                    rv_legislator.layoutManager = LinearLayoutManager(context)
                    rv_legislator.adapter = partyDistrictLegislatorListViewAdapter
                }
            }
        })
    }

    fun isPartyRegionSelected() {
        when (party_name) {
            "blue" -> {
                legislator_list_act_party_category.visibility = View.VISIBLE
                legislator_list_act_district_category.visibility = View.GONE
                replaceFragment(LegislatorPartyListFragment())
                //legislator_frag_party_color_party_iv.setBackgroundColor(Orange)
            }
            "red" -> {
                legislator_list_act_party_category.visibility = View.VISIBLE
                legislator_list_act_district_category.visibility = View.GONE
            }
            "mint" -> {
                legislator_list_act_party_category.visibility = View.VISIBLE
                legislator_list_act_district_category.visibility = View.GONE

            }
            "yellow" -> {
                legislator_list_act_party_category.visibility = View.VISIBLE
                legislator_list_act_district_category.visibility = View.GONE

            }
            "orange" -> {
                legislator_list_act_party_category.visibility = View.VISIBLE
                legislator_list_act_district_category.visibility = View.GONE

            }
            "navy" -> {
                legislator_list_act_party_category.visibility = View.VISIBLE
                legislator_list_act_district_category.visibility = View.GONE

            }
            "green" -> {
                legislator_list_act_party_category.visibility = View.VISIBLE
                legislator_list_act_district_category.visibility = View.GONE

            }
            "gray" -> {
                legislator_list_act_party_category.visibility = View.VISIBLE
                legislator_list_act_district_category.visibility = View.GONE
            }
        }
        when (region_name){
            "seoul" -> {
                legislator_list_act_party_category.visibility = View.GONE
                legislator_list_act_district_category.visibility = View.VISIBLE
                replaceFragment(LegislatorPartyListFragment())
            }
            "incheon" -> {
                legislator_list_act_party_category.visibility = View.GONE
                legislator_list_act_district_category.visibility = View.VISIBLE
            }
            "gyeonggi" -> {
                legislator_list_act_party_category.visibility = View.GONE
                legislator_list_act_district_category.visibility = View.VISIBLE
            }
            "gangwon" -> {
                legislator_list_act_party_category.visibility = View.GONE
                legislator_list_act_district_category.visibility = View.VISIBLE
            }
            "chungbug" -> {
                legislator_list_act_party_category.visibility = View.GONE
                legislator_list_act_district_category.visibility = View.VISIBLE
            }
            "chungnam" -> {
                legislator_list_act_party_category.visibility = View.GONE
                legislator_list_act_district_category.visibility = View.VISIBLE
            }
            "sejong" -> {
                legislator_list_act_party_category.visibility = View.GONE
                legislator_list_act_district_category.visibility = View.VISIBLE
            }
            "daejeon" -> {
                legislator_list_act_party_category.visibility = View.GONE
                legislator_list_act_district_category.visibility = View.VISIBLE
            }
            "gyeongbug" -> {
                legislator_list_act_party_category.visibility = View.GONE
                legislator_list_act_district_category.visibility = View.VISIBLE
            }
            "daegu" -> {
                legislator_list_act_party_category.visibility = View.GONE
                legislator_list_act_district_category.visibility = View.VISIBLE
            }
            "ulsan" -> {
                legislator_list_act_party_category.visibility = View.GONE
                legislator_list_act_district_category.visibility = View.VISIBLE
            }
            "busan" -> {
                legislator_list_act_party_category.visibility = View.GONE
                legislator_list_act_district_category.visibility = View.VISIBLE
            }
            "jeonbug" -> {
                legislator_list_act_party_category.visibility = View.GONE
                legislator_list_act_district_category.visibility = View.VISIBLE
            }
            "gwangju" -> {
                legislator_list_act_party_category.visibility = View.GONE
                legislator_list_act_district_category.visibility = View.VISIBLE
            }
            "gyeongnam" -> {
                legislator_list_act_party_category.visibility = View.GONE
                legislator_list_act_district_category.visibility = View.VISIBLE
            }
            "jeonnam" -> {
                legislator_list_act_party_category.visibility = View.GONE
                legislator_list_act_district_category.visibility = View.VISIBLE
            }
            "jeju" -> {
                legislator_list_act_party_category.visibility = View.GONE
                legislator_list_act_district_category.visibility = View.VISIBLE
            }
        }
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
            if (legislator_list_act_party_category.visibility == View.VISIBLE && legislator_list_act_district_category.visibility == View.GONE){
                getPartyLegislatorLikableListResponse()
            }
            if(legislator_list_act_party_category.visibility == View.GONE && legislator_list_act_district_category.visibility == View.VISIBLE){
                getDistrictLegislatorLikableListResponse()
            }
            isFavorableSelected = true
            isSelectedTabView()
        }
        legislator_list_act_unlikeable_tab_btn.setOnClickListener {
            if (legislator_list_act_party_category.visibility == View.VISIBLE && legislator_list_act_district_category.visibility == View.GONE){
                getPartyLegislatorUnlikableListResponse()
            }
            if(legislator_list_act_party_category.visibility == View.GONE && legislator_list_act_district_category.visibility == View.VISIBLE){
                getDistrictLegislatorUnlikableListResponse()
            }
            isFavorableSelected = false
            isSelectedTabView()
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
        if (isFavorableSelected){
            legislator_list_act_likeable_title_tv.setTextColor(Color.parseColor("#6b6b6b"))
            legislator_list_act_likable_underbar_line.visibility = View.VISIBLE
            legislator_list_act_unlikeable_title_tv.setTextColor(Color.parseColor("#d8d8d8"))
            legislator_list_act_unlikeable_underbar_line.visibility = View.INVISIBLE
        }
        else {
            legislator_list_act_likeable_title_tv.setTextColor(Color.parseColor("#d8d8d8"))
            legislator_list_act_likable_underbar_line.visibility = View.INVISIBLE
            legislator_list_act_unlikeable_title_tv.setTextColor(Color.parseColor("#6b6b6b"))
            legislator_list_act_unlikeable_underbar_line.visibility = View.VISIBLE
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
        val fm = supportFragmentManager
        val transaction = fm.beginTransaction()
        transaction.replace(R.id.legislator_list_frag_list, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}