package sopt_jungnami_android.jungnami.legislator_list

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.fragment_legislator_list.*
import kotlinx.android.synthetic.main.tablayout_legislator_list_frag.*
import org.jetbrains.anko.support.v4.startActivity
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.mypage.MyPageActivity

//made by Yunhwan
class LegislatorListFragment : Fragment() {
    var isSelectedPartyTab : Boolean = true
    var whereIsTab : Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_legislator_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addFragment()
        setClickListener()

        configureLegislatorListTabMenu()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

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
        legislator_list_frag_top_bar_search_tv.setOnClickListener {
            var keyword : String = legislator_list_frag_top_bar_search_et.text.toString()

            startActivity<SearchActivity>("keyword" to keyword)
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
    private fun checkWhereiIsTabView(){
        if (whereIsTab==0){
            legislator_list_tablayout_party_title_tv.setTextColor(Color.parseColor("#6B6B6B"))
            legislator_list_tablayout_region_title_tv.setTextColor(Color.parseColor("#D8D8D8"))
        } else {
            legislator_list_tablayout_party_title_tv.setTextColor(Color.parseColor("#D8D8D8"))
            legislator_list_tablayout_region_title_tv.setTextColor(Color.parseColor("#6B6B6B"))
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

    override fun onPause() {
        super.onPause()
    }


    fun configureLegislatorListTabMenu() {
        legislator_list_viewpager.adapter = LegislatorListTabPagerAdapter(2, childFragmentManager)
        legislator_list_tablayout.setupWithViewPager(legislator_list_viewpager)

        val headerView: View = (activity!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
                .inflate(R.layout.tablayout_legislator_list_frag, null, false)
        val party = headerView.findViewById(R.id.legislator_list_tablayout_party_tab_btn) as RelativeLayout
        val region = headerView.findViewById(R.id.legislator_list_tablayout_region_tab_btn) as RelativeLayout

        legislator_list_tablayout.getTabAt(0)!!.customView = party
        legislator_list_tablayout.getTabAt(1)!!.customView = region

        legislator_list_tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
                whereIsTab = tab!!.position
                checkWhereiIsTabView()
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
                whereIsTab = tab!!.position
                checkWhereiIsTabView()
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                whereIsTab = tab!!.position
                checkWhereiIsTabView()
            }
        })
    }
}
