package com.fengniao.kaiyan.base

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.fengniao.kaiyan.util.UIUtils

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UIUtils.translucentBar(this, Color.BLACK)
    }

    override fun onContentChanged() {
        super.onContentChanged()
    }


    fun <T> jumpToActivity(activity: Class<T>) {
        val intent: Intent = Intent(this, activity)
        jumpToActivity(intent)
    }

    fun jumpToActivity(intent: Intent) {
        startActivity(intent)
    }
}
