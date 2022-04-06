package com.example.kiosko_model.network

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

class LoginInterceptor: Interceptor {
    private val userName = "SicomAcess"
    private val Password = "$1c0om007"
    private val credential = Credentials.basic(userName,Password)


    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization",credential)
            .build()
        return chain.proceed(request)
    }
}