package com.qxy.tiktlin.model.network


import com.qxy.tiktlin.model.network.AppConfig.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path


private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()



object TokenApi {
    val retrofitService : TokenApiService by lazy {
        retrofit.create(TokenApiService::class.java) }
}

interface TokenApiService {

    @Headers("Content-Type: application/json")
    @GET("oauth/client_token/")
    fun getAccessToken(@Path("client_secret")client_secret:String
                       ,@Path("code")code:String
                       ,@Path("grant_type")grant_type:String
                       ,@Path("client_key")client_key:String): String

}
