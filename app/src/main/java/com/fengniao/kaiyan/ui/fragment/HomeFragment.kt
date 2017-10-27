package com.fengniao.kaiyan.ui.fragment


import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.fengniao.kaiyan.R
import com.fengniao.kaiyan.base.BaseFragment
import com.fengniao.kaiyan.bean.HomeBean
import com.fengniao.kaiyan.net.HttpClient
import com.fengniao.kaiyan.ui.adapter.HomeAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.regex.Pattern


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : BaseFragment() {
    private var date: String? = null
    private lateinit var mAdapter: HomeAdapter
    private val mList: MutableList<HomeBean.IssueListBean.ItemListBean> = mutableListOf()

    override fun initView() {
        mAdapter = HomeAdapter(context, mList)
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter = mAdapter
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE &&
                        recyclerView?.canScrollVertically(-1)!!) {
                    loadMore(date)
                }
            }
        })
        refresh_view.setOnRefreshListener {
            loadData()
        }
        loadData()
        refresh_view.isRefreshing = true
    }

    private fun loadData() {
        HttpClient.getInstance()
                .getHomeList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t: HomeBean? ->
                    if (refresh_view.isRefreshing) refresh_view.isRefreshing = false
                    if (t != null) {
                        handleData(t, true)
                    }
                })
    }


    private fun loadMore(date: String?) {
        if (date != null) {
            HttpClient.getInstance()
                    .getHomeMore(date, "2")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ t: HomeBean? ->
                        if (t != null) {
                            handleData(t, false)
                        }
                    })
        }
    }


    private fun handleData(homeBean: HomeBean, isClear: Boolean) {
        val regEx = "[^0-9]"
        val pattern: Pattern = Pattern.compile(regEx)
        val m = pattern.matcher(homeBean.nextPageUrl)
        date = m.replaceAll("").subSequence(1, m.replaceAll("").length - 1).toString()
        if (isClear)
            mList.clear()
        homeBean.issueList?.flatMap { it.itemList!! }
                ?.filter { it.type.equals("video") }
                ?.forEach { mList.add(it) }
        mAdapter.notifyDataSetChanged()
    }


    override fun setLayoutId(): Int = R.layout.fragment_home

}
