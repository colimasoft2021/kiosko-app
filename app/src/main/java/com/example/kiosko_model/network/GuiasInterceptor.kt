package com.example.kiosko_model.network

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

class GuiasInterceptor : Interceptor {
    //    private val userName = "Admin"
//    private val Password = "password"
    private val userName = "AdminKiosko"
    private val Password = "T1endA5K105k0"

    private val credential = Credentials.basic(userName, Password)


    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", credential)
            .build()
        return chain.proceed(request)
    }
}