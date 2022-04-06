package com.example.kiosko_model.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kiosko_model.repository.Repository

class ComponentsViewModelFactory(
    private val repository: Repository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ComponentesViewModel(repository) as T
    }
}