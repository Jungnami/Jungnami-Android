package sopt_jungnami_android.jungnami.rank

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.activity_legislator_list.*
import kotlinx.android.synthetic.main.fragment_rank.*
import kotlinx.android.synthetic.main.tablayout_rank_fragment.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.startActivity
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.legislator_list.SearchActivity
import sopt_jungnami_android.jungnami.legislator_list.SearchPartyActivity
import sopt_jungnami_android.jungnami.legislator_list.SearchRigionActivity
import sopt_jungnami_android.jungnami.mypage.MyPageActivity

//made by yun hwan
class RankFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_rank, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //addFragment()
        setViewClickListener()
        configureRankTabMenu()
    }

    private fun setViewClickListener() {

        //상단바 마이페이지 이동
        rank_frag_top_bar_mypage_btn.setOnClickListener {
            startActivity<MyPageActivity>()
        }
        //상단바 검색창 없애기
        rank_frag_search_view_blind_box_rl.setOnClickListener {
            rank_frag_is_display_search_box_rl.visibility = View.GONE
            val imm: InputMethodManager = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
        rank_frag_top_bar_search_cancel_btn.setOnClickListener {
            rank_frag_is_display_search_box_rl.visibility = View.GONE
            val imm: InputMethodManager = context!!.getSystemService( Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }

        //상단바 검색 창 띄우기
        rank_frag_top_bar_search_btn.setOnClickListener {
            rank_frag_is_display_search_box_rl.visibility = View.VISIBLE
            rank_frag_top_bar_search_et.requestFocus()
            val imm: InputMethodManager = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(rank_frag_top_bar_search_et, InputMethodManager.SHOW_IMPLICIT)
            //imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)

        }
        rank_frag_top_bar_search_request_btn.setOnClickListener {
            var keyword: String = rank_frag_top_bar_search_et.text.toString()

            startActivity<SearchActivity>("keyword" to keyword)
        }

        rank_frag_top_bar_search_et.setOnKeyListener(object: View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if ((event!!.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    //Enter키눌렀을떄 처리
                    Log.v("TAG","눌렸다")
                    var keyword: String = rank_frag_top_bar_search_et.text.toString()

                    startActivity<SearchActivity>("keyword" to keyword)
                    return true;
                }
                return false;
            }
        })

    }


    private fun whereIsTab(position: Int) {
        if(position == 0){
            likeable_title_tv.setTextColor(Color.parseColor("#6B6B6B"))
            unlikeable_title_tv.setTextColor(Color.parseColor("#D8D8D8"))
        } else {
            likeable_title_tv.setTextColor(Color.parseColor("#D8D8D8"))
            unlikeable_title_tv.setTextColor(Color.parseColor("#6B6B6B"))
        }
    }


    fun configureRankTabMenu() {
        rank_frag_viewpager.adapter = RankTabPagerAdapter(2, childFragmentManager)
        rank_frag_tablayout.setupWithViewPager(rank_frag_viewpager)

        val headerView: View = (activity!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
                .inflate(R.layout.tablayout_rank_fragment, null, false)
        val likeable = headerView.findViewById(R.id.likeable_tab_btn) as RelativeLayout
        val unlikeable = headerView.findViewById(R.id.unlikeable_tab_btn) as RelativeLayout

        rank_frag_tablayout.getTabAt(0)!!.customView = likeable
        rank_frag_tablayout.getTabAt(1)!!.customView = unlikeable

        rank_frag_tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
                whereIsTab(tab!!.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                whereIsTab(tab!!.position)
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                whereIsTab(tab!!.position)
            }
        })
    }

}
