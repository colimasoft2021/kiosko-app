package com.example.kiosko_model.PopUps

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kiosko_model.databinding.ActivityLoadBinding

class Load : AppCompatActivity() {
    private var _binding: ActivityLoadBinding? = null
    private val binding get() = _binding!!
    lateinit var dialog : AlertDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoadBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val width2 = 500
        val height2 = 650
        window.setLayout(width2, height2)
        window
    }
}