package com.fengniao.kaiyan.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.fengniao.kaiyan.R

class SearchAdapter(context: Context, list: List<String>) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    var mList: List<String>? = null
    var mContext: Context? = null

    init {
        mList = list
        mContext = context
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.tvTitle?.text = mList!![position]
    }

    override fun getItemCount(): Int = mList!!.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_search, parent, false))


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle:TextView? = null
        init {
            tvTitle = itemView.findViewById<TextView>(R.id.tv_title)
        }
    }
}