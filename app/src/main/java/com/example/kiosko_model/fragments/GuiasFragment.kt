package com.example.kiosko_model.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.kiosko_model.Home
import com.example.kiosko_model.R
import com.example.kiosko_model.databinding.FragmentGuiasBinding
import com.example.kiosko_model.databinding.FragmentVideosBinding
import com.example.kiosko_model.models.*
import com.example.kiosko_model.repository.Repository
import java.util.concurrent.TimeoutException

private lateinit var viewModel: GuiasViewModel

class GuiasFragment : Fragment() {

    private var _binding: FragmentGuiasBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentGuiasBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val VideosLista = binding.GuiasRapidasLista
        binding.GuiasRapidasLista.removeAllViews()

        val repository = Repository()
        val viewModelFactory = GuiasViewModelFactory(repository)


        viewModel = ViewModelProvider(this, viewModelFactory)[GuiasViewModel::class.java]
        viewModel.getGuias()
        try {
            viewModel.guiasResponse.observe(viewLifecycleOwner) { response ->

                binding.GuiasRapidasLista.removeAllViews()

                if (response.isSuccessful) {
                    response.body()?.forEach {
                        val boton = Button(context)
                        boton.text = it.titulo
                        val componenetes = it.componentes

                        boton.setOnClickListener {
                            viewModel.componentes(componenetes)
                            findNavController().navigate(R.id.action_guiasFragment_to_guiasContenido)


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