package com.example.kiosko_model.network

//import com.squareup.moshi.Moshi
//import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
//import retrofit2.Retrofit
//import retrofit2.converter.moshi.MoshiConverterFactory
//import retrofit2.converter.scalars.ScalarsConverterFactory
//import retrofit2.http.GET
//
//const val BASE_url =
//    "https://api.github.com/users/"
//    //"https://android-kotlin-fun-mars-server.appspot.com"
//
////private val moshi = Moshi.Builder()
////    .add(KotlinJsonAdapterFactory())
////    .build()
//
//private val retrofit = Retrofit.Builder()
//    .addConverterFactory(ScalarsConverterFactory.create())
////    .addConverterFactory(MoshiConverterFactory.create(moshi))
//    .baseUrl(BASE_url)
//    .build()
//
//interface ApiLoginService {
////    @GET("photos")
//    @GET("Aleex-A")
//    suspend fun getLogin():String
//}
//object loginApi{
//    val retrofitService: ApiLoginService by lazy {
//        retrofit.create(ApiLoginService::class.java)
//    }
//}