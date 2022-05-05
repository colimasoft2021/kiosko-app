
package com.example.kiosko_model.network

import com.example.kiosko_model.utilTimeoutExceptionpushPost.Constants.Companion.COMPONENTES_BASE_URL
import com.example.kiosko_model.utilTimeoutExceptionpushPost.Constants.Companion.LOGIN_BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient



object RetrofitGuiasInstance {

    private val client = OkHttpClient.Builder().apply {
        addInterceptor(GuiasInterceptor())
    }.build()

    val retrofit by lazy{
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