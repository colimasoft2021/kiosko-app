package com.example.kiosko_model.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kiosko_model.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class ComponentesViewModel2(): ViewModel() {

    private val _Componentes2 = MutableLiveData<List<Componentes2?>>()
    val componentes2: LiveData<List<Componentes2?>> get() = _Componentes2

    fun componentes2(componentes2:List<Componentes2?>){
        _Componentes2.value = componentes2
    }

    private val _hijos = MutableLiveData<Int>()
    val hijos: LiveData<Int> get() = _hijos

    fun hijos(hijo:Int){
        _hijos.value = hijo
    }

    private val _index = MutableLiveData<Int>()
    val index: LiveData<Int> get() = _index

    fun index(index:Int){
        _index.value = index
    }



}