package com.fengniao.kaiyan.app

import android.app.Application
import android.content.Context

class AppContext:Application(){

    companion object {
         lateinit var mContext:Context
    }

    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext
    }
}