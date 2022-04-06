package com.example.kiosko_model.network

import com.example.kiosko_model.utilTimeoutException.Constants.Companion.COMPONENTES_BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitComponentsInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(COMPONENTES_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val componentesApi: ApiComponentesService by lazy {
        retrofit.create(ApiComponentesService::class.java)
    }
}