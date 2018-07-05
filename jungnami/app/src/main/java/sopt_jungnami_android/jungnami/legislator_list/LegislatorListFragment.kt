package sopt_jungnami_android.jungnami.legislator_list

import android.content.Context
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


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_legislator_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addFragment()
        legislator_list_frag_party_tab_btn.setOnClickListener {
            replaceFragment(PartyTab())
        }

        legislator_list_frag_region_tab_btn.setOnClickListener {
            replaceFragment(RegionTab())
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
