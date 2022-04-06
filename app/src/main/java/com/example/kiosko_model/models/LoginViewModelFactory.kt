package com.example.kiosko_model.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kiosko_model.repository.Repository

class LoginViewModelFactory(
    private val repository: Repository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(repository) as T
    }


}