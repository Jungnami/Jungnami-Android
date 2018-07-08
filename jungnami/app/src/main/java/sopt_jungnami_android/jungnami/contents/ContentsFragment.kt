package sopt_jungnami_android.jungnami.contents

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_contents.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import sopt_jungnami_android.jungnami.Alarm
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.data.ContentItemData
import sopt_jungnami_android.jungnami.mypage.MyPageActivity


class ContentsFragment : Fragment() {

//    made by Yunhwan

    var current_tab_idx : Int = 0
    lateinit var contentsRecyclerViewAdapter: ContentsRecyclerViewAdapter
    lateinit var contentsDataList : ArrayList<ContentItemData>
    

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_contents, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setClickListener()
        //요청
        requestDataToServer()
        //메인 컨텐츠
        setMainContentView()
        //서브 컨텐츠
        changeRecyclerViewData()


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
        //메인 컨텐츠 클릭 리스터
        contents_frag_main_content_lr.setOnClickListener {
            startActivity<ContentsDetail>()
        }

    }

    private fun setMainContentView(){
        val mainContent = contentsDataList[0]
        contentsDataList.removeAt(0)

        contents_frag_main_content_title_tv.text = mainContent.title
        contents_frag_main_content_info_tv.text = mainContent.category
//        contents_frag_main_content_image_iv.setImageResource()
    }

    private fun requestDataToServer(){
        contentsDataList = ArrayList()
        contentsDataList.add(ContentItemData("국회의원 아들과 폐지 줍는 부모님???", "image", "스토리"))
        contentsDataList.add(ContentItemData("문재인 대통령의\n살아온 일대기와 운명", "image", "스토리"))
        contentsDataList.add(ContentItemData("이재명 시장,\n청와대 실세와 오붓한 시간", "image", "TMI"))
        contentsDataList.add(ContentItemData("장제원 의원\n아들 인성 논란", "image", "TMI"))
    }

    private fun changeRecyclerViewData(){
        contentsRecyclerViewAdapter = ContentsRecyclerViewAdapter(context!!, contentsDataList)
        contents_frag_sub_contents_recycler_rv.layoutManager = GridLayoutManager(context!!,2)
        contents_frag_sub_contents_recycler_rv.adapter = contentsRecyclerViewAdapter
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
