package sopt_jungnami_android.jungnami

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_legislator_party_list.*
import org.jetbrains.anko.support.v4.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sopt_jungnami_android.jungnami.Get.GetPartyDistrictLegislatorListResponse
import sopt_jungnami_android.jungnami.Network.ApplicationController
import sopt_jungnami_android.jungnami.Network.NetworkService
import sopt_jungnami_android.jungnami.data.PartyDistrictLegistlatorListData
import sopt_jungnami_android.jungnami.db.SharedPreferenceController
import sopt_jungnami_android.jungnami.legislator.LegislatorPageActivity

class LegislatorUnlikabletListFragment : Fragment() {

    lateinit var networkService: NetworkService
    lateinit var legislatorItems : ArrayList<PartyDistrictLegistlatorListData>
    lateinit var legislatorListViewAdapter: LegislatorListViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_legislator_party_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        networkService = ApplicationController.instance.networkService
        legislatorItems = ArrayList()
        setAdapter()
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
                    legislatorListViewAdapter = LegislatorListViewAdapter(context!!, legislatorItems, 0)
                    rv_legislator.layoutManager = LinearLayoutManager(context)
                    rv_legislator.adapter = legislatorListViewAdapter
                }
            }
        })
    }
    fun setAdapter(){
        legislatorListViewAdapter = LegislatorListViewAdapter(context!!,legislatorItems,1)
        rv_legislator.layoutManager = LinearLayoutManager(context)
        rv_legislator.adapter = legislatorListViewAdapter
    }
    private fun getDistrictLegislatorUnlikableListResponse(){
        var region_name = (context as LegislatorList).getregion_name()
        val getDistrictLegislatorListResponse = networkService.getDistrictLegislatorListResponse(SharedPreferenceController.getMyId(this!!.context!!),0, region_name)
        getDistrictLegislatorListResponse.enqueue(object : Callback<GetPartyDistrictLegislatorListResponse>{
            override fun onFailure(call: Call<GetPartyDistrictLegislatorListResponse>?, t: Throwable?) {
            }
            override fun onResponse(call: Call<GetPartyDistrictLegislatorListResponse>?, response: Response<GetPartyDistrictLegislatorListResponse>?) {
                if (response!!.isSuccessful){
                    legislatorItems = response!!.body()!!.data as ArrayList<PartyDistrictLegistlatorListData>
                    setAdapter()
                }
            }
        })
    }

}