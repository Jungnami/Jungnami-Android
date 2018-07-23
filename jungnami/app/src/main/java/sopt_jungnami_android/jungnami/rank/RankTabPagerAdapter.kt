package sopt_jungnami_android.jungnami.rank

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class RankTabPagerAdapter(val tabCount : Int, fm : FragmentManager) : FragmentStatePagerAdapter(fm){


    override fun getItem(position: Int): Fragment? {
        return when (position){
            0 -> LikeableTab()
            1 -> UnlikeableTab()
            else -> null
        }
    }

    override fun getCount(): Int = tabCount
}