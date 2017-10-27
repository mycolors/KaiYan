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
import com.fengniao.kaiyan.bean.FindBean
import com.fengniao.kaiyan.ui.activity.FindDetailActivity

class FindAdapter(mContext: Context, list: MutableList<FindBean>) : RecyclerView.Adapter<FindAdapter.ViewHolder>() {
    var mContext: Context? = null
    var mList: List<FindBean>? = null

    init {
        this.mContext = mContext
        this.mList = list
    }


    override fun getItemCount(): Int = mList!!.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder
            = ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_list_find, parent, false))

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.tvFind?.text = mList!![position].name
        Glide.with(mContext).load(mList!![position].bgPicture).into(holder?.ivFind)
        holder?.itemView?.setOnClickListener {
            var intent: Intent = Intent(mContext, FindDetailActivity::class.java)
            intent.putExtra("name", mList?.get(position)?.name)
            mContext?.startActivity(intent)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvFind: TextView? = null
        var ivFind: ImageView? = null

        init {
            tvFind = view.findViewById<TextView>(R.id.tv_find)
            ivFind = view.findViewById<ImageView>(R.id.iv_find)
        }
    }
}