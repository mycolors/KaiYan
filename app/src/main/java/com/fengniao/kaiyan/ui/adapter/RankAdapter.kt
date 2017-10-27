package com.fengniao.kaiyan.ui.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.fengniao.kaiyan.R
import com.fengniao.kaiyan.bean.HotBean
import com.fengniao.kaiyan.bean.VideoBean
import com.fengniao.kaiyan.ui.activity.VideoActivity

class RankAdapter(context: Context, list: MutableList<HotBean.ItemListBean.DataBean>) : RecyclerView.Adapter<RankAdapter.ViewHolder>() {
    var mContext: Context? = null
    var mList: MutableList<HotBean.ItemListBean.DataBean>? = null

    init {
        mContext = context
        mList = list
    }

    override fun getItemCount(): Int = mList!!.size

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val photoUrl: String = mList!![position].cover?.feed!!
        photoUrl.let { Glide.with(mContext).load(it).into(holder?.ivRank) }
        val title: String = mList!![position].title!!
        val category = mList!![position].category
        val duration = mList!![position].duration
        val minute = duration.div(60)
        val second = duration.minus((minute.times(60)) as Long)
        val realMinute = if (minute < 10) {
            "0" + minute
        } else {
            minute.toString()
        }
        val realSecond = if (second < 10) {
            "0" + second
        } else {
            second.toString()
        }
        holder?.tvTitle?.text = title
        holder?.tvTime?.text = "$category / $realMinute'$realSecond''"
        holder?.itemView?.setOnClickListener {
            val intent: Intent = Intent(mContext, VideoActivity::class.java)
            val desc = mList!![position].description
            val playUrl = mList!![position].playUrl
            val blurred = mList!![position].cover?.blurred
            val collect = mList!![position].consumption?.collectionCount
            val share = mList!![position].consumption?.shareCount
            val reply = mList!![position].consumption?.replyCount
            val time = System.currentTimeMillis()
            val videoBean = VideoBean(photoUrl, title, desc, duration, playUrl, category, blurred, collect, share, reply, time)
            intent.putExtra("data", videoBean)
            mContext?.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_list_rank, parent, false))

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivRank: ImageView? = null
        var tvTitle: TextView? = null
        var tvTime: TextView? = null

        init {
            ivRank = itemView.findViewById<ImageView>(R.id.iv_rank)
            tvTime = itemView.findViewById<TextView>(R.id.tv_time)
            tvTitle = itemView.findViewById<TextView>(R.id.tv_title)
        }

    }
}