package com.example.wanandroid_copy2.common.network

import com.example.wanandroid_copy2.common.constant.Constant
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {

    private val retrofit: Retrofit

    fun <T> create(clz: Class<T>): T {
        return retrofit.create(clz)
    }

    init {
        retrofit = Retrofit
            .Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(initOkHttpClient())
            .build()
    }

    companion object {
        val instance by lazy {
            RetrofitFactory()
        }
    }

    private fun initOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(LoggingInterceptor())
            .addInterceptor(initCommonInterceptor())
//            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }


    private fun initCommonInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("charset", "UTF-8")
                .build()
            chain.proceed(request)
        }
    }
}