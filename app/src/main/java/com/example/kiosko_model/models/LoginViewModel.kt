package com.example.kiosko_model.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kiosko_model.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel(private val repository: Repository): ViewModel() {

    val myResponse: MutableLiveData<Response<List<LoginR>>> = MutableLiveData()
    val error: MutableLiveData<Throwable> = MutableLiveData()

    fun pushPost(post: Post){
        viewModelScope.launch {
            try{
                val response = repository.pushPost(post)
                myResponse.value = response
            }catch(e:Throwable) {
                error.value = e
            }
        }
    }


//


}