package sopt_jungnami_android.jungnami

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_legislator_party_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sopt_jungnami_android.jungnami.Get.GetPartyDistrictLegislatorListResponse
import sopt_jungnami_android.jungnami.Network.ApplicationController
import sopt_jungnami_android.jungnami.Network.NetworkService
import sopt_jungnami_android.jungnami.data.PartyDistrictLegistlatorListData
import sopt_jungnami_android.jungnami.db.SharedPreferenceController

class CutLegislatorDistrictListFragment : Fragment() {

    lateinit var networkService: NetworkService
    lateinit var legislatorItems : ArrayList<PartyDistrictLegistlatorListData>
    lateinit var partyDistrictLegislatorListViewAdapter: PartyDistrictLegislatorListViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_legislator_party_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        networkService = ApplicationController.instance.networkService
        var isParty = (context as LegislatorList).getisParty()
        if (isParty == true){
            getPartyLegislatorUnlikableListResponse()
        }else if (isParty == false){
            getDistrictLegislatorUnlikableListResponse()
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
                "seoul" -> {
                    legislator_frag_top_iv.setImageResource(R.drawable.party_blue_tab)
                }
                "incheon" -> {
                    legislator_frag_top_iv.setImageResource(R.drawable.party_blue_tab)
                }
                "gyeonggi" -> {
                    legislator_frag_top_iv.setImageResource(R.drawable.party_blue_tab)

                }
                "gangwon" -> {
                    legislator_frag_top_iv.setImageResource(R.drawable.party_blue_tab)

                }
                "chungbug" -> {
                    legislator_frag_top_iv.setImageResource(R.drawable.party_blue_tab)
                }
                "chungnam" -> {
                    legislator_frag_top_iv.setImageResource(R.drawable.party_blue_tab)
                }
                "sejong" -> {
                    legislator_frag_top_iv.setImageResource(R.drawable.party_blue_tab)
                }
                "daejeon" -> {
                    legislator_frag_top_iv.setImageResource(R.drawable.party_blue_tab)
                }
                "gyeongbug" -> {
                    legislator_frag_top_iv.setImageResource(R.drawable.party_blue_tab)
                }
                "daegu" -> {
                    legislator_frag_top_iv.setImageResource(R.drawable.party_blue_tab)
                }
                "ulsan" -> {
                    legislator_frag_top_iv.setImageResource(R.drawable.party_blue_tab)
                }
                "busan" -> {
                    legislator_frag_top_iv.setImageResource(R.drawable.party_blue_tab)
                }
                "jeonbug" -> {
                    legislator_frag_top_iv.setImageResource(R.drawable.party_blue_tab)
                }
                "gwangju" -> {
                    legislator_frag_top_iv.setImageResource(R.drawable.party_blue_tab)
                }
                "gyeongnam" -> {
                    legislator_frag_top_iv.setImageResource(R.drawable.party_blue_tab)
                }
                "jeonnam" -> {
                    legislator_frag_top_iv.setImageResource(R.drawable.party_blue_tab)
                }
                "jeju" -> {
                    legislator_frag_top_iv.setImageResource(R.drawable.party_blue_tab)
                }
            }
        }
    }

    private fun getPartyLegislatorUnlikableListResponse() {
        var p_name = (context as LegislatorList).getp_name()
        val getPartyLegislatorListResponse = networkService.getPartyLegislatorListResponse(SharedPreferenceController.getMyId(this!!.context!!), 0, p_name)
        getPartyLegislatorListResponse.enqueue(object : Callback<GetPartyDistrictLegislatorListResponse> {
            override fun onFailure(call: Call<GetPartyDistrictLegislatorListResponse>?, t: Throwable?) {
            }
            override fun onResponse(call: Call<GetPartyDistrictLegislatorListResponse>?, response: Response<GetPartyDistrictLegislatorListResponse>?) {
                var str = response!!.message()
                legislatorItems = response!!.body()!!.data as ArrayList<PartyDistrictLegistlatorListData>
                if (!(str.equals("No data"))) {
                    partyDistrictLegislatorListViewAdapter = PartyDistrictLegislatorListViewAdapter(context, legislatorItems, 0)
                    rv_legislator.layoutManager = LinearLayoutManager(context)
                    rv_legislator.adapter = partyDistrictLegislatorListViewAdapter
                }
            }
        })
    }

    private fun getDistrictLegislatorUnlikableListResponse(){
        var region_name = (context as LegislatorList).getregion_name()
        val getDistrictLegislatorListResponse = networkService.getDistrictLegislatorListResponse(SharedPreferenceController.getMyId(this!!.context!!),0, region_name)
        getDistrictLegislatorListResponse.enqueue(object : Callback<GetPartyDistrictLegislatorListResponse>{
            override fun onFailure(call: Call<GetPartyDistrictLegislatorListResponse>?, t: Throwable?) {
            }
            override fun onResponse(call: Call<GetPartyDistrictLegislatorListResponse>?, response: Response<GetPartyDistrictLegislatorListResponse>?) {
                var str = response!!.message()
                legislatorItems = response!!.body()!!.data as ArrayList<PartyDistrictLegistlatorListData>
                if(!(str.equals("No data"))){
                    partyDistrictLegislatorListViewAdapter = PartyDistrictLegislatorListViewAdapter(context, legislatorItems,0)
                    rv_legislator.layoutManager = LinearLayoutManager(context)
                    rv_legislator.adapter = partyDistrictLegislatorListViewAdapter
                }
            }
        })
    }

}