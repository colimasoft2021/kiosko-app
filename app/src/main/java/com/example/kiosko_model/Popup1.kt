package com.example.kiosko_model

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.WindowManager
import com.example.kiosko_model.databinding.ActivityPopup1Binding

class Popup1 : Activity() {
    private  var _binding: ActivityPopup1Binding? = null
    private  val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPopup1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.close.setOnClickListener{
            finish()
        }



        val width2 = 500
        val height2 = 650
            window.setLayout(width2, height2)

//        val params =  WindowManager.LayoutParams()
//            params.gravity= Gravity.CENTER
//            params.x = 0
//            params.y = - 20
//

    }
}