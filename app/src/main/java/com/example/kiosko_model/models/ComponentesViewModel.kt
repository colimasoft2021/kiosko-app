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
    val componentesResponse: MutableLiveData <Response<customModulos>>  = MutableLiveData()



    private val mutableComponentes = MutableLiveData<List<Componentes?>>()
    val componentes: LiveData<List<Componentes?>> get() = mutableComponentes

    fun componentes(componentes:List<Componentes?>){
        mutableComponentes.value = componentes
    }


    val status:MutableLiveData<String> = MutableLiveData()

    private val _datos = MutableLiveData<Response<customModulos>>()
    val datos: LiveData <Response<customModulos>> = _datos


    fun getComponentes(id: Id){
        viewModelScope.launch {
            val response = repository.getComponentes(id)
            componentesResponse.value = response
            status.value ="Success in the data"
            _datos.value = response
        }
    }
//

}