package com.example.kiosko_model.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.kiosko_model.R
import com.example.kiosko_model.databinding.FragmentAccesoDirectoBinding


class accesoDirectoFragment : Fragment() {
        private var _binding: FragmentAccesoDirectoBinding? = null

        private val binding get() = _binding!!

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState : Bundle?
        ) : View? {
            _binding = FragmentAccesoDirectoBinding.inflate(inflater,container,false)

            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            binding.videosBorde.setOnClickListener {
                findNavController().navigate(R.id.action_accesoDirectoFragment_to_videosFragment)
            }
            binding.videos.setOnClickListener {
                findNavController().navigate(R.id.action_accesoDirectoFragment_to_videosFragment)
            }

            binding.guiasBorde.setOnClickListener {
                findNavController().navigate(R.id.action_accesoDirectoFragment_to_guiasFragment)
            }
            binding.guias.setOnClickListener {
                findNavController().navigate(R.id.action_accesoDirectoFragment_to_guiasFragment)
            }


        }
        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
    }