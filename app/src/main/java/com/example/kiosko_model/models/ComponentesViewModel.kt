package com.example.kiosko_model.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kiosko_model.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class ComponentesViewModel(private val repository: Repository): ViewModel() {

//    val componentesResponse: MutableLiveData<Response<List<Componentes>>>  = MutableLiveData()
    val componentesResponse: MutableLiveData <Response<List<Componentes>>>  = MutableLiveData()

    val componentesCompuestos: MutableLiveData <List<Compuestos>>  = MutableLiveData()

//    private var _componentesCompuestos = MutableLiveData<Response<List<Compuestos>>>()
//    val componentesCompuestos: LiveData <Response<List<Compuestos>>> = _componentesCompuestos

    val status:MutableLiveData<String> = MutableLiveData()

    private val _datos = MutableLiveData<Response<List<Componentes>>>()
    val datos: LiveData <Response<List<Componentes>>> = _datos


    fun getComponentes(){
        viewModelScope.launch {
            val response = repository.getComponentes()
            componentesResponse.value = response
            status.value ="Success in the data"
            _datos.value = response
        }
    }
//
    fun setComponentesCompuestos(index: Int){
    viewModelScope.launch {
        val response = repository.getComponentes()
        componentesCompuestos.value = response.body()?.get(index)?.componentes
    }
    }

}