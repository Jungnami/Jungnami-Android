package sopt_jungnami_android.jungnami.rank

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_legislator_list.*
import kotlinx.android.synthetic.main.fragment_rank.*
import sopt_jungnami_android.jungnami.R

class RankFragment : Fragment() {
    var isSelectedLikeableTab : Boolean = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_rank, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addFragment()
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
