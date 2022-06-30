package com.example.kiosko_model.repository

import com.example.kiosko_model.models.*
import com.example.kiosko_model.network.*
import retrofit2.Response
import retrofit2.Retrofit

class Repository {

    suspend fun getVideos(): Response<List<Videos>> {
        return RetrofitVideosInstance.api.getVideos()
    }

    suspend fun getGuias(): Response<List<Guias>>{
        return RetrofitGuiasInstance.api.getGuias()
    }

    suspend fun getComponentes(id: Id): Response<customModulos> {
        return RetrofitComponentsInstance.api.getComponentes(id)
    }

    suspend fun getAvisoInicial(): Response<List<avisoInicialResponse>>{
        return RetrofitAvisoInicialInstance.api.getAvisoIniciales()
    }

    suspend fun pushPost(post: Post): Response<List<LoginR>>{
        return RetrofitLoginInstance.api.pushPost(post)
    }

    suspend fun pushPostRegistro(registro: PostRegistro): Response<LoginResponseR>{
        return RetrofitLoginRegistroInstance.apiRegistro.pushPostRegistro(registro)
    }
    suspend fun pushPostNotificaciones(id: Id): Response<Notificaciones>{
        return RetrofitNotificacionesInstance.apiRegistro.pushPostNotificaciones(id)
    }
    suspend fun updateProgress(registro: PostProgreso): Response<ProgresoR>{
        return RetrofitProgresoInstance.api.updateProgress(registro)
    }
}