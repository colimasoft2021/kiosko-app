package com.example.kiosko_model.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kiosko_model.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class VideosViewModel(private val repository: Repository): ViewModel() {

//    val componentesResponse: MutableLiveData<Response<List<Componentes>>>  = MutableLiveData()
    private val _videosResponse = MutableLiveData<Response<List<Videos>>>()
    val videosResponse: LiveData <Response<List<Videos>>> = _videosResponse

//    private val _datos = MutableLiveData<Response<customModulos>>()
//    val datos: LiveData <Response<customModulos>> = _datos


    private val mutable = MutableLiveData<List<Componentes?>>()
    val componentes: LiveData<List<Componentes?>> get() = mutable

    fun componentes(componentes:List<Componentes?>){
        mutable.value = componentes
    }



    fun getVideos(){
        viewModelScope.launch {
            val response = repository.getVideos()
            _videosResponse.value = response
        }
    }
//

}