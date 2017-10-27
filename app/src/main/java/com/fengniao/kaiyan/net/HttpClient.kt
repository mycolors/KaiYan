package com.fengniao.kaiyan.net

import android.annotation.SuppressLint
import com.fengniao.kaiyan.app.AppContext
import com.fengniao.kaiyan.bean.FindBean
import com.fengniao.kaiyan.bean.HomeBean
import com.fengniao.kaiyan.bean.HotBean
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

class HttpClient private constructor() {
    private var okHttpClient: OkHttpClient
    private var retrofit: Retrofit
    private var apiService: ApiService
    private val mContext = AppContext.mContext
    private var httpCacheDirectory: File? = null
    private var cache: Cache? = null


    init {
        //缓存地址
        if (httpCacheDirectory == null) {
            httpCacheDirectory = File(mContext.cacheDir, "app_cache")
        }
        if (cache == null) {
            cache = Cache(httpCacheDirectory, 10 * 1024 * 1024)
        }

        okHttpClient = OkHttpClient.Builder()
                .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .cache(cache)
                .addNetworkInterceptor(CacheInterceptor(mContext))
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .build()

        retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(ApiConstant.BASE_URL)
                .build()
        apiService = retrofit.create(ApiService::class.java)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        var mInstance: HttpClient? = null

        fun getInstance(): HttpClient {
            if (mInstance == null) {
                synchronized(HttpClient::class) {
                    if (mInstance == null) {
                        mInstance = HttpClient()
                    }
                }
            }
            return mInstance!!
        }
    }

    //获取首页数据
    fun getHomeList(): Observable<HomeBean> {
        return apiService.getHomeData()
    }


    //首页获取更多数据
    fun getHomeMore(date: String, num: String): Observable<HomeBean> {
        return apiService.getHomeMore(date, num)
    }

    //获取发现频道数据
    fun getFindData(): Observable<MutableList<FindBean>> = apiService.getFindData()

    //获取热门排行数据
    fun getHotData(strategy: String): Observable<HotBean> {
        return apiService.getHotData(
                10,
                strategy,
                "26868b32e808498db32fd51fb422d00175e179df",
                83
        )
    }

    //获取发现频道详情数据
    fun getFindDetailData(categoryName: String, strategy: String): Observable<HotBean> {
        return apiService.getFindDetailData(categoryName, strategy, "26868b32e808498db32fd51fb422d00175e179df", 83)
    }

    fun getFindMore(start: Int, categoryName: String, strategy: String): Observable<HotBean> {
        return apiService.getFindMore(start, 10, categoryName, strategy)
    }


}
