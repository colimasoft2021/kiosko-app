package com.example.kiosko_model.PopUps

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kiosko_model.databinding.ActivityPopup2Binding

class Popup2 : Activity() {
    private  var _binding: ActivityPopup2Binding? = null
    private  val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPopup2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.close.setOnClickListener{
            finish()
        }

        val width2 = 500
        val height2 = 500
        window.setLayout(width2, height2)

//        val params =  WindowManager.LayoutParams()
//            params.gravity= Gravity.CENTER
//            params.x = 0
//            params.y = - 20
//

    }
}