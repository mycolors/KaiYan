package com.fengniao.kaiyan.net

import com.fengniao.kaiyan.bean.FindBean
import com.fengniao.kaiyan.bean.HomeBean
import com.fengniao.kaiyan.bean.HotBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("v2/feed?num=2&udid=26868b32e808498db32fd51fb422d00175e179df&vc=83")
    fun getHomeData(): Observable<HomeBean>

    @GET("v2/feed")
    fun getHomeMore(@Query("date") date: String, @Query("num") num: String): Observable<HomeBean>

    @GET("v2/categories?udid=26868b32e808498db32fd51fb422d00175e179df&vc=83")
    fun getFindData(): Observable<MutableList<FindBean>>

    @GET("v3/ranklist")
    fun getHotData(@Query("num") num: Int, @Query("strategy") strategy: String,
                   @Query("udid") udid: String, @Query("vc") vc: Int): Observable<HotBean>

    @GET("v3/videos")
    fun getFindDetailData(@Query("categoryName") categoryName: String, @Query("strategy") strategy: String,
                          @Query("udid") udid: String, @Query("vc") vc: Int): Observable<HotBean>

    @GET("v3/videos")
    fun getFindMore(@Query("start") start: Int, @Query("num") num: Int,
                    @Query("categoryName") categoryName: String, @Query("strategy") strategy: String)
            : Observable<HotBean>

}