package com.fengniao.kaiyan.base

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onContentChanged() {
        super.onContentChanged()
    }


    fun<T> jumpToActivity(activity: Class<T>) {
        val intent: Intent = Intent(this, activity)
        jumpToActivity(intent)
    }

    fun jumpToActivity(intent: Intent) {
        startActivity(intent)
    }
}
