package com.fengniao.kaiyan.ui.activity

import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.fengniao.kaiyan.R
import com.fengniao.kaiyan.base.BaseActivity
import com.fengniao.kaiyan.ui.fragment.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : BaseActivity(), View.OnClickListener {


    var homeFragment: HomeFragment? = null
    var findFragment: FindFragment? = null
    var hotFragment: HotFragment? = null
    var mineFragment: MineFragment? = null
    var currentFragment: Fragment? = null
    lateinit var searchFragment: SearchFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        searchFragment = SearchFragment()
    }

    private fun initView() {
        tv_title.typeface = Typeface.createFromAsset(assets, "fonts/Lobster-1.4.otf")
        initBtn()
        initFragment()
        selectTab(0, homeFragment!!)
    }

    private fun initFragment() {
        if (homeFragment == null)
            homeFragment = supportFragmentManager.findFragmentByTag("home") as HomeFragment?
        if (findFragment == null)
            findFragment = supportFragmentManager.findFragmentByTag("find") as FindFragment?
        if (hotFragment == null)
            hotFragment = supportFragmentManager.findFragmentByTag("hot") as HotFragment?
        if (mineFragment == null)
            mineFragment = supportFragmentManager.findFragmentByTag("mine") as MineFragment?
        if (homeFragment == null)
            homeFragment = HomeFragment()
    }

    private fun showFragment(fragment: Fragment, tag: String) {
        if (currentFragment!!.isVisible) {
            supportFragmentManager.beginTransaction().hide(currentFragment).commit()
        }
        currentFragment = fragment
        if (fragment.isAdded) {
            supportFragmentManager.beginTransaction().show(fragment).commit()
        } else {
            supportFragmentManager.beginTransaction().add(R.id.content, fragment, tag)
                    .show(fragment).commit()
        }
    }

    private fun initBtn() {
        rb_home.setOnClickListener(this)
        rb_find.setOnClickListener(this)
        rb_hot.setOnClickListener(this)
        rb_mine.setOnClickListener(this)
        iv_search.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.rb_home -> {
                if (homeFragment == null)
                    homeFragment = HomeFragment()
                selectTab(0, homeFragment!!)
            }
            R.id.rb_find -> {
                if (findFragment == null)
                    findFragment = FindFragment()
                selectTab(1, findFragment!!)
            }
            R.id.rb_hot -> {
                if (hotFragment == null)
                    hotFragment = HotFragment()
                selectTab(2, hotFragment!!)
            }
            R.id.rb_mine -> {
                if (mineFragment == null)
                    mineFragment = MineFragment()
                selectTab(3, mineFragment!!)
            }
            R.id.iv_search -> {
                searchFragment.show(supportFragmentManager,"SearchFragment")
            }
        }
    }

    private fun selectTab(position: Int, fragment: Fragment) {
        if (currentFragment == fragment) return
        if (currentFragment == null)
            currentFragment = fragment
        rb_home.isSelected = false
        rb_find.isSelected = false
        rb_hot.isSelected = false
        rb_mine.isSelected = false
        var tag: String = ""
        when (position) {
            0 -> {
                tag = "home"
                rb_home.isSelected = true
                tv_title.text = getToday()
            }
            1 -> {
                tag = "find"
                rb_find.isSelected = true
                tv_title.text = "Discover"
            }
            2 -> {
                tag = "hot"
                rb_hot.isSelected = true
                tv_title.text = "Ranking"
            }
            3 -> {
                tag = "mine"
                rb_mine.isSelected = true
                tv_title.text = ""
            }
        }
        showFragment(fragment, tag)
    }

    fun getToday(): String {
        val list = arrayOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
        val data: Date = Date()
        val calendar: Calendar = Calendar.getInstance()
        calendar.time = data
        var index: Int = calendar.get(Calendar.DAY_OF_WEEK) - 1
        if (index < 0) {
            index = 0
        }
        return list[index]
    }


}
