package sopt_jungnami_android.jungnami.legislator_list

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_party_tab.*
import org.jetbrains.anko.support.v4.startActivity
import sopt_jungnami_android.jungnami.LegislatorList
import sopt_jungnami_android.jungnami.R

class PartyTab : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_party_tab, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setClickListener()
    }

    private fun setClickListener(){
        party_tab_blue_btn.setOnClickListener {
            startActivity<LegislatorList>("isParty" to true, "party_name" to "더불어민주당", "region_name" to "trashValue")
        }
        party_tab_red_btn.setOnClickListener {
            startActivity<LegislatorList>("isParty" to true, "party_name" to "자유한국당", "region_name" to "trashValue")
        }
        party_tab_mint_btn.setOnClickListener {
            startActivity<LegislatorList>("isParty" to true, "party_name" to "바른미래당", "region_name" to "trashValue")
        }
        party_tab_yellow_btn.setOnClickListener {
            startActivity<LegislatorList>("isParty" to true, "party_name" to "정의당", "region_name" to "trashValue")
        }
        party_tab_orange_btn.setOnClickListener {
            startActivity<LegislatorList>("isParty" to true, "party_name" to "민중당", "region_name" to "trashValue")
        }
        party_tab_indigo_btn.setOnClickListener {
            startActivity<LegislatorList>("isParty" to true, "party_name" to "대한애국당", "region_name" to "trashValue")
        }
        party_tab_green_btn.setOnClickListener {
            startActivity<LegislatorList>("isParty" to true, "party_name" to "민주평화당", "region_name" to "trashValue")
        }
        party_tab_gray_btn.setOnClickListener {
            startActivity<LegislatorList>("isParty" to true, "party_name" to "무소속", "region_name" to "trashValue")
        }
    }
}
