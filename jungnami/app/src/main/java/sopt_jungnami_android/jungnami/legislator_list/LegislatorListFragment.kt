package sopt_jungnami_android.jungnami.legislator_list

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_legislator_list.*

import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.rank.LikeableTab

class LegislatorListFragment : Fragment() {
    var isSelectedPartyTab : Boolean = true


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_legislator_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addFragment()
        legislator_list_frag_party_tab_btn.setOnClickListener {
            isSelectedPartyTab = true
            replaceFragment(PartyTab())
            checkSelectedTabView()
        }

        legislator_list_frag_region_tab_btn.setOnClickListener {
            isSelectedPartyTab = false
            replaceFragment(RegionTab())
            checkSelectedTabView()
        }
    }
    private fun checkSelectedTabView(){
        if (isSelectedPartyTab){
            legislator_list_frag_party_title_tv.setTextColor(Color.parseColor("#6B6B6B"))
            legislator_list_frag_party_underbar_line.visibility = View.VISIBLE

            legislator_list_frag_region_title_tv.setTextColor(Color.parseColor("#D8D8D8"))
            legislator_list_frag_region_underbar_line.visibility = View.INVISIBLE
        } else {
            legislator_list_frag_party_title_tv.setTextColor(Color.parseColor("#D8D8D8"))
            legislator_list_frag_party_underbar_line.visibility = View.INVISIBLE

            legislator_list_frag_region_title_tv.setTextColor(Color.parseColor("#6B6B6B"))
            legislator_list_frag_region_underbar_line.visibility = View.VISIBLE
        }
    }

    private fun addFragment(){
        val transaction = childFragmentManager.beginTransaction()
        transaction.add(R.id.legislator_list_frag_fragment_fl, PartyTab()).commit()
    }
    private fun replaceFragment(fragment: Fragment){
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.legislator_list_frag_fragment_fl, fragment).commit()
    }
}
