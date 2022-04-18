
package com.example.kiosko_model.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch
import retrofit2.Response

 class PorcentajeViewModel: ViewModel() {

     private val _IdModulo = MutableLiveData<Int>()
     val idModulo: LiveData<Int> get() = _IdModulo

     fun setIdModulo(idModulo:Int){
         _IdModulo.value = idModulo
     }

     private val _id = MutableLiveData<Int>()
     val idProgreso: LiveData<Int> get() = _id

     fun setId(ID:Int){
         _id.value = ID
     }

     private val _porcentaje= MutableLiveData<Int>()
     val porcentaje: LiveData<Int> get() = _porcentaje

     fun setPorcentaje(Porcentaje:Int){
         _porcentaje.value = Porcentaje
     }

     private val _cantidadModulos = MutableLiveData<Int>()
     val cantidadModulos: LiveData<Int> get() = _cantidadModulos

     fun setCantidadModulos(cantidadModulo:Int){
         _cantidadModulos.value = cantidadModulo
     }

     private val _progresoPerModulo = MutableLiveData<Int>()
     val progresoPerModulo: LiveData<Int> get() = _progresoPerModulo

     fun setProgresoPorModulo(progresoTotal:Int){
         val prog =( progresoTotal / _cantidadModulos.value!!)
         _progresoPerModulo.value = prog
     }

     fun reset(){
         _IdModulo.value=0
         _id.value=0
         _porcentaje.value=0
         _cantidadModulos.value=0
         _progresoPerModulo.value=0





     }

 }


