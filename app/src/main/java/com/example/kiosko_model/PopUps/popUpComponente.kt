package com.example.kiosko_model.PopUps

import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import coil.load
import com.example.kiosko_model.R
import com.example.kiosko_model.databinding.ActivityPopUpComponenteBinding

class popUpComponente : Activity() {
    private  var _binding: ActivityPopUpComponenteBinding? = null
    private  val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPopUpComponenteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.close.setOnClickListener{
            finish()
        }

        val texto = intent.extras!!.getString("texto")
        val url2 = intent.extras!!.getString("url")
//
//        val sharedPref = this
//            .getSharedPreferences("AvisoInicial", Context.MODE_PRIVATE)
//
//        val title = sharedPref.getString("title","")
//        val url = sharedPref.getString("url","")
//        val size = sharedPref.getString("size","")
        binding.titlepop.text = texto

        val imagen = binding.message
        imagen.load(url2) {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }
        hideSystemUI()
//
        val displaymetrics = DisplayMetrics()
        this.windowManager?.defaultDisplay?.getMetrics(displaymetrics)
        val height = displaymetrics.heightPixels
        val width = displaymetrics.widthPixels
        if (height > 1000 && width>600 ){

            if (height > 1400  && width>1000 ){
                val width2 = 1000
                val height2 = 1300
                window.setLayout(width2, height2)


            }else{
                val width2 = 700
                val height2 = 1100
                window.setLayout(width2, height2)
            }

        }
        else{
            val width2 = 550
            val height2 = 650
            window.setLayout(width2, height2)
        }

        val params =  WindowManager.LayoutParams()
            params.gravity= Gravity.CENTER
            params.x = 0
            params.y = - 20


    }

    fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    // Shows the system bars by removing all the flags
    // except for the ones that make the content appear under the system bars.
    fun showSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }


}