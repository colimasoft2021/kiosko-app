package com.example.kiosko_model.models

//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.kiosko_model.network.loginApi
//import kotlinx.coroutines.launch
//
//class LoginModel : ViewModel() {
//
//    // The internal MutableLiveData that stores the status of the most recent request
//    private val _status = MutableLiveData<String>()
//    // The external immutable LiveData for the request status
//    val status: LiveData<String> = _status
//
//    init {
//        loginData()
//    }
//
//
//    private fun loginData() {
//        viewModelScope.launch {
//            val listResult = loginApi.retrofitService.getLogin()
//            _status.value = listResult
//        }
//    }
//}