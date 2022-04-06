package com.example.kiosko_model.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.navigation.fragment.findNavController
import com.example.kiosko_model.R
import com.example.kiosko_model.databinding.WelcomeBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class Welcome : Fragment() {

    private var _binding: WelcomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = WelcomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.start.setOnClickListener {
            findNavController().navigate(R.id.action_Welcome_to_Login)
        }
   }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}