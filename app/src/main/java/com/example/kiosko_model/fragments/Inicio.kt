package com.example.kiosko_model.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.kiosko_model.R
import com.example.kiosko_model.adapter.ButtonRowAdapter
import com.example.kiosko_model.databinding.InicioBinding
import com.example.kiosko_model.models.*
import com.example.kiosko_model.repository.Repository
import java.security.cert.CertPathValidatorException
import java.util.concurrent.TimeoutException


class Inicio : Fragment() {
    private lateinit var viewModel: ComponentesViewModel
    private val viewModel2: CompuestosViewModel by viewModels({requireParentFragment()})
    private val porcentajeViewModel: PorcentajeViewModel by viewModels({requireParentFragment()})


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

            val sharedPref = this.requireActivity()
                .getSharedPreferences("UsD", Context.MODE_PRIVATE)

            val id = Id(sharedPref.getString("id","defaultName")!!.toInt())
                    viewModel = ViewModelProvider(this, viewModelFactory)[ComponentesViewModel::class.java]
                    viewModel.getComponentes(id)
                    porcentajeViewModel.setId(sharedPref.getString("id","defaultName")!!.toInt())
                    val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT)

            Log.d("response ID MODULO", porcentajeViewModel.idModulo.value.toString())
            Log.d("response PORCENTAJE", porcentajeViewModel.porcentaje.value.toString())
            Log.d("RESPONSE CANTIDADMOD", porcentajeViewModel.cantidadModulos.value.toString())
                porcentajeViewModel.reset()

                    try {
                        viewModel.datos.observe(viewLifecycleOwner) { response ->

                            if (response.isSuccessful) {
                                binding.llBotonera.removeAllViews()


                                response.body()?.customModulos!!.forEach {
                                    if (it.componentes.isNullOrEmpty()&&it.submodulos.isNullOrEmpty()){
////

                                    }else{
                                        if (it.submodulos!!.isNotEmpty()){
                                            val idModulo = it.id
                                            val hijos = it.numeroHijos


                                            val tv = TextView(context)
                                            tv.text = it.titulo
                                            tv.textSize = 40F

                                            val progressBar =
                                                ProgressBar(context,
                                                    null,
                                                    0,
                                                    R.style.CustomProgressBar)

                                            //Asignamos propiedades de layout al boton
                                            progressBar.max = 100
                                            progressBar.progress = it.porcentaje


                                            llBotonera.addView(tv, lp)
                                            llBotonera.addView(progressBar)

                                            //Asignamose el Listener

                                            it.submodulos.forEach { i->
                                                if(i.componentes.isNotEmpty()){

                                                    val compuestos = i.componentes
                                                    val id = i.id
                                                    val padre = i.padre
                                                    val button = Button(context)

                                                    button.layoutParams = lp
                                                    //Asignamos Texto al botón
                                                    button.text = i.titulo
                                                    button.background = ResourcesCompat.getDrawable(
                                                        resources,
                                                        R.drawable.round_corners,
                                                        null)
//                                        button.setBackgroundColor(Color.parseColor("#19AA80"))
                                                    button.setTextColor(Color.parseColor("#FFFFFFFF"))
                                                    llBotonera.addView(button, lp)
                                                    //Asignamose el Listener

                                                    //Añadimos el botón a la botonera
                                                    button.setOnClickListener {
                                                        porcentajeViewModel.setCantidadModulos(hijos)
                                                        porcentajeViewModel.setIdModulo(idModulo)
                                                        porcentajeViewModel.setProgresoPorModulo(100)
                                                        val progresoPModulo = porcentajeViewModel.progresoPerModulo.value
                                                        val progreso = porcentajeViewModel.porcentaje.value
                                                        porcentajeViewModel.setPorcentaje(progreso!! + progresoPModulo!!)
                                                        llBotonera.removeAllViews()
                                                        viewModel2.componentes(compuestos)
                                                        viewModel2.id(id)
                                                        viewModel2.padre(padre)

                                                        findNavController().navigate(R.id.action_inicioFragment_to_contenido)


                                                    }

                                                }
                                            }
                                        }
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



