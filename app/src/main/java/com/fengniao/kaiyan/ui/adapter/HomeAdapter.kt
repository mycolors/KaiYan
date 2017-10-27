package com.fengniao.kaiyan.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.fengniao.kaiyan.R
import com.fengniao.kaiyan.bean.HomeBean
import com.fengniao.kaiyan.bean.VideoBean
import com.fengniao.kaiyan.ui.activity.VideoActivity

class HomeAdapter(mContext: Context, mList: List<HomeBean.IssueListBean.ItemListBean>)
    : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    var mList: List<HomeBean.IssueListBean.ItemListBean>
    var mContext: Context


    init {
        this.mList = mList
        this.mContext = mContext

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HomeViewHolder?, position: Int) {
        val bean = mList.get(position)
        val title = bean.data?.title
        val category = bean.data?.category
        val minute = bean.data?.duration?.div(60)
        val second = bean.data?.duration?.minus(minute?.times(60) as Long)
        val realMinute: String = if (minute!! < 10) {
            "0" + minute
        } else {
            minute.toString()
        }
        val realSecond: String = if (second!! < 10) {
            "0" + second
        } else {
            second.toString()
        }
        val photo = bean.data?.cover?.feed
        val author = bean.data?.author
        Glide.with(mContext).load(photo).into(holder?.ivPhoto)
        holder?.tvTitle?.text = title
        holder?.tvDetail?.text = "发布于 $category / $realMinute:$realSecond"
        if (author != null) {
            holder?.ivAvatar?.visibility = View.VISIBLE
            Glide.with(mContext).load(author.icon).into(holder?.ivAvatar)
        } else {
            holder?.ivAvatar?.visibility = View.GONE
        }
        holder?.itemView?.setOnClickListener {
            val intent: Intent = Intent(mContext, VideoActivity::class.java)
            val desc = bean.data?.description
            val duration = bean.data?.duration
            val playUrl = bean.data?.playUrl
            val blurred = bean.data?.cover?.blurred
            val collect = bean.data?.consumption?.collectionCount
            val share = bean.data?.consumption?.shareCount
            val reply = bean.data?.consumption?.replyCount
            val time = System.currentTimeMillis()
            val videoBean = VideoBean(photo, title, desc, duration, playUrl, category, blurred, collect, share, reply, time)
            intent.putExtra("data", videoBean)
            mContext.startActivity(intent)
        }
    }


    override fun getItemCount(): Int = mList.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): HomeViewHolder
            = HomeViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_list_home, parent
            , false), mContext)

    class HomeViewHolder(itemView: View, context: Context) : RecyclerView.ViewHolder(itemView) {
        var tvDetail: TextView? = null
        var tvTitle: TextView? = null
        var ivPhoto: ImageView? = null
        var ivAvatar: ImageView? = null

        init {
            tvDetail = itemView.findViewById<TextView>(R.id.tv_detail)
            tvTitle = itemView.findViewById<TextView>(R.id.tv_title)
            ivPhoto = itemView.findViewById<ImageView>(R.id.iv_photo)
            ivAvatar = itemView.findViewById<ImageView>(R.id.iv_avatar)
            tvTitle?.typeface = Typeface.createFromAsset(context.assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
        }
    }


}