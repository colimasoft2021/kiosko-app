package com.example.kiosko_model.network

import android.provider.SyncStateContract
import com.example.kiosko_model.utilTimeoutExceptionpushPost.Constants
import com.example.kiosko_model.utilTimeoutExceptionpushPost.Constants.Companion.REGISTRO_USER_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient



object RetrofitProgresoInstance {

    private val client = OkHttpClient.Builder().apply {
        addInterceptor(LoginRegistroInterceptor())
    }.build()

    private val registro by lazy {
        Retrofit.Builder()
            .baseUrl(REGISTRO_USER_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val apiRegistro: ApiService by lazy {
        registro.create(ApiService::class.java)
    }
}