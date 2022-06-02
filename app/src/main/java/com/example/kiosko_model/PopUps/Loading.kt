package com.example.kiosko_model.PopUps

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import com.example.kiosko_model.databinding.LoadingBinding

class Loading : Activity() {
    private  var _binding: LoadingBinding? = null
    private  val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = LoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)



//        val height2 = 500
//        val width2 = 500
//        window.setLayout(width2, height2)
//
        val params =  WindowManager.LayoutParams()
            params.gravity= Gravity.CENTER
//            params.x = 0
//            params.y = - 20
//

    }
}