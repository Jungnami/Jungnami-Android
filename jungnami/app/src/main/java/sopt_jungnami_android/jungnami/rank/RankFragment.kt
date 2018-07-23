package sopt_jungnami_android.jungnami.rank

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.fragment_rank.*
import org.jetbrains.anko.support.v4.startActivity
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.legislator_list.SearchActivity
import sopt_jungnami_android.jungnami.mypage.MyPageActivity

//made by yun hwan
class RankFragment : Fragment() {
    var isSelectedLikeableTab: Boolean = true


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_rank, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addFragment()
        setViewClickListener()

        //configureRankTabMenu()

    }

    private fun setViewClickListener() {
        //탭 이동 리스터
        rank_frag_likeable_tab_btn.setOnClickListener {
            isSelectedLikeableTab = true
            replaceFragment(LikeableTab())
            checkSelectedTabView()
        }
        rank_frag_unlikeable_tab_btn.setOnClickListener {
            isSelectedLikeableTab = false
            replaceFragment(UnlikeableTab())
            checkSelectedTabView()
        }
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
            val imm: InputMethodManager = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }

        //상단바 검색 창 띄우기
        rank_frag_top_bar_search_btn.setOnClickListener {
            rank_frag_is_display_search_box_rl.visibility = View.VISIBLE
            rank_frag_top_bar_search_et.requestFocus()
            val imm: InputMethodManager = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(rank_frag_top_bar_search_et,InputMethodManager.SHOW_IMPLICIT)
            //imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)

        }
        rank_frag_top_bar_search_request_btn.setOnClickListener {
            var keyword : String = rank_frag_top_bar_search_et.text.toString()

            startActivity<SearchActivity>("keyword" to keyword)
        }

    }

    private fun checkSelectedTabView() {
        if (isSelectedLikeableTab) {
            rank_frag_likeable_title_tv.setTextColor(Color.parseColor("#6B6B6B"))
            rank_frag_likeable_underbar_line.visibility = View.VISIBLE

            rank_frag_unlikeable_title_tv.setTextColor(Color.parseColor("#D8D8D8"))
            rank_frag_unlikeable_underbar_line.visibility = View.INVISIBLE
        } else {
            rank_frag_likeable_title_tv.setTextColor(Color.parseColor("#D8D8D8"))
            rank_frag_likeable_underbar_line.visibility = View.INVISIBLE

            rank_frag_unlikeable_title_tv.setTextColor(Color.parseColor("#6B6B6B"))
            rank_frag_unlikeable_underbar_line.visibility = View.VISIBLE
        }
    }

    private fun addFragment() {
        val transaction = childFragmentManager.beginTransaction()
        transaction.add(R.id.rank_frag_fragment_frame_fl, LikeableTab()).commit()
    }

    fun replaceFragment(fragment: Fragment) {
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.rank_frag_fragment_frame_fl, fragment).commit()
    }
    fun connectionLikeableTab(){
        val fragment = childFragmentManager.findFragmentById(R.id.rank_frag_fragment_frame_fl)
        (fragment as LikeableTab).getRankItemDataAtServer()
    }
    fun connectionUnLikeableTab(){
        val fragment = childFragmentManager.findFragmentById(R.id.rank_frag_fragment_frame_fl)
        (fragment as UnlikeableTab).getRankItemDataAtServer()
    }
//    fun configureRankTabMenu(){
//        rank_frag_viewpager.adapter = RankTabPagerAdapter(2, childFragmentManager)
//        rank_frag_tablayout.setupWithViewPager(rank_frag_viewpager)
//
//        val headerView : View = (activity!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
//                .inflate(R.layout.tablayout_rank_fragment, null, false)
//        val likeable = headerView.findViewById(R.id.likeable_tab_btn) as RelativeLayout
//        val unlikeable = headerView.findViewById(R.id.unlikeable_tab_btn) as RelativeLayout
//
//        rank_frag_tablayout.getTabAt(0)!!.customView = likeable
//        rank_frag_tablayout.getTabAt(1)!!.customView = unlikeable
//
//        rank_frag_tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
//            override fun onTabReselected(tab: TabLayout.Tab?) {
//
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) {
//            }
//
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//            }
//        })
//    }

}
