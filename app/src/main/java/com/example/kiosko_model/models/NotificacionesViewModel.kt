package com.example.kiosko_model.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kiosko_model.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class NotificacionesViewModel(private val repository: Repository): ViewModel() {

    val myResponse: MutableLiveData<Response<Notificaciones>> = MutableLiveData()
    val stats: MutableLiveData<String> = MutableLiveData()

    fun pushPostNotificaciones(id: Id){
        viewModelScope.launch {
            val response = repository.pushPostNotificaciones(id)
            myResponse.value = response
        }
    }
//


}