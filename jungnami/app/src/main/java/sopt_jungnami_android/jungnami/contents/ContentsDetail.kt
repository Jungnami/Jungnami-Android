package sopt_jungnami_android.jungnami.contents

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.View
import kotlinx.android.synthetic.main.activity_contents_detail.*
import org.jetbrains.anko.support.v4.onPageChangeListener
import org.jetbrains.anko.toast
import sopt_jungnami_android.jungnami.CommunityWritePage
import sopt_jungnami_android.jungnami.R

class ContentsDetail : AppCompatActivity() {
    lateinit var contentsDetailViewPagerAdapter: ContentsDetailViewPagerAdapter
    lateinit var contentsDetailItemData : ArrayList<Int>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contents_detail)
        setStatusBarColor()
        setClickListener()

        requestDataToServer()
        setViewPagerChangeListener()
        setViewPagerAdapter()

    }



    private fun requestDataToServer(){
        contentsDetailItemData = ArrayList()
        contentsDetailItemData.add(R.drawable.party_green_character)
        contentsDetailItemData.add(R.drawable.party_blue_character)
        contentsDetailItemData.add(R.drawable.party_indigo_character)
        contentsDetailItemData.add(R.drawable.party_red_character)
        contentsDetailItemData.add(R.drawable.party_yellow_character)

    }

    private fun setViewPagerAdapter(){
        contentsDetailViewPagerAdapter = ContentsDetailViewPagerAdapter(this,contentsDetailItemData)
        contents_detail_act_viewpager_vp.adapter = contentsDetailViewPagerAdapter

    }
    private fun setClickListener(){
        contents_act_detail_back_btn.setOnClickListener {
            finish()
        }
        contents_act_detail_like_btn.setOnClickListener {
            contents_act_detail_like_btn.isSelected = true
            // 좋아요++ 은 나중에
        }
        contents_act_detail_comment_btn.setOnClickListener {
            val intent = Intent(applicationContext, CommunityWritePage::class.java) // contents_comment 댓글 창 .kt 파일명
            startActivity(intent)
        }
        contents_act_detail_share_btn.setOnClickListener {

        }
        contents_act_detail_scrap_btn.setOnClickListener {
            contents_act_detail_scrap_btn.isSelected = true
        }
    }
    private fun setViewPagerChangeListener(){
        contents_detail_act_viewpager_vp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }
            override fun onPageSelected(position: Int) {
                contents_act_detail_image_count_tv.text = "${position+1}/${contentsDetailItemData.size}"
            }
        })
    }
    private fun setStatusBarColor() {
        val view : View? = window.decorView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (view != null){
                window.statusBarColor = Color.parseColor("#000000")
            }
        }
    }
}
