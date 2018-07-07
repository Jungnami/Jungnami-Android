package sopt_jungnami_android.jungnami.rank

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_rank.*
import sopt_jungnami_android.jungnami.R

class RankFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_rank, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addFragment()
        rank_frag_likeable_tab_btn.setOnClickListener {
            replaceFragment(LikeableTab())
        }
        rank_frag_unlikeable_tab_btn.setOnClickListener {
            replaceFragment(UnlikeableTab())
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
