package com.example.kiosko_model.network

import com.example.kiosko_model.models.Componentes
import retrofit2.Response
import retrofit2.http.GET

interface ApiComponentesService {

    @GET("GetModulosAndComponentsForApp")
    suspend fun  getComponents(): Response<List<Componentes>>

}