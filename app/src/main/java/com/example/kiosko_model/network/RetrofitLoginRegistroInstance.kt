package com.example.kiosko_model.network

import com.example.kiosko_model.utilTimeoutException.Constants.Companion.LOGIN_BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient



object RetrofitLoginRegistroInstance {

    private val client = OkHttpClient.Builder().apply {
        addInterceptor(LoginInterceptor())
    }.build()

    private val retrofit by lazy{
        Retrofit.Builder()
            .baseUrl(LOGIN_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

}