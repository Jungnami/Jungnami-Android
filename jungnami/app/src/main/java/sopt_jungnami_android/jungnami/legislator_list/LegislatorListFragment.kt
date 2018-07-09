package sopt_jungnami_android.jungnami.legislator_list

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_legislator_list.*
import kotlinx.android.synthetic.main.fragment_legislator_list.*
import org.jetbrains.anko.support.v4.startActivity

import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.mypage.MyPageActivity
import sopt_jungnami_android.jungnami.mypage.UserPageActivity

//made by Yunhwan
class LegislatorListFragment : Fragment() {
    var isSelectedPartyTab : Boolean = true


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_legislator_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addFragment()
        setClickListener()
    }
    private fun setClickListener(){
        //tab 이동 리스터
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
        //마이페이지로 이동
        legislator_list_frag_top_bar_mypage_btn.setOnClickListener {
            startActivity<MyPageActivity>()
        }
        //검색
        legislator_list_frag_top_bar_search_btn.setOnClickListener {
            legislator_list_frag_is_display_search_box_rl.visibility = View.VISIBLE
        }
        legislator_list_frag_is_display_blind_panel_rl.setOnClickListener {
            legislator_list_frag_is_display_search_box_rl.visibility = View.GONE
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
