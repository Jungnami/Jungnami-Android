package sopt_jungnami_android.jungnami.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import sopt_jungnami_android.jungnami.community.CommunityFragment
import sopt_jungnami_android.jungnami.contents.ContentsFragment
import sopt_jungnami_android.jungnami.legislator_list.LegislatorListFragment
import sopt_jungnami_android.jungnami.rank.RankFragment

class MainTabAdapter(fm : FragmentManager) : FragmentStatePagerAdapter(fm){
    override fun getItem(position: Int): Fragment? {
        when (position){
            0-> return RankFragment()
            1-> return LegislatorListFragment()
            2-> return CommunityFragment()
            3-> return ContentsFragment()
            else-> return null
        }
    }

    override fun getCount(): Int = 4
}