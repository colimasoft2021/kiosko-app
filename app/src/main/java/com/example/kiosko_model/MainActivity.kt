package com.example.kiosko_model

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.kiosko_model.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val sharedPref = getSharedPreferences("UsD", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("userName", "" )
        editor.putString("userID", "" )
        editor.apply()

    }


}