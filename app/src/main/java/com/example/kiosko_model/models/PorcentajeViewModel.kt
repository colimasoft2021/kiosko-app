
package com.example.kiosko_model.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch
import retrofit2.Response

 class Porcentaje: ViewModel() {

     private val _IdModulo = MutableLiveData<Int>()
     val idModulo: LiveData<Int> get() = _IdModulo

     fun SetIdModulo(idModulo:Int){
         _IdModulo.value = idModulo
     }


     private val _IdUsua = MutableLiveData<Int>()
     val idModulo: LiveData<Int> get() = _IdModulo

     fun SetIdModulo(idModulo:Int){
         _IdModulo.value = idModulo
     }

 }


