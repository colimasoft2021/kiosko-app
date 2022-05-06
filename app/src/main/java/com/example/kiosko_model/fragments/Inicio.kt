package com.example.kiosko_model.fragments

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.fonts.FontFamily
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.kiosko_model.Home
import com.example.kiosko_model.PopUps.popUpComponente
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
    private val viewModelprimerVez: primerVezVM by viewModels({requireParentFragment()})
    private val porcentajeViewModel: PorcentajeViewModel by viewModels({requireParentFragment()})
    private lateinit var avisoViewModel: MensajeInicialViewModel

    private val pv: primerVezVM by viewModels({requireParentFragment()})


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

                    val repository = Repository()
                    val viewModelFactory = ComponentsViewModelFactory(repository)

        try{

            val sharedPref = this.requireActivity()
                .getSharedPreferences("UsD", Context.MODE_PRIVATE)

            val id = Id(sharedPref.getString("id","defaultName")!!.toInt())
            viewModel = ViewModelProvider(this, viewModelFactory)[ComponentesViewModel::class.java]
            viewModel.getComponentes(id)

            porcentajeViewModel.reset()

            Log.d("response ID MODULO", porcentajeViewModel.idModulo.value.toString())
            Log.d("response PORCENTAJE", porcentajeViewModel.porcentaje.value.toString())
            Log.d("RESPONSE CANTIDADMOD", porcentajeViewModel.cantidadModulos.value.toString())
            porcentajeViewModel.setId(sharedPref.getString("id","defaultName")!!.toInt())


            try {
                        viewModel.datos.observe(viewLifecycleOwner) { response ->

                            if (response.isSuccessful) {
                                binding.llBotoneraConProgreso.removeAllViews()
                                binding.llBotonera.removeAllViews()

                                val lltextHardCoded = LinearLayout(context)
                                lltextHardCoded.orientation = LinearLayout.VERTICAL
                                lltextHardCoded.gravity = Gravity.CENTER_VERTICAL

                                val ContenedorHeader = LinearLayout(context)
                                ContenedorHeader.orientation = LinearLayout.HORIZONTAL
                                ContenedorHeader.setBackgroundColor(Color.WHITE)

                                val TextHeader = TextView(context)
                                TextHeader.text = "El objetivo de esta aplicación es bridarte capacitación para tener un mejor rendimiento como parte de la familia kiosko."
                                TextHeader.setTextColor(Color.BLACK)
                                TextHeader.textSize = 20F
                                TextHeader.gravity = Gravity.LEFT
                                TextHeader.typeface = Typeface.DEFAULT_BOLD

                                val TextHardCodedTitle = TextView(context)
                                TextHardCodedTitle.text = "Inicia tu capacitación"
                                TextHardCodedTitle.setTextColor(Color.BLACK)
                                TextHardCodedTitle.textSize = 30F
                                TextHardCodedTitle.gravity = Gravity.LEFT
                                TextHardCodedTitle.typeface = Typeface.DEFAULT_BOLD

                                val lpTextHardCodedTitle = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                                lpTextHardCodedTitle.setMargins(0,15,0,5)


                                val TextHardCodedSubTitle = TextView(context)
                                TextHardCodedSubTitle.text = "Elige el apartado con el que quieres comenzar"
                                TextHardCodedSubTitle.setTextColor(Color.BLACK)
                                TextHardCodedSubTitle.textSize = 20F
                                TextHardCodedSubTitle.gravity = Gravity.LEFT

                                val lpTextHardCodedSubTitle = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                                lpTextHardCodedSubTitle.setMargins(0,10,0,15)

                                lltextHardCoded.addView(TextHardCodedTitle,lpTextHardCodedTitle)
                                lltextHardCoded.addView(TextHardCodedSubTitle,lpTextHardCodedSubTitle)

                                val lltextHardCodedProgress = LinearLayout(context)
                                lltextHardCodedProgress.orientation = LinearLayout.VERTICAL
                                lltextHardCodedProgress.gravity = Gravity.CENTER_VERTICAL

                                val TextHardCodedProgres = TextView(context)
                                TextHardCodedProgres.text = "Continuar con tu progreso llevado"
                                TextHardCodedProgres.setTextColor(Color.BLACK)
                                TextHardCodedProgres.textSize = 30F
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


                                response.body()?.customModulos!!.forEach {
                                    if (it.componentes.isNullOrEmpty()&&it.submodulos.isNullOrEmpty()){

                                    }else{
                                        if (it.submodulos!!.isNotEmpty()){
                                            val idModulo = it.id
                                            val hijos = it.numeroHijos

                                            val compuestos = it.submodulos[0].componentes
                                            val idSubmodulo = it.submodulos[0].id
                                            val padre = it.submodulos[0].padre
                                            val imagenBoton = it.submodulos[0].componentes[0]?.idModuloNavigation?.url


                                            val linearLayout = LinearLayout(context)
                                            linearLayout.orientation = LinearLayout.HORIZONTAL
                                            linearLayout.gravity = Gravity.CENTER_VERTICAL

                                            LinearHorizontalBotones.orientation = LinearLayout.HORIZONTAL
                                            LinearHorizontalBotones.gravity = Gravity.CENTER_VERTICAL

                                            val LinearHorizontalBotonera = LinearLayout(context)
                                            LinearHorizontalBotonera.orientation = LinearLayout.HORIZONTAL
                                            LinearHorizontalBotonera.gravity = Gravity.CENTER_VERTICAL

                                            val contenedorBotonProgreso = LinearLayout(context)
                                            contenedorBotonProgreso.orientation = LinearLayout.VERTICAL
                                            contenedorBotonProgreso.gravity = Gravity.CENTER

                                            val contenedorBotoneraProgreso = LinearLayout(context)
                                            contenedorBotoneraProgreso.orientation = LinearLayout.VERTICAL
                                            contenedorBotoneraProgreso.gravity = Gravity.CENTER


                                            val ButtonProgress = ImageButton(context)
//                                            ButtonProgress.setTextColor(Color.parseColor("#FFFFFFFF"))

                                            ButtonProgress.load(imagenBoton) {
                                                placeholder(R.drawable.loading_animation)
                                                error(R.drawable.ic_broken_image)
                                            }
                                            ButtonProgress.adjustViewBounds = true
                                            ButtonProgress.scaleType = ImageView.ScaleType.FIT_XY
                                            ButtonProgress.background= null

                                            ButtonProgress.setOnClickListener {
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

                                                findNavController().navigate(R.id.action_inicioFragment_to_contenido)
                                            }

                                            val tituloBotonProgres = TextView(context)
                                            tituloBotonProgres.text = it.titulo
                                            tituloBotonProgres.setTextColor(Color.BLACK)
                                            tituloBotonProgres.textSize = 18F
                                            tituloBotonProgres.gravity = Gravity.CENTER

                                            val ButtonProgressBotonera = ImageButton(context)
                                            ButtonProgressBotonera.background = null
//                                            ButtonProgress.setTextColor(Color.parseColor("#FFFFFFFF"))

                                            ButtonProgressBotonera.load(imagenBoton) {
                                                placeholder(R.drawable.loading_animation)
                                                error(R.drawable.ic_broken_image)
                                            }
                                            ButtonProgressBotonera.adjustViewBounds = true
                                            ButtonProgressBotonera.scaleType = ImageView.ScaleType.FIT_XY
                                            ButtonProgressBotonera.setOnClickListener {
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

                                                findNavController().navigate(R.id.action_inicioFragment_to_contenido)
                                            }

                                            val tituloBotoneraProgres = TextView(context)
                                            tituloBotoneraProgres.text = it.titulo
                                            tituloBotoneraProgres.setTextColor(Color.BLACK)
                                            tituloBotoneraProgres.textSize = 18F
                                            tituloBotoneraProgres.gravity = Gravity.CENTER

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

                                            val lpProgresoBotonContenedor = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,  LinearLayout.LayoutParams.MATCH_PARENT)
                                            lpProgresoContenedor.setMargins(20,10,30,50)

                                            val lpContenedorBotonera = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,  LinearLayout.LayoutParams.MATCH_PARENT)
                                            lpContenedorBotonera.setMargins(15,30,20,20)

                                            val lpBotonProgreso = LinearLayout.LayoutParams(200,150)

                                            val lpTituloBotonProgres = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                                            lpTituloBotonProgres.setMargins(0,10,0,20)





                                            contenedorBotoneraProgreso.addView(ButtonProgressBotonera,lpBotonProgreso)
                                            contenedorBotoneraProgreso.addView(tituloBotoneraProgres)
                                            LinearHorizontalBotones.addView(contenedorBotoneraProgreso,lpContenedorBotonera)



                                            contenedorBotonProgreso.addView(ButtonProgress,lpBotonProgreso)
                                            contenedorBotonProgreso.addView(tituloBotonProgres)
                                            linearLayout.addView(contenedorBotonProgreso)



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
                                val scrollLP = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height/3 )
                                scrollLP.setMargins(10,10,20,20)
                                Log.d("HEIGTHHHHHHHHH",height.toString())
                                Log.d("widthhhhhhhhhhhhhh", width.toString())

                                val scrollLPContenedor = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT)
                                scrollLPContenedor.setMargins(15,10,20,50)

//                                scrollPorcentaje.layoutParams = contenedorProgresoScroll.layoutParams
                                scrollPorcentaje.addView(contenedorProgresoScroll )

                                lltextHardCodedProgress.addView(scrollPorcentaje, scrollLP)

                                llBotoneraConProgreso.addView(lltextHardCodedProgress,scrollLPContenedor)

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


        val repo = Repository()
        val viewModelFactoryAviso = MensajeInicialViewModelFactory(repo)

        avisoViewModel =  ViewModelProvider(this, viewModelFactoryAviso)[MensajeInicialViewModel::class.java]
        avisoViewModel.getAvisoInicial()

        Log.d("PV", pv.primerVez.value.toString())
        if(pv.primerVez.value==true){

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

            }
        }
    }

    override fun onPause() {
        super.onPause()
        binding.llBotoneraConProgreso.removeAllViews()
        binding.llBotonera.removeAllViews()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }


}



