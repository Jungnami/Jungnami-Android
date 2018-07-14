package sopt_jungnami_android.jungnami

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import kotlinx.android.synthetic.main.fragment_legislator_party_list.*
import kotlinx.android.synthetic.main.fragment_likeable_tab.*
import org.jetbrains.anko.support.v4.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sopt_jungnami_android.jungnami.Get.GetPartyDistrictLegislatorListResponse
import sopt_jungnami_android.jungnami.Network.ApplicationController
import sopt_jungnami_android.jungnami.Network.NetworkService
import sopt_jungnami_android.jungnami.R.id.legislator_frag_top_iv
import sopt_jungnami_android.jungnami.R.id.rv_legislator
import sopt_jungnami_android.jungnami.data.PartyDistrictLegistlatorListData
import sopt_jungnami_android.jungnami.db.SharedPreferenceController
import sopt_jungnami_android.jungnami.legislator.LegislatorPageActivity

class LegislatorPartyListFragment: Fragment()  {


    lateinit var networkService: NetworkService
    lateinit var legislatorItems : ArrayList<PartyDistrictLegistlatorListData>
    lateinit var legislatorListAdapter : LegislatorListViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_legislator_party_list, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        legislatorItems = ArrayList()
        setAdapter()

        var isParty = (context as LegislatorList).getisParty()
        if (isParty == true){
            getPartyLegislatorLikableListResponse()
        }else if (isParty == false){
            getDistrictLegislatorLikableListResponse()
        }
        if (isParty == true) {
            var p_name = (context as LegislatorList).getp_name()
            when (p_name) {
                "더불어민주당" -> {
                    legislator_frag_top_iv.setImageResource(R.drawable.party_blue_tab)
                }
                "자유한국당" -> {
                    legislator_frag_top_iv.setImageResource(R.drawable.party_red_tab)
                }
                "바른미래당" -> {
                    legislator_frag_top_iv.setImageResource(R.drawable.party_mint_tab)
                }
                "정의당" -> {
                    legislator_frag_top_iv.setImageResource(R.drawable.party_yellow_tab)
                }
                "민중당" -> {
                    legislator_frag_top_iv.setImageResource(R.drawable.party_orange_tab)
                }
                "대한애국당" -> {
                    legislator_frag_top_iv.setImageResource(R.drawable.party_indigo_tab)
                }
                "민주평화당" -> {
                    legislator_frag_top_iv.setImageResource(R.drawable.party_green_tab)
                }
                "무소속" -> {
                    legislator_frag_top_iv.setImageResource(R.drawable.party_gray_tab)
                }
            }
        }else {
            var region_name = (context as LegislatorList).getregion_name()
            when (region_name){
                "서울" -> {
                    legislator_frag_top_iv.setImageResource(R.drawable.region_seoul_tab)
                }
                "인천" -> {
                    legislator_frag_top_iv.setImageResource(R.drawable.region_incheon_tab)
                }
                "경기" -> {
                    legislator_frag_top_iv.setImageResource(R.drawable.region_gyeonggi_tab)

                }
                "강원" -> {
                    legislator_frag_top_iv.setImageResource(R.drawable.region_gangwon_tab)

                }
                "충북" -> {
                    legislator_frag_top_iv.setImageResource(R.drawable.region_chungbug_tab)
                }
                "충남" -> {
                    legislator_frag_top_iv.setImageResource(R.drawable.region_chungnam_tab)
                }
                "세종" -> {
                    legislator_frag_top_iv.setImageResource(R.drawable.region_sejong_tab)
                }
                "대전" -> {
                    legislator_frag_top_iv.setImageResource(R.drawable.region_daejeon_tab)
                }
                "경북" -> {
                    legislator_frag_top_iv.setImageResource(R.drawable.region_gyeongbug_tab)
                }
                "대구" -> {
                    legislator_frag_top_iv.setImageResource(R.drawable.region_daegu_tab)
                }
                "울산" -> {
                    legislator_frag_top_iv.setImageResource(R.drawable.region_ulsan_tab)
                }
                "부산" -> {
                    legislator_frag_top_iv.setImageResource(R.drawable.region_busan_tab)
                }
                "전북" -> {
                    legislator_frag_top_iv.setImageResource(R.drawable.region_jeonbug_tab)
                }
                "광주" -> {
                    legislator_frag_top_iv.setImageResource(R.drawable.region_gwangju_tab)
                }
                "경남" -> {
                    legislator_frag_top_iv.setImageResource(R.drawable.region_gyeongnam_tab)
                }
                "전남" -> {
                    legislator_frag_top_iv.setImageResource(R.drawable.region_jeonnam_tab)
                }
                "제주" -> {
                    legislator_frag_top_iv.setImageResource(R.drawable.region_jeju_tab)
                }
            }
        }
    }

    private fun getPartyLegislatorLikableListResponse(){
        (context as LegislatorList).window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

        var p_name = (context as LegislatorList).getp_name()
        networkService = ApplicationController.instance.networkService
        val getPartyLegislatorListResponse = networkService.getPartyLegislatorListResponse(SharedPreferenceController.getMyId(this!!.context!!),1,p_name)
        getPartyLegislatorListResponse.enqueue(object : Callback<GetPartyDistrictLegislatorListResponse> {
            override fun onFailure(call: Call<GetPartyDistrictLegislatorListResponse>?, t: Throwable?) {
                (context as LegislatorList).window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

            }
            override fun onResponse(call: Call<GetPartyDistrictLegislatorListResponse>?, response: Response<GetPartyDistrictLegislatorListResponse>?) {
                var str = response!!.message()
                legislatorItems = response!!.body()!!.data as ArrayList<PartyDistrictLegistlatorListData>
                Log.v("success!",legislatorItems[0].party_name)
                if(!(str.equals("No data"))){
                    Log.v("success2", "통신2")
                    legislatorListAdapter = LegislatorListViewAdapter(context!!,legislatorItems,1)
                    rv_legislator.layoutManager = LinearLayoutManager(context)
                    rv_legislator.adapter = legislatorListAdapter
                }
                (context as LegislatorList).window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

            }
        })
    }

    private fun getDistrictLegislatorLikableListResponse(){
        (context as LegislatorList).window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

        var region_name = (context as LegislatorList).getregion_name()
        networkService = ApplicationController.instance.networkService
        val getDistrictLegislatorListResponse = networkService.getDistrictLegislatorListResponse(SharedPreferenceController.getMyId(this!!.context!!),1, region_name)
        getDistrictLegislatorListResponse.enqueue(object : Callback<GetPartyDistrictLegislatorListResponse>{
            override fun onFailure(call: Call<GetPartyDistrictLegislatorListResponse>?, t: Throwable?) {
                (context as LegislatorList).window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

            }
            override fun onResponse(call: Call<GetPartyDistrictLegislatorListResponse>?, response: Response<GetPartyDistrictLegislatorListResponse>?) {
                if (response!!.isSuccessful){
                    legislatorItems = response!!.body()!!.data as ArrayList<PartyDistrictLegistlatorListData>
                    setAdapter()
                }
                (context as LegislatorList).window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

            }
        })
    }
    fun setAdapter(){
        legislatorListAdapter = LegislatorListViewAdapter(context!!, legislatorItems, 1)
        rv_legislator.layoutManager = LinearLayoutManager(context)
        rv_legislator.adapter = legislatorListAdapter
    }
}