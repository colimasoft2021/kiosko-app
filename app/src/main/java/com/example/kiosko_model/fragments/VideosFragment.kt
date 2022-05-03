package com.example.kiosko_model.fragments

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.kiosko_model.Home
import com.example.kiosko_model.R
import com.example.kiosko_model.databinding.FragmentVideosBinding
import com.example.kiosko_model.databinding.InicioBinding
import com.example.kiosko_model.models.*
import com.example.kiosko_model.repository.Repository
import com.google.android.material.progressindicator.LinearProgressIndicator
import java.util.concurrent.TimeoutException

private lateinit var viewModel: VideosViewModel

class VideosFragment : Fragment() {


    private var _binding: FragmentVideosBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentVideosBinding.inflate(inflater, container, false)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val VideosLista = binding.VideosLista
        binding.VideosLista.removeAllViews()

        val repository = Repository()
        val viewModelFactory = VideosViewModelFactory(repository)


        viewModel = ViewModelProvider(this, viewModelFactory)[VideosViewModel::class.java]
        viewModel.getVideos()
        try {
            viewModel.videosResponse.observe(viewLifecycleOwner) { response ->

                binding.VideosLista.removeAllViews()

                if (response.isSuccessful) {
                    response.body()?.forEach {
                        val boton = Button(context)
                        boton.text = it.descripcion ?: "Descripcion"

                        val descripcion = it.descripcion ?: "Descripcion"
                        Log.d("aaaaaaaaaaa" , it.toString())
                        val url = it.url
                        boton.setOnClickListener {

                            (activity as Home?) ?.PopUpComponenteVideo(descripcion,url,mensajeInicial = false)

                        }
                        VideosLista.addView(boton)
                    }



                } else {
                    Toast.makeText(context, "algo salio mal", Toast.LENGTH_SHORT).show()

                }

            }

        } catch (e: TimeoutException) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()

        }

    }
}