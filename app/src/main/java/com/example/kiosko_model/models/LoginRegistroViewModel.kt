package com.example.kiosko_model.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kiosko_model.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginRegistroViewModel(private val repository: Repository): ViewModel() {

    val myResponse: MutableLiveData<Response<LoginResponseR>> = MutableLiveData()
    val stats: MutableLiveData<String> = MutableLiveData()

    fun pushPostRegistro(postRegistro: PostRegistro){
        viewModelScope.launch {
            val response = repository.pushPostRegistro(postRegistro)
            myResponse.value = response
        }
    }
//


}