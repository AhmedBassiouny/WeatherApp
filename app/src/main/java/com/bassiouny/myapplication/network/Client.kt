package com.bassiouny.myapplication.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class Client {
    private lateinit var retrofit: Retrofit
    private lateinit var okHttpClient: OkHttpClient
    private val REQUEST_TIMEOUT = 60
    private val BASE_URL = "https://app.tibber.com/v4/"

    fun getService(): Api {
        return getClient().create(Api::class.java)
    }

    private fun getClient(): Retrofit {

        initOkHttp()
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit
    }

    private fun initOkHttp() {
        val httpClient = OkHttpClient().newBuilder()
            .connectTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)

        httpClient.addInterceptor(Interceptor { chain ->
            val builder = chain.request().newBuilder()
            builder.header(
                "Authorization",
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ImI0MjAwMDFkLTE4OWItNDRjMC1hM2Q1LWQ2MjQ1MmJmZGQ0MiIsImlzSW1wZXJzb25hdGVkIjp0cnVlLCJpYXQiOjE1ODQ3MDY4NjV9.S-4SVcZIg_MXcXfd8slLpAfJ8E565wMRFEqO4cDP3wc"
            )
            builder.header("Host", "app.tibber.com")
            return@Interceptor chain.proceed(builder.build())
        })

        okHttpClient = httpClient.build()
    }
}