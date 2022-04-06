package com.example.kiosko_model.network

import com.example.kiosko_model.models.Componentes
import com.example.kiosko_model.models.LoginR
import com.example.kiosko_model.models.Post
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {



//    @GET("posts/1")
    @GET("Aleex-A")
    suspend fun getPost(): Response<Post>

    @GET("{postNombre}")
    suspend fun getPost2(
        @Path("postNombre") nombre : String
    ): Response<Post>

    @POST("Usuarios")
    suspend fun pushPost(
        @Body post: Post
    ): Response<List<LoginR>>
}