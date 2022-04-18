package com.example.kiosko_model.repository

import com.example.kiosko_model.models.*
import com.example.kiosko_model.network.RetrofitComponentsInstance
import com.example.kiosko_model.network.RetrofitLoginInstance
import com.example.kiosko_model.network.RetrofitLoginRegistroInstance
import com.example.kiosko_model.network.RetrofitProgresoInstance
import retrofit2.Response
import retrofit2.Retrofit

class Repository {

    suspend fun getPost(): Response<Post> {
        return RetrofitLoginInstance.api.getPost()
    }

    suspend fun getPost2(nombre : String): Response<Post>{
        return RetrofitLoginInstance.api.getPost2(nombre)
    }

    suspend fun getComponentes(id: Id): Response<customModulos> {
        return RetrofitComponentsInstance.api.getComponentes(id)
    }

    suspend fun pushPost(post: Post): Response<List<LoginR>>{
        return RetrofitLoginInstance.api.pushPost(post)
    }
    suspend fun pushPostRegistro(registro: PostRegistro): Response<LoginResponseR>{
        return RetrofitLoginRegistroInstance.apiRegistro.pushPostRegistro(registro)
    }
    suspend fun updateProgress(registro: PostProgreso): Response<ProgresoR>{
        return RetrofitProgresoInstance.api.updateProgress(registro)
    }
}