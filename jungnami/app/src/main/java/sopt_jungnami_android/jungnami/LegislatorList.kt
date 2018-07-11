package sopt_jungnami_android.jungnami

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_legislator_list.*
import sopt_jungnami_android.jungnami.Network.ApplicationController
import sopt_jungnami_android.jungnami.Network.NetworkService
import sopt_jungnami_android.jungnami.data.PartyDistrictLegistlatorListData

// Written by SooYoung

class LegislatorList : AppCompatActivity(), View.OnClickListener {

    lateinit var networkService: NetworkService
    lateinit var legislatorItems : ArrayList<PartyDistrictLegistlatorListData>
    lateinit var partyDistrictLegislatorListViewAdapter: PartyDistrictLegislatorListViewAdapter

    var isParty : Boolean = true

    var context : Context = this
    var isFavorableSelected: Boolean = true

    lateinit var party_name : String
    lateinit var region_name : String

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

        networkService = ApplicationController.instance.networkService
        getPartyDistrictLegislatorList()
        isParty = intent.getBooleanExtra("isParty", true)
        if (isParty) {
            party_name = intent.getStringExtra("party_name")
        } else {
            region_name = intent.getStringExtra("region_name")
        }
        isPartyRegionSelected()
    }

    fun getPartyDistrictLegislatorList(){

    }

    fun isPartyRegionSelected() {
        when (party_name){
            "blue" -> {
                legislator_list_act_party_category.visibility = View.GONE
                legislator_list_act_district_category.visibility = View.VISIBLE

            }
            "red" -> {

            }
            "mint" -> {

            }
            "yellow" -> {

            }
            "orange" -> {

            }
            "navy" -> {

            }
            "green" -> {

            }
            "gray" -> {

            }
        }
        when (region_name){
            "seoul" -> {

            }
            "incheon" -> {

            }
            "gyeonggi" -> {

            }
            "gangwon" -> {

            }
            "chungbug" -> {

            }
            "chungnam" -> {

            }
            "sejong" -> {

            }
            "daejeon" -> {

            }
            "gyeongbug" -> {

            }
            "daegu" -> {

            }
            "ulsan" -> {

            }
            "busan" -> {

            }
            "jeonbug" -> {

            }
            "gwangju" -> {

            }
            "gyeongnam" -> {

            }
            "jeonnam" -> {

            }
            "jeju" -> {

            }
        }
    }

    private fun setOnClickListener(){
        legislator_list_act_likeable_tab_btn.setOnClickListener {
            isFavorableSelected = true
            isSelectedTabView()
        }
        legislator_list_act_unlikeable_tab_btn.setOnClickListener {
            isFavorableSelected = false
            isSelectedTabView()
        }
        legislator_list_act_search.setOnClickListener {
            legislator_list_act_legislator_search_rl.visibility = View.VISIBLE
        }
        legislator_list_act_is_display_blind_panel_rl.setOnClickListener {
            legislator_list_act_legislator_search_rl.visibility = View.GONE
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
