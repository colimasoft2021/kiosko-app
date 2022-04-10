package com.example.kiosko_model.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kiosko_model.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginRegistroViewModel(private val repository: Repository): ViewModel() {

    val myResponse: MutableLiveData<Response<List<LoginR>>> = MutableLiveData()
    val myResponse2: MutableLiveData<Response<List<LoginResponseR>>> = MutableLiveData()
    val stats: MutableLiveData<String> = MutableLiveData()

    fun pushPost(post: Post){
        viewModelScope.launch {
            val response = repository.pushPost(post)
            myResponse.value = response
        }
    }

    fun pushPostRegistro(postRegistro: PostRegistro){
        viewModelScope.launch {
            val response = repository.pushPostRegistro(postRegistro)
            myResponse2.value = response
        }
    }
//


}