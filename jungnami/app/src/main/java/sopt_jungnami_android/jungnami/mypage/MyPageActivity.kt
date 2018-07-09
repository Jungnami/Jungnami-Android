package sopt_jungnami_android.jungnami.mypage

import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_my_page.*
import org.jetbrains.anko.startActivity
import sopt_jungnami_android.jungnami.Alarm
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.coinpage.CoinPageActivity
import sopt_jungnami_android.jungnami.data.ContentItemData

class MyPageActivity : AppCompatActivity() {
    var isSelectScrap : Boolean = true
    lateinit var myPageRecyclerViewAdapter: MyPageRecyclerViewAdapter
    lateinit var contentDataList : ArrayList<ContentItemData>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)
        setStatusBarColor()

        setClickListener()

        requestDataToServer()
        setRecyclerViewAdapter()
    }

    private fun requestDataToServer(){
        contentDataList = ArrayList()
        contentDataList.add(ContentItemData("국회의원 아들과 폐지 줍는 부모님???", "image", "스토리"))
        contentDataList.add(ContentItemData("문재인 대통령의\n살아온 일대기와 운명", "image", "스토리"))
        contentDataList.add(ContentItemData("이재명 시장,\n청와대 실세와 오붓한 시간", "image", "TMI"))
        contentDataList.add(ContentItemData("장제원 의원\n아들 인성 논란", "image", "TMI"))

    }

    private fun setRecyclerViewAdapter(){
        myPageRecyclerViewAdapter = MyPageRecyclerViewAdapter(this, dataList = contentDataList)
        mypage_act_recyclerview_list_rv.layoutManager = GridLayoutManager(this, 2)
        mypage_act_recyclerview_list_rv.adapter = myPageRecyclerViewAdapter
    }

    private fun setClickListener(){
        mypage_act_mycoin_check_btn.setOnClickListener {
            startActivity<CoinPageActivity>("target" to "coin")
        }
        mypage_act_myvote_check_btn.setOnClickListener {
            startActivity<CoinPageActivity>("target" to "vote")
        }
        mypage_act_top_bar_back_btn.setOnClickListener {
            finish()
        }
        mypage_act_top_bar_bell_btn.setOnClickListener {
            startActivity<Alarm>()
        }
        // tab 이동 관련
        mypage_act_scrap_tab_btn.setOnClickListener {
            isSelectScrap = true
            checkSelectedTabView()
        }
        mypage_act_feed_tab_btn.setOnClickListener {
            isSelectScrap = false
            checkSelectedTabView()
        }
    }
    private fun checkSelectedTabView(){
        if (isSelectScrap) {
            mypage_act_scrap_tab_btn.setTextColor(Color.parseColor("#36C5F1"))
            mypage_act_feed_tab_btn.setTextColor(Color.parseColor("#D6D6D6"))
        } else {
            mypage_act_scrap_tab_btn.setTextColor(Color.parseColor("#D6D6D6"))
            mypage_act_feed_tab_btn.setTextColor(Color.parseColor("#36C5F1"))
        }
    }

    private fun setStatusBarColor(){
        val view : View? = window.decorView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (view != null){
                view.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                window.statusBarColor = Color.parseColor("#FFFFFF")
            }
        }
    }
}
