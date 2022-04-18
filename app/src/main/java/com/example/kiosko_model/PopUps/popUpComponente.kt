package com.example.kiosko_model.PopUps

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.kiosko_model.R
import com.example.kiosko_model.databinding.ActivityPopUpComponenteBinding

class popUpComponente : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pop_up_componente)

        val texto = intent.extras!!.getString("texto")
        val url = intent.extras!!.getString("url")


        val width2 = 500
        val height2 = 650
        window.setLayout(width2, height2)
        val textoP = findViewById(R.id.textoPopUp) as TextView

        val imagen  = findViewById(R.id.imagenPopUp) as ImageView
        imagen.load(url) {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }
        textoP.text=texto

    }


}