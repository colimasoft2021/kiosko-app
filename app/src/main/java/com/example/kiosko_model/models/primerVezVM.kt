package com.example.kiosko_model.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch
import retrofit2.Response

 class primerVezVM: ViewModel() {

     private val _primerVez = MutableLiveData<Boolean>()
     val primerVez: LiveData<Boolean> get() = _primerVez

     fun posicion(PV: Boolean) {
         _primerVez.value = PV
     }

 }


