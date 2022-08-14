package com.qxy.tiktlin.model

import com.qxy.tiktlin.model.bean.MovieJsonBean
import com.qxy.tiktlin.model.network.RankApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkRetrofit {

    fun getRankData(token:String,type :Int) {
        val call: Call<MovieJsonBean?>? = RankApi.retrofitService.getMovieRankData(token,type)
        call!!.enqueue(object : Callback<MovieJsonBean?> {
            override fun onResponse(
                call: Call<MovieJsonBean?>,
                response: Response<MovieJsonBean?>
            ) {
                val goods: MovieJsonBean? = response.body()
            }

            override fun onFailure(call: Call<MovieJsonBean?>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

}