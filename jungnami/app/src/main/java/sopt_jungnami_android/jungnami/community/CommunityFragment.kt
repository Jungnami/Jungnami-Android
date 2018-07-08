package sopt_jungnami_android.jungnami.community


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_community.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import sopt_jungnami_android.jungnami.CommunityWritePage

import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.mypage.MyPageActivity

class CommunityFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_community, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setClickedListener()

        community_frag_no_login_status_rl.visibility = View.GONE

    }
    private fun setClickedListener(){
        community_frag_top_bar_bell_btn.setOnClickListener {
            toast("종")
        }
        community_frag_top_bar_my_page_btn.setOnClickListener {
            startActivity<MyPageActivity>()
        }

        community_frag_write_feed_btn.setOnClickListener {
            startActivity<CommunityWritePage>()
        }

    }

}
