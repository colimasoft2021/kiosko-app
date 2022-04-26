package com.example.kiosko_model.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kiosko_model.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MensajeInicialViewModel(private val repository: Repository): ViewModel() {

//    val componentesResponse: MutableLiveData<Response<List<Componentes>>>  = MutableLiveData()
    val AvisoResponse: MutableLiveData <Response<List<avisoInicialResponse>>>  = MutableLiveData()


    fun getAvisoInicial(){
        viewModelScope.launch {
            val response = repository.getAvisoInicial()
            AvisoResponse.value = response

        }
    }
//

}