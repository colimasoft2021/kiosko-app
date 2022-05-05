package com.example.kiosko_model.network

import com.example.kiosko_model.models.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {



//    @GET("posts/1")


    @GET("GetAllComponentesPopUpVideo")
    suspend fun getVideos(): Response<List<Videos>>

    @GET("GetModulosGuiasRapidas")
    suspend fun getGuias(): Response<List<Guias>>

    @GET("MessagesInitialsForApp")
    suspend fun getAvisoIniciales(): Response<List<avisoInicialResponse>>

    @GET("{postNombre}")
    suspend fun getPost2(
        @Path("postNombre") nombre : String
    ): Response<Post>

    @POST("Usuarios")
    suspend fun pushPost(
        @Body post: Post
    ): Response<List<LoginR>>


    @POST("saveNewUser")
    suspend fun pushPostRegistro(
        @Body postRegistro: PostRegistro
    ): Response<LoginResponseR>

    @POST("GetModulosAndComponentsForApp")
    suspend fun getComponentes(
        @Body id: Id
    ):  Response<customModulos>

    @POST("UpdateProgress")
    suspend fun updateProgress(
        @Body progreso: PostProgreso
    ): Response<ProgresoR>

}