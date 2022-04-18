package com.example.kiosko_model.modelslite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kiosko_model.models.PostProgreso
import com.example.kiosko_model.models.ProgresoR
import com.example.kiosko_model.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class ProgresoViewModel(private val repository: Repository): ViewModel() {

    val myResponse: MutableLiveData<Response<ProgresoR>> = MutableLiveData()

    fun pushProgresoRegistro(postProgreso: PostProgreso){
        viewModelScope.launch {
            val response = repository.updateProgress(postProgreso)
            myResponse.value = response
        }
    }
//


}