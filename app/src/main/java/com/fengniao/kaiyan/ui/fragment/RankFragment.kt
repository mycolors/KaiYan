package com.fengniao.kaiyan.ui.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import com.fengniao.kaiyan.R
import com.fengniao.kaiyan.base.BaseFragment
import com.fengniao.kaiyan.bean.HotBean
import com.fengniao.kaiyan.net.HttpClient
import com.fengniao.kaiyan.ui.adapter.RankAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_rank.*


/**
 * A simple [Fragment] subclass.
 */
class RankFragment : BaseFragment() {

    companion object {
        fun newInstance(type: String): RankFragment {
            val fragment: RankFragment = RankFragment()
            val bundle: Bundle = Bundle()
            bundle.putString("type", type)
            fragment.arguments = bundle
            return fragment
        }
    }

    private val mList: MutableList<HotBean.ItemListBean.DataBean> = mutableListOf()
    lateinit var mAdapter: RankAdapter

    override fun setLayoutId(): Int = R.layout.fragment_rank

    override fun initView() {
        recycler_view.layoutManager = LinearLayoutManager(context)
        mAdapter = RankAdapter(context, mList)
        recycler_view.adapter = mAdapter
        loadData()
    }

    private fun loadData() {
        val type: String = arguments.getString("type")
        HttpClient.getInstance()
                .getHotData(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t: HotBean? ->
                    t?.itemList?.forEach { mList.add(it.data!!) }
                    mAdapter.notifyDataSetChanged()
                })


    }

}
