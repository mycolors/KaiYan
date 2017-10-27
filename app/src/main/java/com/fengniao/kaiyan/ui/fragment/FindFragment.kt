package com.fengniao.kaiyan.ui.fragment


import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import com.fengniao.kaiyan.R
import com.fengniao.kaiyan.base.BaseFragment
import com.fengniao.kaiyan.bean.FindBean
import com.fengniao.kaiyan.net.HttpClient
import com.fengniao.kaiyan.ui.adapter.FindAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_find.*


/**
 * A simple [Fragment] subclass.
 */
class FindFragment : BaseFragment() {
    val mList: MutableList<FindBean> = mutableListOf()
    var mAdapter: FindAdapter? = null

    override fun initView() {
        recycler_view.layoutManager = GridLayoutManager(context, 2)
        mAdapter = FindAdapter(context, mList)
        recycler_view.adapter = mAdapter
        loadData()
    }

    override fun setLayoutId(): Int = R.layout.fragment_find


    private fun loadData() {
        HttpClient.getInstance()
                .getFindData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t: MutableList<FindBean>? ->
                    mList.addAll(t!!)
                    mAdapter?.notifyDataSetChanged()
                })

    }


}
