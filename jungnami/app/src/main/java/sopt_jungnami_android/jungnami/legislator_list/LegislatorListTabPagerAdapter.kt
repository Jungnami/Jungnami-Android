package sopt_jungnami_android.jungnami.legislator_list

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class LegislatorListTabPagerAdapter(val tabCount : Int, val fm : FragmentManager) : FragmentStatePagerAdapter(fm){
    override fun getItem(position: Int): Fragment? {
        when(position){
            0 -> return PartyTab()
            1 -> return RegionTab()
            else -> return null
        }
    }

    override fun getCount(): Int = tabCount
}