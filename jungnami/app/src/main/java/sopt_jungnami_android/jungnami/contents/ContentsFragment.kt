package sopt_jungnami_android.jungnami.contents

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_contents.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import sopt_jungnami_android.jungnami.Alarm
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.mypage.MyPageActivity


class ContentsFragment : Fragment() {

//    made by Yunhwan

    var current_tab_idx : Int = 0
    

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_contents, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setClickListener()
    }
    private fun setClickListener(){
        contents_frag_top_bar_my_page_btn.setOnClickListener {
            startActivity<MyPageActivity>()
        }
        contents_frag_top_bar_bell_btn.setOnClickListener {
            startActivity<Alarm>()
        }
        //Tab 클릭 리스터
        contents_frag_recommend_btn.setOnClickListener {
            checkSelectedTabView(0)
        }
        contents_frag_tmi_btn.setOnClickListener {
            checkSelectedTabView(1)
        }
        contents_frag_story_btn.setOnClickListener {
            checkSelectedTabView(2)
        }

    }

    private fun requestDataToServer(){

    }

    private fun changeRecyclerViewData(){

    }


    private fun changeNonSelectedTabView(idx : Int){
        when (idx) {
            0 -> {
                contents_frag_recommend_tv.setTextColor(Color.parseColor("#D8D8D8"))
                contents_frag_recommend_underline.visibility = View.INVISIBLE
            }
            1 -> {
                contents_frag_tmi_tv.setTextColor(Color.parseColor("#D8D8D8"))
                contents_frag_tmi_underline.visibility = View.INVISIBLE
            }
            2 -> {
                contents_frag_story_tv.setTextColor(Color.parseColor("#D8D8D8"))
                contents_frag_story_underline.visibility = View.INVISIBLE
            }
        }
    }

    private fun changeSelectedTabView(idx : Int){
        when (idx) {
            0 -> {
                contents_frag_recommend_tv.setTextColor(Color.parseColor("#36C5F1"))
                contents_frag_recommend_underline.visibility = View.VISIBLE
            }
            1 -> {
                contents_frag_tmi_tv.setTextColor(Color.parseColor("#36C5F1"))
                contents_frag_tmi_underline.visibility = View.VISIBLE
            }
            2 -> {
                contents_frag_story_tv.setTextColor(Color.parseColor("#36C5F1"))
                contents_frag_story_underline.visibility = View.VISIBLE
            }
        }
    }


    private fun checkSelectedTabView(selected_tab_idx : Int){
        when (selected_tab_idx){
            0 -> {
                changeNonSelectedTabView(current_tab_idx)
                current_tab_idx = 0
                changeSelectedTabView(current_tab_idx)
            }
            1 -> {
                changeNonSelectedTabView(current_tab_idx)
                current_tab_idx = 1
                changeSelectedTabView(current_tab_idx)
            }
            2 -> {
                changeNonSelectedTabView(current_tab_idx)
                current_tab_idx = 2
                changeSelectedTabView(current_tab_idx)
            }
        }
    }
}
