package sopt_jungnami_android.jungnami.legislator_list

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_region_tab.*
import org.jetbrains.anko.support.v4.startActivity
import sopt_jungnami_android.jungnami.LegislatorList
import sopt_jungnami_android.jungnami.R

class RegionTab : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_region_tab, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setClickListener()
    }


    private fun setClickListener(){
        resion_tab_map_seoul_btn.setOnClickListener {
            startActivity<LegislatorList>("isParty" to false, "region_name" to "서울", "party_name" to "trash value")
        }
        resion_tab_map_incheon_btn.setOnClickListener {
            startActivity<LegislatorList>("isParty" to false, "region_name" to "인천", "party_name" to "trash value")
        }
        resion_tab_map_gyeonggi_btn.setOnClickListener {
            startActivity<LegislatorList>("isParty" to false, "region_name" to "경기", "party_name" to "trash value")
        }
        resion_tab_map_gangwon_btn.setOnClickListener {
            startActivity<LegislatorList>("isParty" to false, "region_name" to "강원", "party_name" to "trash value")
        }
        resion_tab_map_chungbug_btn.setOnClickListener {
            startActivity<LegislatorList>("isParty" to false, "region_name" to "충북", "party_name" to "trash value")
        }
        resion_tab_map_chungnam_btn.setOnClickListener {
            startActivity<LegislatorList>("isParty" to false, "region_name" to "충남", "party_name" to "trash value")
        }
        resion_tab_map_sejong_btn.setOnClickListener {
            startActivity<LegislatorList>("isParty" to false, "region_name" to "세종", "party_name" to "trash value")
        }
        resion_tab_map_daejeon_btn.setOnClickListener {
            startActivity<LegislatorList>("isParty" to false, "region_name" to "대전", "party_name" to "trash value")
        }
        resion_tab_map_gyeongbug_btn.setOnClickListener {
            startActivity<LegislatorList>("isParty" to false, "region_name" to "경북", "party_name" to "trash value")
        }
        resion_tab_map_daegu_btn.setOnClickListener {
            startActivity<LegislatorList>("isParty" to false, "region_name" to "대구", "party_name" to "trash value")
        }
        resion_tab_map_ulsan_btn.setOnClickListener {
            startActivity<LegislatorList>("isParty" to false, "region_name" to "울산", "party_name" to "trash value")
        }
        resion_tab_map_busan_btn.setOnClickListener {
            startActivity<LegislatorList>("isParty" to false, "region_name" to "부산", "party_name" to "trash value")
        }
        resion_tab_map_jeonbug_btn.setOnClickListener {
            startActivity<LegislatorList>("isParty" to false, "region_name" to "전북", "party_name" to "trash value")
        }
        resion_tab_map_gwangju_btn.setOnClickListener {
            startActivity<LegislatorList>("isParty" to false, "region_name" to "광주", "party_name" to "trash value")
        }
        resion_tab_map_gyeongnam_btn.setOnClickListener {
            startActivity<LegislatorList>("isParty" to false, "region_name" to "경남", "party_name" to "trash value")
        }
        resion_tab_map_jeonnam_btn.setOnClickListener {
            startActivity<LegislatorList>("isParty" to false, "region_name" to "전남", "party_name" to "trash value")
        }
        resion_tab_map_jeju_btn.setOnClickListener {
            startActivity<LegislatorList>("isParty" to false, "region_name" to "제주", "party_name" to "trash value")
        }
    }
}
