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
            startActivity<LegislatorList>("isParty" to false, "region_name" to "seoul")
        }
        resion_tab_map_incheon_btn.setOnClickListener {
            startActivity<LegislatorList>("isParty" to false, "region_name" to "incheon")
        }
        resion_tab_map_gyeonggi_btn.setOnClickListener {
            startActivity<LegislatorList>("isParty" to false, "region_name" to "gyeonggi")
        }
        resion_tab_map_gangwon_btn.setOnClickListener {
            startActivity<LegislatorList>("isParty" to false, "region_name" to "gangwon")
        }
        resion_tab_map_chungbug_btn.setOnClickListener {
            startActivity<LegislatorList>("isParty" to false, "region_name" to "chungbug")
        }
        resion_tab_map_chungnam_btn.setOnClickListener {
            startActivity<LegislatorList>("isParty" to false, "region_name" to "chungnam")
        }
        resion_tab_map_sejong_btn.setOnClickListener {
            startActivity<LegislatorList>("isParty" to false, "region_name" to "sejong")
        }
        resion_tab_map_daejeon_btn.setOnClickListener {
            startActivity<LegislatorList>("isParty" to false, "region_name" to "daejeon")
        }
        resion_tab_map_gyeongbug_btn.setOnClickListener {
            startActivity<LegislatorList>("isParty" to false, "region_name" to "gyeongbug")
        }
        resion_tab_map_daegu_btn.setOnClickListener {
            startActivity<LegislatorList>("isParty" to false, "region_name" to "daegu")
        }
        resion_tab_map_ulsan_btn.setOnClickListener {
            startActivity<LegislatorList>("isParty" to false, "region_name" to "ulsan")
        }
        resion_tab_map_busan_btn.setOnClickListener {
            startActivity<LegislatorList>("isParty" to false, "region_name" to "busan")
        }
        resion_tab_map_jeonbug_btn.setOnClickListener {
            startActivity<LegislatorList>("isParty" to false, "region_name" to "jeonbug")
        }
        resion_tab_map_gwangju_btn.setOnClickListener {
            startActivity<LegislatorList>("isParty" to false, "region_name" to "gwangju")
        }
        resion_tab_map_gyeongnam_btn.setOnClickListener {
            startActivity<LegislatorList>("isParty" to false, "region_name" to "gyeongnam")
        }
        resion_tab_map_jeonnam_btn.setOnClickListener {
            startActivity<LegislatorList>("isParty" to false, "region_name" to "jeonnam")
        }
        resion_tab_map_jeju_btn.setOnClickListener {
            startActivity<LegislatorList>("isParty" to false, "region_name" to "jeju")
        }
    }
}
