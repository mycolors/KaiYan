package com.fengniao.kaiyan.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.fengniao.kaiyan.ui.fragment.RankFragment

class HotAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    val titles: Array<String> = arrayOf("周排行", "月排行", "总排行")

    override fun getItem(position: Int): Fragment =
            when (position) {
                0 -> RankFragment.newInstance("weekly")
                1 -> RankFragment.newInstance("monthly")
                else -> RankFragment.newInstance("historical")
            }

    override fun getCount(): Int = titles.size

    override fun getPageTitle(position: Int): CharSequence {
        return titles[position]
    }
}