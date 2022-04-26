package com.example.kiosko_model.PopUps

import android.content.Intent
import android.media.MediaPlayer.OnCompletionListener
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.kiosko_model.databinding.ActivityPopUpComponenteVideoBinding


class popUpComponenteVideo : AppCompatActivity() {
        private  var _binding: ActivityPopUpComponenteVideoBinding? = null
        private  val binding get() = _binding!!

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            _binding = ActivityPopUpComponenteVideoBinding.inflate(layoutInflater)
            setContentView(binding.root)



            val texto = intent.extras!!.getString("texto")
            val url2 = intent.extras!!.getString("url")
//
//        val sharedPref = this
//            .getSharedPreferences("AvisoInicial", Context.MODE_PRIVATE)
//
//        val title = sharedPref.getString("title","")
//        val url = sharedPref.getString("url","")
//        val size = sharedPref.getString("size","")
            binding.titlepopV.text = texto

            val video = binding.messageV

            val uri = Uri.parse(url2)

            val mediaController = MediaController(this)
            video.setVideoURI(uri)
            video.setMediaController(mediaController)
            hideSystemUI()
//
//        val width2 = 500
//        val height2 = 650
//            window.setLayout(width2, height2)

//        val params =  WindowManager.LayoutParams()
//            params.gravity= Gravity.CENTER
//            params.x = 0
//            params.y = - 20
//

            video.setOnCompletionListener(OnCompletionListener { //Termina reproduccion,
                //Realiza Intent.
                binding.closeV.setOnClickListener{
                    finish()
                }
            })

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

