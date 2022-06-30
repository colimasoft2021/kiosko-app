package com.example.kiosko_model.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kiosko_model.modelsimagenBoton.Compuestos
import com.example.kiosko_model.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class GuiasViewModel(private val repository: Repository): ViewModel() {

//    val componentesResponse: MutableLiveData<Response<List<Componentes>>>  = MutableLiveData()
    private val _guiasResponse = MutableLiveData<Response<List<Guias>>>()
    val guiasResponse: LiveData <Response<List<Guias>>> = _guiasResponse

//    private val _datos = MutableLiveData<Response<customModulos>>()
//    val datos: LiveData <Response<customModulos>> = _datos


    private val _componentes = MutableLiveData<List<Compuestos?>>()
    val componentes: LiveData<List<Compuestos?>> get() = _componentes

    fun componentes(componentes:List<Compuestos?>){
        _componentes.value = componentes
    }



    fun getGuias(){
        viewModelScope.launch {
            val response = repository.getGuias()
            _guiasResponse.value = response
        }
    }
//

}