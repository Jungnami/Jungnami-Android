package sopt_jungnami_android.jungnami.rank

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_legislator_list.*
import kotlinx.android.synthetic.main.fragment_rank.*
import org.jetbrains.anko.support.v4.startActivity
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.mypage.MyPageActivity

class RankFragment : Fragment() {
    var isSelectedLikeableTab : Boolean = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        activity!!.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        return inflater.inflate(R.layout.fragment_rank, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addFragment()
        setViewClickListener()

    }

    private fun setViewClickListener(){
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
        }
        rank_frag_top_bar_search_cancel_btn.setOnClickListener {
            rank_frag_is_display_search_box_rl.visibility = View.GONE
        }
        //상단바 검색 창 띄우기
        rank_frag_top_bar_search_btn.setOnClickListener {
            rank_frag_is_display_search_box_rl.visibility = View.VISIBLE
            rank_frag_top_bar_search_et.requestFocus()
            //activity!!.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        }
    }

    private fun checkSelectedTabView(){
        if (isSelectedLikeableTab){
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

    private fun addFragment(){
        val transaction = childFragmentManager.beginTransaction()
        transaction.add(R.id.rank_frag_fragment_frame_fl, LikeableTab()).commit()
    }
    private fun replaceFragment(fragment: Fragment){
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.rank_frag_fragment_frame_fl, fragment).commit()
    }

}
