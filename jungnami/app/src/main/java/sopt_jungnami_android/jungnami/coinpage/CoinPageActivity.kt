package sopt_jungnami_android.jungnami.coinpage

import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import kotlinx.android.synthetic.main.activity_coin_page.*
import sopt_jungnami_android.jungnami.R
//made by YunHwan
class CoinPageActivity : AppCompatActivity() {
    var isCoinChargeSelected : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_page)
        setStatusBarColor()
        setInitTab(intent.getStringExtra("target"))
        checkSelectedTabView()
        setClickListener()
    }

    private fun setInitTab(targetTabName : String){
        when(targetTabName){
            "coin" -> {
                isCoinChargeSelected = true
                addFragment(CoinChargeFragment())
            }
            "vote" -> {
                isCoinChargeSelected = false
                addFragment(VoteChargeFragment())
            }
        }
    }
    private fun setClickListener(){
        coinpage_act_coin_charge_tab_btn.setOnClickListener {
            isCoinChargeSelected = true
            checkSelectedTabView()
            replaceFragment(CoinChargeFragment())
        }
        coinpage_act_vote_charge_tab_btn.setOnClickListener {
            isCoinChargeSelected = false
            checkSelectedTabView()
            replaceFragment(VoteChargeFragment())
        }
        coinpage_act_cancel_btn.setOnClickListener {
            finish()
        }
    }
    private fun checkSelectedTabView(){
        if (isCoinChargeSelected){
            coinpage_act_coin_charge_title_tv.setTextColor(Color.parseColor("#6B6B6B"))
            coinpage_act_coin_charge_underbar_line.visibility = View.VISIBLE
            coinpage_act_vote_charge_title_tv.setTextColor(Color.parseColor("#D8D8D8"))
            coinpage_act_vote_charge_underbar_line.visibility = View.INVISIBLE
        } else {
            coinpage_act_coin_charge_title_tv.setTextColor(Color.parseColor("#D8D8D8"))
            coinpage_act_coin_charge_underbar_line.visibility = View.INVISIBLE
            coinpage_act_vote_charge_title_tv.setTextColor(Color.parseColor("#6B6B6B"))
            coinpage_act_vote_charge_underbar_line.visibility = View.VISIBLE
        }
    }

    fun refreshCoinPage(){
        replaceFragment(CoinChargeFragment())
    }
    fun refreshVotePage(){
        replaceFragment(VoteChargeFragment())
    }


    private fun addFragment(fragment: Fragment){
        val fm = supportFragmentManager
        val transaction = fm.beginTransaction()
        transaction.add(R.id.coinpage_act_fragment_frame_fl, fragment)
        transaction.commit()
    }

    private fun replaceFragment(fragment: Fragment){
        val fm = supportFragmentManager
        val transaction = fm.beginTransaction()
        transaction.replace(R.id.coinpage_act_fragment_frame_fl, fragment)
        transaction.commit()
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
