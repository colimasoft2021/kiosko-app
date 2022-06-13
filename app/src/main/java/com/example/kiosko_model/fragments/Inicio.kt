package com.example.kiosko_model.fragments

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.graphics.fonts.FontFamily
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.kiosko_model.Home
import com.example.kiosko_model.R
import com.example.kiosko_model.adapter.ButtonRowAdapter
import com.example.kiosko_model.databinding.InicioBinding
import com.example.kiosko_model.models.*
import com.example.kiosko_model.repository.Repository
import com.google.android.material.progressindicator.LinearProgressIndicator
import java.security.cert.CertPathValidatorException
import java.util.concurrent.TimeoutException


class Inicio : Fragment() {
    private lateinit var viewModel: ComponentesViewModel
//    private lateinit var avisoViewModel: MensajeInicialViewModel
    private val viewModel2: CompuestosViewModel by viewModels({requireParentFragment()})
    private val viewModel3: CompuestosViewModel2 by viewModels({requireParentFragment()})
    private val viewModelLocal: ComponentesViewModel2 by viewModels({requireParentFragment()})
    private val porcentajeViewModel: PorcentajeViewModel by viewModels({requireParentFragment()})
    private lateinit var avisoViewModel: MensajeInicialViewModel



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
        val displaymetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displaymetrics)
        val height = displaymetrics.heightPixels
        val width = displaymetrics.widthPixels



        val llBotoneraConProgreso = binding.llBotoneraConProgreso
        binding.llBotoneraConProgreso.removeAllViews()
        val llBotonera = binding.llBotonera
        binding.llBotonera.removeAllViews()




            val sharedPref = this.requireActivity()
                .getSharedPreferences("UsD", Context.MODE_PRIVATE)

            val pv = sharedPref.getBoolean("primer", false)

            val id = Id(sharedPref.getString("id","defaultName")!!.toInt())


            porcentajeViewModel.reset()

            Log.d("response ID MODULO", porcentajeViewModel.idModulo.value.toString())
            Log.d("response PORCENTAJE", porcentajeViewModel.porcentaje.value.toString())
            Log.d("RESPONSE CANTIDADMOD", porcentajeViewModel.cantidadModulos.value.toString())


            try {

                val repository = Repository()
                val viewModelFactory = ComponentsViewModelFactory(repository)

                porcentajeViewModel.setId(sharedPref.getString("id","defaultName")!!.toInt())
                viewModel = ViewModelProvider(this, viewModelFactory)[ComponentesViewModel::class.java]
                viewModel.getComponentes(id)
                viewModel.datos.observe(viewLifecycleOwner) { response ->

                    if (response.isSuccessful ) {
                        binding.llBotoneraConProgreso.removeAllViews()
                        binding.llBotonera.removeAllViews()

                        val lltextHardCoded = LinearLayout(context)
                        lltextHardCoded.orientation = LinearLayout.VERTICAL
                        lltextHardCoded.gravity = Gravity.CENTER_VERTICAL

                        val TextHardCodedTitle = TextView(context)
                        TextHardCodedTitle.text = "Inicia tu capacitación"
                        TextHardCodedTitle.setTextColor(Color.BLACK)
                        TextHardCodedTitle.textSize = 24F
                        TextHardCodedTitle.gravity = Gravity.LEFT
                        TextHardCodedTitle.typeface = Typeface.DEFAULT_BOLD

                        val lpTextHardCodedTitle = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                        lpTextHardCodedTitle.setMargins(0,15,0,5)


                        val TextHardCodedSubTitle = TextView(context)
                        TextHardCodedSubTitle.text = "Elige el apartado con el que quieres comenzar"
                        TextHardCodedSubTitle.setTextColor(Color.BLACK)
                        TextHardCodedSubTitle.textSize = 16F
                        TextHardCodedSubTitle.gravity = Gravity.LEFT

                        val lpTextHardCodedSubTitle = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                        lpTextHardCodedSubTitle.setMargins(0,10,0,15)

                        lltextHardCoded.addView(TextHardCodedTitle,lpTextHardCodedTitle)
                        lltextHardCoded.addView(TextHardCodedSubTitle,lpTextHardCodedSubTitle)

                        val lltextHardCodedProgress = LinearLayout(context)
                        lltextHardCodedProgress.orientation = LinearLayout.VERTICAL
                        lltextHardCodedProgress.gravity = Gravity.CENTER_VERTICAL

                        val TextHardCodedProgres = TextView(context)
                        TextHardCodedProgres.text = "Continuar con tu progreso actual"
                        TextHardCodedProgres.setTextColor(Color.BLACK)
                        TextHardCodedProgres.textSize = 24F
                        TextHardCodedProgres.gravity = Gravity.LEFT
                        TextHardCodedProgres.typeface = Typeface.DEFAULT_BOLD

                        val lpTextHardCodedProgres = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                        lpTextHardCodedProgres.setMargins(0,15,0,30)

                        lltextHardCodedProgress.addView(TextHardCodedProgres,lpTextHardCodedProgres)


                        val LinearHorizontalBotones = LinearLayout(context)
                        val scroll = HorizontalScrollView(context)

                        val contenedorProgresoScroll = LinearLayout(context)
                        contenedorProgresoScroll.orientation = LinearLayout.VERTICAL
                        contenedorProgresoScroll.gravity = Gravity.CENTER




                        val scrollPorcentaje = ScrollView(context)

                        val lpBotonProgreso = LinearLayout.LayoutParams(150,100)
                        lpBotonProgreso.setMargins(20,20,20,20)

                        response.body()?.customModulos!!.forEach {
                            if (it.componentes.isNullOrEmpty()&&it.submodulos.isNullOrEmpty()){

                            }else{
                                if (it.submodulos!!.isNotEmpty()){
                                    val idModulo = it.id
                                    val hijos = it.numeroHijos

                                    val colorBotonFondo = it.backgroundColor ?: "#F000000"

                                    val compuestos = it.submodulos[0].componentes
                                    val idSubmodulo = it.submodulos[0].id
                                    val submodulo = it.submodulos
                                    val padre = it.idModulo
                                    val hijo = it.numeroHijos
                                    val index = 1
                                    val imagenBoton = it.url

                                    val linearLayout = LinearLayout(context)
                                    linearLayout.orientation = LinearLayout.HORIZONTAL
                                    linearLayout.gravity = Gravity.CENTER_VERTICAL

                                    LinearHorizontalBotones.orientation = LinearLayout.HORIZONTAL
                                    LinearHorizontalBotones.gravity = Gravity.CENTER_VERTICAL

                                    val LinearHorizontalBotonera = LinearLayout(context)
                                    LinearHorizontalBotonera.orientation = LinearLayout.HORIZONTAL
                                    LinearHorizontalBotonera.gravity = Gravity.CENTER_VERTICAL

                                    val col = Color.WHITE
                                    val rad = 15//radius will be 5px
                                    val strk = 5
                                    val gD = GradientDrawable()
                                    gD.setColor(col)
                                    gD.cornerRadius = rad.toFloat()
                                    gD.setStroke(strk, col)

                                    val contenedorBotonProgreso = LinearLayout(context)
                                    contenedorBotonProgreso.orientation = LinearLayout.VERTICAL
                                    contenedorBotonProgreso.gravity = Gravity.CENTER
                                    contenedorBotonProgreso.background = gD


                                    val contenedorBotonera = LinearLayout(context)
                                    contenedorBotonera.orientation = LinearLayout.VERTICAL
                                    contenedorBotonera.gravity = Gravity.CENTER
                                    contenedorBotonera.background = gD

                                    val color = Color.parseColor(colorBotonFondo)
                                    val radius = 20//radius will be 5px
                                    val grD = GradientDrawable()
                                    grD.setColor(color)
                                    grD.cornerRadius = rad.toFloat()

                                    val botoneraHorizontalProgreso = Button(context)
                                        botoneraHorizontalProgreso.background=grD
//                                    botoneraHorizontalProgreso.setBackgroundColor(Color.parseColor(colorBotonFondo))

//

                                    botoneraHorizontalProgreso.setOnClickListener {

                                        porcentajeViewModel.setCantidadModulos(hijos)
                                        porcentajeViewModel.setIdModulo(idModulo)
                                        porcentajeViewModel.setProgresoPorModulo(100)
                                        val progresoPModulo = porcentajeViewModel.progresoPerModulo.value
                                        val progreso = porcentajeViewModel.porcentaje.value
                                        porcentajeViewModel.setPorcentaje(progreso!! + progresoPModulo!!)
                                        llBotoneraConProgreso.removeAllViews()
                                        llBotonera.removeAllViews()
                                        viewModel2.componentes(compuestos)
                                        viewModel2.id(idSubmodulo)
                                        viewModel2.padre(padre)
                                        viewModel2.colorModulo(color)
                                        viewModel3.padre(padre)
                                        viewModelLocal.hijos(hijo)
                                        viewModelLocal.index(index)
                                        viewModelLocal.componentes2(submodulo)

                                        if((activity as Home?)?.isOnlineNet() == true) {
                                            findNavController().navigate(R.id.action_inicioFragment_to_contenido)
                                        }
                                    }



                                    val tituloBotonProgres = TextView(context)
                                    botoneraHorizontalProgreso.text = it.titulo
                                    botoneraHorizontalProgreso.setTextColor(Color.WHITE)
                                    botoneraHorizontalProgreso.textSize = 18F
                                    botoneraHorizontalProgreso.gravity = Gravity.CENTER
                                    botoneraHorizontalProgreso.isAllCaps = false

                                    val botoneraHorizontal = Button(context)

                                    botoneraHorizontal.background=grD
//                                    botoneraHorizontal.setBackgroundColor(Color.parseColor(colorBotonFondo))

                                    botoneraHorizontal.setOnClickListener {
                                        porcentajeViewModel.setCantidadModulos(hijos)
                                        porcentajeViewModel.setIdModulo(idModulo)
                                        porcentajeViewModel.setProgresoPorModulo(100)
                                        val progresoPModulo = porcentajeViewModel.progresoPerModulo.value
                                        val progreso = porcentajeViewModel.porcentaje.value
                                        porcentajeViewModel.setPorcentaje(progreso!! + progresoPModulo!!)
                                        llBotoneraConProgreso.removeAllViews()
                                        llBotonera.removeAllViews()
                                        viewModel2.componentes(compuestos)
                                        viewModel2.id(idSubmodulo)
                                        viewModel2.padre(padre)
                                        viewModel2.colorModulo(color)
                                        viewModel3.padre(padre)
                                        viewModelLocal.hijos(hijo)
                                        viewModelLocal.index(index)
                                        viewModelLocal.componentes2(submodulo)

                                        if((activity as Home?)?.isOnlineNet() == true) {
                                            findNavController().navigate(R.id.action_inicioFragment_to_contenido)
                                        }

                                    }

                                    val tituloBotoneraProgres = TextView(context)
                                    botoneraHorizontal.text = it.titulo
                                    botoneraHorizontal.setTextColor(Color.WHITE)
                                    botoneraHorizontal.textSize = 18F
                                    botoneraHorizontal.gravity = Gravity.CENTER
                                    botoneraHorizontal.isAllCaps = false

                                    val contenedorProgreso = LinearLayout(context)
                                    contenedorProgreso.orientation = LinearLayout.VERTICAL
                                    contenedorProgreso.gravity = Gravity.CENTER

                                    val progresoDEText = TextView(context)
                                    progresoDEText.text = "Llevas un progreso de:"
                                    progresoDEText.setTextColor(Color.BLACK)
                                    progresoDEText.textSize = 15F
                                    progresoDEText.gravity = Gravity.LEFT

                                    val porc = it.porcentaje.toString()
                                    val progreso = TextView(context)
                                    progreso.text = "$porc%"
                                    progreso.setTextColor(Color.BLACK)
                                    progreso.textSize = 15F
                                    progreso.gravity = Gravity.CENTER

                                    val progressBar =
                                        LinearProgressIndicator(requireContext())
//
                                    progressBar.max = 100
                                    progressBar.progress = it.porcentaje
                                    progressBar.trackThickness = 20
                                    progressBar.trackCornerRadius = 10
                                    progressBar.trackColor = Color.parseColor("#47D2FE")
                                    progressBar.setIndicatorColor( Color.parseColor("#014358"))

                                    val lpProgreso = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,  LinearLayout.LayoutParams.WRAP_CONTENT)
                                    lpProgreso.setMargins(20,0,20,0)

                                    val lpProgresoTitulo = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,  LinearLayout.LayoutParams.WRAP_CONTENT)
                                    lpProgresoTitulo.setMargins(30,10,40,15)

                                    val lpProgresoPorcentaje = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,  LinearLayout.LayoutParams.WRAP_CONTENT)
                                    lpProgresoPorcentaje.setMargins(30,10,40,10)

                                    val lpProgresoContenedor = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,  LinearLayout.LayoutParams.WRAP_CONTENT)
                                    lpProgresoContenedor.setMargins(20,20,30,50)

                                    val lpProgresoBotonContenedor = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,  LinearLayout.LayoutParams.WRAP_CONTENT)
                                    lpProgresoContenedor.setMargins(0,0,0,0)

                                    val lpContenedorBotonera = LinearLayout.LayoutParams(260, 230)
                                    lpContenedorBotonera.setMargins(15,30,20,20)

                                    val lpBotonProgreso = LinearLayout.LayoutParams(240,210)
                                    lpBotonProgreso.setMargins(20,20,20,20)

                                    val lpTituloBotonProgres = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                                    lpTituloBotonProgres.setMargins(0,10,0,20)





                                    contenedorBotonera.addView(botoneraHorizontal,lpBotonProgreso)
//                                    contenedorBotonera.addView(tituloBotoneraProgres,lpProgresoBotonContenedor)
                                    LinearHorizontalBotones.addView(contenedorBotonera,lpContenedorBotonera)



                                    contenedorBotonProgreso.addView(botoneraHorizontalProgreso,lpBotonProgreso)
//                                    contenedorBotonProgreso.addView(tituloBotonProgres,lpProgresoBotonContenedor)
                                    linearLayout.addView(contenedorBotonProgreso,lpContenedorBotonera)



                                    contenedorProgreso.addView(progresoDEText,lpProgresoTitulo)
                                    contenedorProgreso.addView(progreso,lpProgresoPorcentaje)
                                    contenedorProgreso.addView(progressBar,lpProgreso)
                                    linearLayout.addView(contenedorProgreso,lpProgresoContenedor)

                                    contenedorProgresoScroll.addView(linearLayout,lpTituloBotonProgres)

                                }
                            }
                        }

                        scroll.addView(LinearHorizontalBotones)
                        lltextHardCoded.addView(scroll)
                        llBotonera.addView(lltextHardCoded)
                        //checar compatibilidad con tablets menores de 600 px

                        var scrollLP : LinearLayout.LayoutParams


                        Log.d("height",height.toString())
                        Log.d("width",width.toString())

                        if (height > 1000 && width>600 ){

                            if (height > 1400  && width>1000 ){
                                val height2 = 1300
                                 scrollLP = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height/3 )


                            }else{
                                val height2 = 1100
                                 scrollLP = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height/4 )
                            }

                        }
                        else{
                            val height2 = 650
                             scrollLP = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height/2 )
                        }

                        scrollLP.setMargins(10,10,20,20)

                        //                            margen erroneo, no visible progreso completo
                        val scrollLPContenedor = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT)
                        scrollLPContenedor.setMargins(15,10,20,0)

//                                scrollPorcentaje.layoutParams = contenedorProgresoScroll.layoutParams
                        scrollPorcentaje.addView(contenedorProgresoScroll )

                        lltextHardCodedProgress.addView(scrollPorcentaje, scrollLP)

//                            margen erroneo, no visible progreso completo

                        llBotoneraConProgreso.addView(lltextHardCodedProgress,scrollLPContenedor)

                    } else {
                        Toast.makeText(context, "algo salio mal", Toast.LENGTH_SHORT).show()

                    }

                }
            } catch (e: Exception) {
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()

            } finally {

//                Toast.makeText(context, "terminado", Toast.LENGTH_SHORT).show()


                val repo = Repository()
                val viewModelFactoryAviso = MensajeInicialViewModelFactory(repo)

                avisoViewModel =  ViewModelProvider(this, viewModelFactoryAviso)[MensajeInicialViewModel::class.java]
                avisoViewModel.getAvisoInicial()


                Log.d("PV", pv.toString())

                if(pv){

                    try {
                        avisoViewModel.AvisoResponse.observe(viewLifecycleOwner) { response ->

                            val size = response.body()!!.size
                            var index = 0

                            response.body()?.forEach {
                                index++
                                (activity as Home?)?.PopUp(it.descripcion, it.url)
                            }


                        }
                    } catch (e: Error) {

                    } finally {

                        val sp = this.requireActivity()
                            .getSharedPreferences("UsD",
                                Context.MODE_PRIVATE)
                        val editor = sp.edit()
                        editor.putBoolean("primer", false)
//                val editor = sharedPref.edit()
//                editor.putString("primerVez", "no")
                        editor.apply()

                    }
                }
                Log.d("PV", pv.toString())
            }





    }

    override fun onPause() {
        super.onPause()

    }


    override fun onDestroyView() {
        super.onDestroyView()

    }


}
