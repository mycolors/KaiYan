package com.fengniao.kaiyan.ui.activity

import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import com.bumptech.glide.Glide
import com.fengniao.kaiyan.R
import com.fengniao.kaiyan.base.BaseActivity
import com.fengniao.kaiyan.bean.VideoBean
import kotlinx.android.synthetic.main.activity_video.*

class VideoActivity : BaseActivity() {

    private lateinit var vidoeBean: VideoBean

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
        vidoeBean = intent.getParcelableExtra("data")
        initView()
        initVideo()
    }

    private fun initView() {
        Glide.with(this).load(vidoeBean.blurred).into(iv_bottom)
        tv_desc.text = vidoeBean.description
        tv_title.typeface = Typeface.createFromAsset(assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
        tv_title.text = vidoeBean.title
        tv_title.typeface = Typeface.createFromAsset(assets, "fonts/FZLanTingHeiS-L-GB-Regular.TTF")
        val category = vidoeBean.category
        val duration = vidoeBean.duration
        val minute = duration?.div(60)
        val second = duration?.minus((minute?.times(60)) as Long)
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
        tv_time.text = "$category / $realMinute'$realSecond''"
        tv_favor.text = vidoeBean.collect.toString()
        tv_share.text = vidoeBean.share.toString()
        tv_reply.text = vidoeBean.share.toString()
    }

    private fun initVideo() {
        video_view.setVideoURI(Uri.parse(vidoeBean.playUrl))
        video_view.setOnPreparedListener { video_view.start() }
    }
}
