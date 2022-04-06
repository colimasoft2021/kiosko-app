package com.example.kiosko_model

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavDeepLinkBuilder
import com.example.kiosko_model.databinding.ActivityRememberBinding

class Remember : AppCompatActivity() {

    private lateinit var binding: ActivityRememberBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRememberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()


        binding.start.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }

    }

}