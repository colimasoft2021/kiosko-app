package com.example.kiosko_model.fragments

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.kiosko_model.Home
import com.example.kiosko_model.MainActivity
import com.example.kiosko_model.R
import com.example.kiosko_model.databinding.FragmentAccesoDirectoBinding


class accesoDirectoFragment : Fragment() {
    private var _binding: FragmentAccesoDirectoBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccesoDirectoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val color = Color.parseColor("#FC4C02")
        val radius = 15//radius will be 5px
        val graDient = GradientDrawable()
        graDient.setColor(color)
        graDient.cornerRadius = radius.toFloat()

        val vistaTitle = binding.titleContainer

        val titulo = Button(context)
        titulo.text = getString(R.string.material_de_apoyo)
        titulo.textSize = 30f
        titulo.background = graDient
        titulo.gravity = Gravity.CENTER_HORIZONTAL
        titulo.setTextColor(Color.WHITE)
        val layoutTitulo = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        layoutTitulo.setMargins(20, 20, 20, 20)

        vistaTitle.addView(titulo, layoutTitulo)

        val texto = TextView(context)
        texto.text = getString(R.string.con_la_finalidad_de_facilitar_tu_aprendizaje)
        texto.textSize = 18f
        texto.textAlignment = TextView.TEXT_ALIGNMENT_TEXT_START
        texto.setTextColor(Color.BLACK)
        val layoutTexto = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        layoutTexto.setMargins(20, 5, 20, 20)

        val  videosBorde = binding.videosb
        val  videos = binding.videos
        val  guiasBorde = binding.guiasb
        val  guias = binding.guias

//        videosBorde.isEnabled = false
//        videos.isEnabled = false
//        guiasBorde.isEnabled = false
//        guias.isEnabled = false


        vistaTitle.addView(texto, layoutTexto)










        videosBorde.setOnClickListener {
            findNavController().navigate(R.id.action_accesoDirectoFragment_to_videosFragment)
        }
        videos.setOnClickListener {
            findNavController().navigate(R.id.action_accesoDirectoFragment_to_videosFragment)
        }

        guiasBorde.setOnClickListener {
            findNavController().navigate(R.id.action_accesoDirectoFragment_to_guiasFragment)
        }
        guias.setOnClickListener {
            findNavController().navigate(R.id.action_accesoDirectoFragment_to_guiasFragment)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun wifi() {


        var data = false

        if ((activity as Home?)?.isNetDisponible() == true) {
            if ((activity as Home?)?.isOnlineNet() == true) {
                (activity as Home?)?.checkConnectivity()
            data = true

            }
        } else {
            (activity as Home?)?.checkConnectivity()
            data = false

        }


    }
}