package com.example.kiosko_model.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kiosko_model.modelsimagenBoton.Compuestos
import kotlinx.coroutines.launch
import retrofit2.Response

 class CompuestosViewModel2: ViewModel() {

     private val mutableComponentes = MutableLiveData<List<Compuestos?>>()
     val componentes: LiveData<List<Compuestos?>> get() = mutableComponentes

     private val _id = MutableLiveData<Int>()
     val id: LiveData<Int> get() = _id

     private val _padre = MutableLiveData<String?>()
     val padre: LiveData<String?> get() = _padre

     private val _colorModulo = MutableLiveData<Int>()
     val colorModulo: LiveData<Int> get() = _colorModulo

     fun colorModulo(colorModulo:Int){
         _colorModulo.value = colorModulo
     }

     fun componentes(compuestos:List<Compuestos?>){
         mutableComponentes.value = compuestos
     }

     fun id(id:Int){
         _id.value = id
     }


     fun padre(padre:String?){
         _padre.value = padre
     }

     private val mutablePosicion = MutableLiveData<Int>()
     val posicion: LiveData<Int> get() = mutablePosicion

     fun posicion(index: Int) {
         mutablePosicion.value = index
     }

 }


