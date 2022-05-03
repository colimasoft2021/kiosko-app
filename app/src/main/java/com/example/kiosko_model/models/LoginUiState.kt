package com.example.kiosko_model.models

import android.widget.ImageView
import com.google.gson.annotations.SerializedName

data class LoginUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isUserLoggedIn: Boolean = false
)

