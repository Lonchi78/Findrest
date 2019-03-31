package com.lonchi.andrej.findrest.Data

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.lonchi.andrej.findrest.Data.Response.Restaurant
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

const val API_KEY = "2b3a8c2baa6d953047bc375668d2988a"

//  https://developers.zomato.com/api/v2.1/restaurant?res_id=16507359

interface ZomatoApiService {

    @Headers("user-key: 2b3a8c2baa6d953047bc375668d2988a")
    @GET("restaurant")
    fun getRestaurant(
        @Query("res_id") id : Int = 16507359
    ): Deferred<Restaurant>

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