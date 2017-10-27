package com.fengniao.kaiyan.net

import android.content.Context
import android.util.Log
import com.fengniao.kaiyan.util.NetWorkUtils
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response

class CacheInterceptor(val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response? {
        var request = chain?.request()
        return if (NetWorkUtils.isConnect(context)) {
            val response = chain?.proceed(request)
            val maxAge = 60
            val cacheControl = request?.cacheControl().toString()
            Log.e("CacheInterceptor", "6s load cahe" + cacheControl)
            return response?.newBuilder()?.removeHeader("Prama")
                    ?.removeHeader("Cache-Control")
                    ?.header("Cache-Control", "public, max-age=" + maxAge)?.build()
        } else {
            Log.e("CacheInterceptor", " no network load cahe")
            request = request?.newBuilder()?.cacheControl(CacheControl.FORCE_CACHE)?.build()
            val response = chain?.proceed(request)
            val maxState = 60 * 60 * 24 * 3
            return response?.newBuilder()?.removeHeader("Prama")?.removeHeader("Cache-Control")
                    ?.header("Cache_control", "public, only-if-cached, max-stale=" + maxState)
                    ?.build()
        }
    }
}