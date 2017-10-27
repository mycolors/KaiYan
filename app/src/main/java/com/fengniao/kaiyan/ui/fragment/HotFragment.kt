package com.fengniao.kaiyan.ui.fragment


import android.support.v4.app.Fragment
import com.fengniao.kaiyan.R
import com.fengniao.kaiyan.base.BaseFragment
import com.fengniao.kaiyan.ui.adapter.HotAdapter
import kotlinx.android.synthetic.main.fragment_hot.*


/**
 * A simple [Fragment] subclass.
 */
class HotFragment : BaseFragment() {

    override fun setLayoutId(): Int = R.layout.fragment_hot

    override fun initView() {
        val adapter: HotAdapter = HotAdapter(childFragmentManager)
        view_pager.adapter = adapter
        tab_layout.setupWithViewPager(view_pager)
    }


}
