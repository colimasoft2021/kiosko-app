package com.example.kiosko_model.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.kiosko_model.databinding.FragmentNotificacionesBinding
import android.app.Activity
import com.example.kiosko_model.*


class notificacionesFragment : Fragment() {
    private var _binding: FragmentNotificacionesBinding? = null
    private var a = 0

    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState : Bundle?
    ) : View? {
        _binding = FragmentNotificacionesBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.CLICK.setOnClickListener {
            val act = activity as Home?
            act?.setNotifications(a)//

            act?.notifications()//
//
        }
        binding.CLICKEA.setOnClickListener{
            val activity: Activity? = activity
            a++
            val toast = Toast.makeText(activity, a.toString(), Toast.LENGTH_SHORT)
            toast.show()
        }
//        binding.CLICKs.setOnClickListener{
//            val d  = sharedViewModel.notify.value?.toInt()
//            val z = Toast.makeText(activity, d.toString(), Toast.LENGTH_SHORT)
//            z.show()
//        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}