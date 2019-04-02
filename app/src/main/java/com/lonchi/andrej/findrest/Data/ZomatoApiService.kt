package com.lonchi.andrej.findrest.Data

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.lonchi.andrej.findrest.Data.Response.getDailymenu.DailyMenus
import com.lonchi.andrej.findrest.Data.db.entity.FetchGeocode
import com.lonchi.andrej.findrest.Data.db.entity.FetchSearch
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

const val API_KEY = "2b3a8c2baa6d953047bc375668d2988a"

//  https://developers.zomato.com/api/v2.1/restaurant?res_id=16507359

interface ZomatoApiService {

    /*  Zomato api method:  /dailymenu     */
    @Headers("user-key: 2b3a8c2baa6d953047bc375668d2988a")
    @GET("dailymenu")
    fun getDailymenu(
        @Query("res_id") id : Int = 16507359
    ): Deferred<DailyMenus>


    /*  Zomato api method:  /search     */
    @Headers("user-key: 2b3a8c2baa6d953047bc375668d2988a")
    @GET("search")
    fun getSearch(
        @Query("q") query : String = "brno"
    ): Deferred<FetchSearch>


    /*  Zomato api method:  /geocode     */
    @Headers("user-key: 2b3a8c2baa6d953047bc375668d2988a")
    @GET("geocode")
    fun getGeocode(
        @Query("lat") lat : Double = 50.0,
        @Query("lon") lon : Double = 50.0
    ): Deferred<FetchGeocode>


    /*  Singleton instance              */
    companion object {
        operator fun invoke(): ZomatoApiService{

            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .build()

                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .writeTimeout(5, TimeUnit.MINUTES) // write timeout
                .readTimeout(5, TimeUnit.MINUTES) // read timeout
                .connectTimeout(30, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://developers.zomato.com/api/v2.1/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ZomatoApiService::class.java)
        }
    }

}