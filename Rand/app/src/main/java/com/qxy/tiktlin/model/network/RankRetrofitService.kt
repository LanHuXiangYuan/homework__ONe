package com.qxy.tiktlin.model.network

import com.qxy.tiktlin.model.bean.MovieJsonBean
import com.qxy.tiktlin.model.network.AppConfig.BASE_URL
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path

import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)  //要访问的主机地址，注意以 /（斜线） 结束，不然可能会抛出异常
    .addConverterFactory(GsonConverterFactory.create()) //添加Gson
    .build();





object RankApi {
    val retrofitService : RankRetrofitService by lazy {
        retrofit.create(RankRetrofitService::class.java) }
}

interface RankRetrofitService {

    @Headers("Content-Type: application/json")
    @GET("discovery/ent/rank/item/")
    fun getMovieRankData(@Header("access-token") access_token:String,@Path("type")typr:Int): Call<MovieJsonBean?>?
}