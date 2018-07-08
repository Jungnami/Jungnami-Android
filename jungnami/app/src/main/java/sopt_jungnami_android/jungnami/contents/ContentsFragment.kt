package sopt_jungnami_android.jungnami.contents

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_contents.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.mypage.MyPageActivity


class ContentsFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_contents, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        contents_frag_top_bar_my_page_btn.setOnClickListener {
            startActivity<MyPageActivity>()
        }

        contents_frag_top_bar_bell_btn.setOnClickListener {
            toast("땡땡땡")
        }
        contents_frag_recommend_btn.setOnClickListener {
            toast("추천")
        }
    }
}
