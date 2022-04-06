package com.example.kiosko_model.fragments

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kiosko_model.databinding.InicioBinding
import com.example.kiosko_model.models.ComponentesViewModel
import com.example.kiosko_model.models.ComponentsViewModelFactory
import com.example.kiosko_model.repository.Repository
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.kiosko_model.R

import com.example.kiosko_model.adapter.ButtonRowAdapter
import com.example.kiosko_model.models.CompuestosViewModel
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.security.cert.CertPath
import java.security.cert.CertPathValidatorException
import java.util.concurrent.TimeoutException


class Inicio : Fragment() {
    private lateinit var viewModel: ComponentesViewModel
    private val viewModel2: CompuestosViewModel by viewModels({requireParentFragment()})

    private val buttonRowAdapter by lazy { ButtonRowAdapter()}

    private var _binding: InicioBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = InicioBinding.inflate(inflater, container, false)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //Creamos los botones en bucle

        //Creamos los botones en bucle
//        try{
//            try {


                    val llBotonera = binding.llBotonera
                    binding.llBotonera.removeAllViews()

                    val repository = Repository()
                    val viewModelFactory = ComponentsViewModelFactory(repository)
        try{

                    viewModel = ViewModelProvider(this, viewModelFactory)[ComponentesViewModel::class.java]
                    viewModel.getComponentes()
                    val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT)

                    try {
                        viewModel.datos.observe(viewLifecycleOwner) { response ->

                            if (response.isSuccessful) {
                                binding.llBotonera.removeAllViews()
//                    response.body()?.let { buttonRowAdapter.setData(it) }

                                response.body()?.forEach {
                                    if (it.componentes.isNullOrEmpty()){

                                        val tv = TextView(context)
                                        //Asignamos propiedades de layout al boton
                                        //Asignamos Texto al botón
                                        tv.text = it.titulo
                                        llBotonera.addView(tv, lp)
                                        //Asignamose el Listener

                                        if(it.padre.isNullOrEmpty()){
                                            tv.textSize = 40F
//                                            val tv1 = TextView(context)
//                                            //Asignamos propiedades de layout al boton
//                                            tv1.textSize = 40F
//                                            //Asignamos Texto al botón
//                                            tv1.text = it.titulo
//                                            llBotonera.addView(tv1, lp)
//                                            //Asignamose el Listener

                                        }else{
                                            tv.textSize = 20F
                                        }


                                    }else{
//                                        if (it.padre.isNullOrEmpty()) {
                                        val compuestos = it.componentes
                                        val id = it.id
                                        val padre = it.padre
//                            val button = binding.botones
                                            val button = Button(context)
                                            val progressBar =
                                                ProgressBar(context,
                                                    null,
                                                    0,
                                                    R.style.CustomProgressBar)

                                            //Asignamos propiedades de layout al boton
                                            progressBar.max = 1000
                                            button.layoutParams = lp
                                            //Asignamos Texto al botón
                                            button.text = it.titulo
                                            button.background = ResourcesCompat.getDrawable(
                                                resources,
                                                R.drawable.round_corners,
                                                null)
//                                        button.setBackgroundColor(Color.parseColor("#19AA80"))
                                            button.setTextColor(Color.parseColor("#FFFFFFFF"))
                                            progressBar.progress = 500
                                            llBotonera.addView(button, lp)
                                            llBotonera.addView(progressBar)
                                            //Asignamose el Listener

                                            //Añadimos el botón a la botonera
                                            button.setOnClickListener {

                                                llBotonera.removeAllViews()
                                                viewModel2.componentes(compuestos)
                                                viewModel2.id(id)
                                                viewModel2.padre(padre)
                                                findNavController().navigate(R.id.action_inicioFragment_to_contenido)
                                            }
//                                        }
                                    }
                                }
                            } else {
                                Toast.makeText(context, "algo salio mal", Toast.LENGTH_SHORT).show()

                            }

                        }
                    } catch (e: TimeoutException) {
                        Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()

                    }
                } catch (e: CertPathValidatorException) {
                    Toast.makeText(context, "Falta certificado SSL  $e", Toast.LENGTH_SHORT)
                        .show()
                    Log.d("Falta certificado SSL", e.toString())
                }
//            } catch (e: ConnectException) {
//                Toast.makeText(context, "Falta certificado Conexion banda $e", Toast.LENGTH_SHORT)
//                    .show()
//                Log.d("Falta conexion SSL", e.toString())
//            }
//        }catch(e: SocketTimeoutException){
//            Toast.makeText(context, " SocketTimeoutException $e", Toast.LENGTH_SHORT)
//                .show()
//            Log.d("SocketTimeoutException", e.toString())
//
//        }

    }

//    private fun setupRecyclerview(){
//        binding.recyclerViewBotones.adapter = buttonRowAdapter
//        binding.recyclerViewBotones.layoutManager = LinearLayoutManager(context)
//    }

    override fun onPause() {
        super.onPause()
        binding.llBotonera.removeAllViews()
    }

    fun refreshFragment(context: Context?){

        context?.let {
            val fragmentManager = (context as? AppCompatActivity)?.supportFragmentManager
            fragmentManager?.let {
                val  currentFragment = fragmentManager.findFragmentById(R.id.inicioFragment)
                currentFragment?.let {
                    val fragmentTransaction =fragmentManager.beginTransaction()
                    fragmentTransaction.detach(it)
                    fragmentTransaction.attach(it)
                    fragmentTransaction.commit()
                }
            }
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }


}



