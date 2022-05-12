package com.example.kiosko_model.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kiosko_model.modelsimagenBoton.Compuestos
import com.example.kiosko_model.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class GuiasContenidoViewModel(): ViewModel() {


    private val _componentes = MutableLiveData<List<Compuestos?>>()
    val componentes: LiveData<List<Compuestos?>> get() = _componentes

    fun componentes(componentes: List<Compuestos?>) {
        _componentes.value = componentes
    }

    private val _urlFondo = MutableLiveData<String?>()
    val urlFondo: LiveData<String?> get() = _urlFondo

    fun urlFondo(url: String?) {
        _urlFondo.value = url
    }


}