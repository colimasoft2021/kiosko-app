package com.example.kiosko_model.repository

import com.example.kiosko_model.models.Componentes
import com.example.kiosko_model.models.LoginR
import com.example.kiosko_model.models.Post
import com.example.kiosko_model.network.RetrofitComponentsInstance
import com.example.kiosko_model.network.RetrofitLoginInstance
import retrofit2.Response
import retrofit2.Retrofit

class Repository {

    suspend fun getPost(): Response<Post> {
        return RetrofitLoginInstance.api.getPost()
    }

    suspend fun getPost2(nombre : String): Response<Post>{
        return RetrofitLoginInstance.api.getPost2(nombre)
    }

    suspend fun getComponentes(): Response<List<Componentes>> {
        return RetrofitComponentsInstance.componentesApi.getComponents()
    }

    suspend fun pushPost(post: Post): Response<List<LoginR>>{
        return RetrofitLoginInstance.api.pushPost(post)
    }
}