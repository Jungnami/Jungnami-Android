package sopt_jungnami_android.jungnami.coinpage

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class CoinPageTabPagerAdapter(val tabCount : Int, fm : FragmentManager) : FragmentStatePagerAdapter(fm){
    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> return CoinChargeFragment()
            else -> return VoteChargeFragment()
        }
    }

    override fun getCount(): Int = tabCount
}