package com.example.kiosko_model.PopUps

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import coil.load
import com.example.kiosko_model.R
import com.example.kiosko_model.databinding.ActivityPopUpComponenteVideoBinding

class popUpComponenteVideo : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pop_up_componente_video)

        val texto = intent.extras!!.getString("texto")
        val url = intent.extras!!.getString("url")


        val width2 = 500
        val height2 = 650
        window.setLayout(width2, height2)
        val uri = Uri.parse(url)
        val textoP = findViewById(R.id.textoPopUp) as TextView

        val video =findViewById(R.id.videoPopUp) as VideoView

        val mediaController = MediaController(this)
        video.setVideoURI(uri)
        video.setMediaController(mediaController)
        textoP.text=texto
    }


}