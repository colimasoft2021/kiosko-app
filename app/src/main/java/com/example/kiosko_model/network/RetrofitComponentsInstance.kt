package com.example.kiosko_model.network

import com.example.kiosko_model.utilTimeoutExceptionpushPost.Constants
import com.example.kiosko_model.utilTimeoutExceptionpushPost.Constants.Companion.COMPONENTES_BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitComponentsInstance {
    private val client = OkHttpClient.Builder().apply {
        addInterceptor(ComponentesInterceptor())
    }.build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(COMPONENTES_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

}