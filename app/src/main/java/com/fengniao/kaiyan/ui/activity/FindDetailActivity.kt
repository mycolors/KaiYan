package com.fengniao.kaiyan.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.fengniao.kaiyan.R
import com.fengniao.kaiyan.base.BaseActivity
import com.fengniao.kaiyan.bean.HotBean
import com.fengniao.kaiyan.net.HttpClient
import com.fengniao.kaiyan.ui.adapter.RankAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.regex.Pattern

class FindDetailActivity : BaseActivity() {

    private var date: String? = null
    private lateinit var mAdapter: RankAdapter
    private val mList: MutableList<HotBean.ItemListBean.DataBean> = mutableListOf()
    var mstart: Int = 10
    lateinit var name: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_detail)
        name = intent.getStringExtra("name")
        initToolbar()
        initView()
    }

    fun initView() {
        mAdapter = RankAdapter(this, mList)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = mAdapter
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE &&
                        recyclerView?.canScrollVertically(-1)!!) {
                    if (date != null) {
                        loadMore("date")
                        mstart = mstart.plus(10)
                    }
                }
            }
        })
        refresh_view.setOnRefreshListener {
            loadData()
        }
        loadData()
        refresh_view.isRefreshing = true
    }

    private fun initToolbar() {
        title = name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun loadData() {
        HttpClient.getInstance()
                .getFindDetailData(name, "date")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t: HotBean? ->
                    if (refresh_view.isRefreshing) refresh_view.isRefreshing = false
                    if (t != null) {
                        handleData(t, true)
                    }
                })
    }


    private fun loadMore(date: String) {
        HttpClient.getInstance()
                .getFindMore(mstart, name, date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t: HotBean? ->
                    if (t != null) {
                        handleData(t, false)
                    }
                })
    }

    private fun handleData(hotBean: HotBean, isClear: Boolean) {
        val regEx = "[^0-9]"
        val pattern: Pattern = Pattern.compile(regEx)
        val m = pattern.matcher(hotBean.nextPageUrl as CharSequence?)
        date = m.replaceAll("").subSequence(1, m.replaceAll("").length - 1).toString()
        if (isClear)
            mList.clear()
        hotBean.itemList?.forEach { mList.add(it.data!!) }
        mAdapter.notifyDataSetChanged()
    }

}
