package com.example.kiosko_model.fragments

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.kiosko_model.Home
import com.example.kiosko_model.PopUps.popUpComponente
import com.example.kiosko_model.PopUps.popUpComponenteVideo
import com.example.kiosko_model.R
import com.example.kiosko_model.adapter.ContentRowAdapter
import com.example.kiosko_model.databinding.FragmentContenido2Binding
import com.example.kiosko_model.databinding.FragmentContenidoBinding
import com.example.kiosko_model.models.*
import com.example.kiosko_model.modelslite.ProgresoViewModel
import com.example.kiosko_model.repository.Repository
import java.security.cert.CertPathValidatorException
import java.util.concurrent.TimeoutException


class Contenido2 : Fragment() {
    private val viewModel3: CompuestosViewModel by viewModels({requireParentFragment()})
    private val viewModel: CompuestosViewModel2 by viewModels({requireParentFragment()})
    private val porcentajeViewModel: PorcentajeViewModel by viewModels({requireParentFragment()})
    private val viewModelLocal: ComponentesViewModel2 by viewModels ({requireParentFragment()})


    private val contentRowAdapter by lazy { ContentRowAdapter(context) }

    private lateinit var progreso: ProgresoViewModel

    private lateinit var viewModel2: ComponentesViewModel

    private var _binding: FragmentContenido2Binding? = null
    private val binding get() = _binding!!


//    private val viewBinding by lazy(LazyThreadSafetyMode.NONE) {
//        FragmentContenidoBinding.inflate(layoutInflater)
//    }

    override fun onStop() {
        super.onStop()
        binding.llContenedor.removeAllViews()

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) : View? {
        _binding = FragmentContenido2Binding.inflate(inflater,container,false)
        binding.llContenedor.removeAllViews()

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = Repository()
        val viewModelFactory = ProgresoViewModelFactory(repository)
        progreso = ViewModelProvider(this, viewModelFactory)[ProgresoViewModel::class.java]

        val imagenFondo = binding.imagenViewContenido2


        val idU = porcentajeViewModel.idProgreso.value!!
        val idM = porcentajeViewModel.idModulo.value!!
        val p = porcentajeViewModel.porcentaje.value!!.toDouble()


        val progresoPost = PostProgreso(idU,idM,p.toDouble())
        progreso.pushProgresoRegistro(progresoPost)

        Log.d("response ID MODULO", porcentajeViewModel.idModulo.value.toString())
        Log.d("response PORCENTAJE", porcentajeViewModel.porcentaje.value.toString())
        Log.d("RESPONSE CANTIDADMOD", porcentajeViewModel.cantidadModulos.value.toString())
        Log.d("RESPONSE id", porcentajeViewModel.idProgreso.value.toString())


        val llContenedor = binding.llContenedor
//        val llContenedor2 = binding.llContenedor2

        val displaymetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displaymetrics)
        val height = displaymetrics.heightPixels
        val width = displaymetrics.widthPixels

        binding.llContenedor.removeAllViews()

        try{
            viewModel.componentes.observe(viewLifecycleOwner) { it ->

                if (it.isNotEmpty()) {
                    binding.llContenedor.removeAllViews()

                    imagenFondo.load(it[0]?.idModuloNavigation?.urlFondo){
//                        placeholder(R.drawable.loading_animation)
//                        kotlin.error(R.drawable.ic_broken_image)
                    }

                    val listViewBackBoton = LinearLayout(context)
                    val buttonBack = Button(context)

                    val LayoutBotonBack = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
                    LayoutBotonBack.setMargins(0,0,0,10)
                    listViewBackBoton.orientation = LinearLayout.HORIZONTAL

                    val submodulo = viewModelLocal.componentes2.value
                    val index = viewModelLocal.index.value

//                    if(index == 1){
                        binding.llContenedor.removeAllViews()

                        val col = Color.parseColor("#000000")
                        val rad = 20//radius will be 5px
                        val strk = 5
                        val gD = GradientDrawable()
                        gD.setColor(col)
                        gD.cornerRadius = rad.toFloat()
                        gD.setStroke(strk, col)
                        Log.d("CONTENIDO2",index.toString())
                        buttonBack.textSize = 18F
                        buttonBack.text = "  Pagina Principal  "
                        buttonBack.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_back_button,
                            0,
                            0,
                            0)
                        buttonBack.marginStart
                        buttonBack.gravity = Gravity.CENTER
                        buttonBack.setTextColor(Color.WHITE)
                        buttonBack.background = gD
                        buttonBack.setOnClickListener {
                            binding.llContenedor.removeAllViews()

                            val intento =
                                Intent(context, Home::class.java)
                            context?.startActivity(intento)
                            binding.llContenedor.removeAllViews()


                        }
//

                    val progresoPModulo = porcentajeViewModel.progresoPerModulo.value
                    val progress = porcentajeViewModel.porcentaje.value
                    porcentajeViewModel.setPorcentaje(progress!! + progresoPModulo!!)

                    listViewBackBoton.addView(buttonBack, LayoutBotonBack)
                    llContenedor.addView(listViewBackBoton)
                    it.forEach { it ->

                        when (it!!.tipoComponente) {



                            "subtitulo" -> {
//                                   //fondo redondo blanco
                                val color = Color.WHITE
                                val radius = 30
                                val strokeWidth = 5
                                val gradientDrawable = GradientDrawable()
                                gradientDrawable.setColor(color)
                                gradientDrawable.cornerRadius = radius.toFloat()
                                gradientDrawable.setStroke(strokeWidth, color)
                                //fondo redondo blanco
//
                                val buttonW = Button(context)
                                val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
                                lp.setMargins(0,15,0,10)
                                buttonW.textSize = 25F
                                buttonW.isClickable= false
                                buttonW.text = it.subtitulo
                                buttonW.background = gradientDrawable
                                buttonW.textSize = 30F
                                buttonW.setTextColor(Color.BLACK)
                                buttonW.gravity = Gravity.CENTER
                                buttonW.setCompoundDrawablesWithIntrinsicBounds(R.drawable.liston_4,0,R.drawable.liston_4,0)
                                buttonW.isEnabled = false

                                llContenedor.addView(buttonW,lp)
                            }

                            "banner-informativo" -> {
                                val buttonW = Button(context)
                                val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
                                lp.setMargins(0,15,0,10)
                                buttonW.textSize = 25F
                                buttonW.isClickable= false
                                buttonW.text = it.descripcion
                                buttonW.setBackgroundColor(Color.parseColor(it.backgroundColor))
                                buttonW.marginStart
                                buttonW.gravity = Gravity.CENTER
                                llContenedor.addView(buttonW,lp)
                            }

                            "carrucel" -> {

                                val horizontalScrollView = HorizontalScrollView(context)
                                val listView = LinearLayout(context)

                                listView.orientation= LinearLayout.HORIZONTAL
                                listView.gravity = Gravity.CENTER_VERTICAL

                                it.desplazantes.forEach {

                                    val layoutParams = LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT)

                                    layoutParams.setMargins(15, 0, 20, 0)

                                    val imagenDesplazanteW = ImageView(context)
                                    imagenDesplazanteW.load(it.url) {
                                        placeholder(R.drawable.loading_animation)
                                        error(R.drawable.ic_broken_image)
                                    }
                                    imagenDesplazanteW.scaleType= ImageView.ScaleType.CENTER_CROP
                                    listView.addView(imagenDesplazanteW,layoutParams)
                                }
                                horizontalScrollView.addView(listView)
                                llContenedor.addView(horizontalScrollView)
                                val param = listView.layoutParams as ViewGroup.MarginLayoutParams
                                param.setMargins(0,5,0,0)
                                listView.layoutParams = param

                            }

                            "texto" -> {
                                val listView = LinearLayout(context)
                                val textoW = TextView(context)
                                textoW.text = it.descripcion
                                textoW.textSize = 20F
                                textoW.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
                                textoW.setTextColor(Color.BLACK)

//                                fondo blanco
//                                   val color = Color.WHITE
//                                   val radius = 30//radius will be 5px
//                                   val strokeWidth = 5
//                                   val gradientDrawable = GradientDrawable()
//                                   gradientDrawable.setColor(color)
//                                   gradientDrawable.cornerRadius = radius.toFloat()
//                                   gradientDrawable.setStroke(strokeWidth, color)
//
//                                   listView.background = gradientDrawable

                                textoW.gravity = Gravity.CENTER
                                val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
                                lp.setMargins(30,20,5,15)
                                listView.addView(textoW,lp)

                                val lp2 = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
                                lp2.setMargins(0,0,0,0)
                                llContenedor.addView(listView,lp2)
                            }

                            "desplazante-texto-imagen" -> {


                                val horizontalScrollView = HorizontalScrollView(context)
                                val listView = LinearLayout(context)

                                listView.orientation= LinearLayout.HORIZONTAL
                                listView.gravity = Gravity.CENTER_VERTICAL

                                it.desplazantes.forEach { des ->
                                    val color =  Color.parseColor(des.backgroundColor)
                                    val radius = 30
                                    val strokeWidth = 5
                                    val gradientDrawable = GradientDrawable()
                                    gradientDrawable.setColor(color)
                                    gradientDrawable.cornerRadius = radius.toFloat()
                                    gradientDrawable.setStroke(strokeWidth, color)

                                    val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
                                    layoutParams.setMargins(0, 0, 0, 0)
                                    val contenedor = LinearLayout(context)
                                    contenedor.orientation= LinearLayout.VERTICAL
                                    contenedor.gravity = Gravity.CENTER
                                    contenedor.background = gradientDrawable

                                    val tituloImagenDesplazanteW = TextView(context)
                                    val textoImagenDesplazanteW = TextView(context)
                                    val imagenDesplazanteW = ImageView(context)

                                    imagenDesplazanteW.load(des.url) {
                                        placeholder(R.drawable.loading_animation)
                                        error(R.drawable.ic_broken_image)
                                    }

//                                       imagenDesplazanteW.scaleType= ImageView.ScaleType.CENTER_CROP
                                    contenedor.addView(imagenDesplazanteW, layoutParams)

                                    tituloImagenDesplazanteW.text = des.titulo
                                    tituloImagenDesplazanteW.gravity = Gravity.CENTER
                                    contenedor.addView(tituloImagenDesplazanteW, layoutParams)

                                    textoImagenDesplazanteW.text = des.texto
                                    textoImagenDesplazanteW.gravity = Gravity.CENTER
                                    contenedor.addView(textoImagenDesplazanteW, layoutParams)

                                    val clayoutParams = LinearLayout.LayoutParams(width/2-width/10, height/4)
                                    clayoutParams.setMargins(10,10,20,10)
                                    listView.addView(contenedor,clayoutParams)
                                }
                                horizontalScrollView.addView(listView)
                                llContenedor.addView(horizontalScrollView)

                                val param = listView.layoutParams as ViewGroup.MarginLayoutParams
                                param.setMargins(0,5,0, 0)
                                listView.layoutParams = param

                            }

                            "texto-imagen" -> {
                                val imagenW = ImageView(context)
                                val texto = TextView(context)
                                val listView = LinearLayout(context)

                                listView.orientation = LinearLayout.HORIZONTAL
                                listView.gravity = Gravity.CENTER_HORIZONTAL



                                imagenW.load(it.url) {
                                    placeholder(R.drawable.loading_animation)
                                    error(R.drawable.ic_broken_image)
                                }
                                imagenW.scaleType = ImageView.ScaleType.FIT_XY


                                val lpI = LinearLayout.LayoutParams(width / 8,
                                    LinearLayout.LayoutParams.MATCH_PARENT)
                                lpI.setMargins(0, 8, 0, 8)

                                listView.addView(imagenW, lpI)

                                texto.text = it.descripcion
                                texto.textSize = 18F
                                texto.setTextColor(Color.BLACK)
                                texto.gravity = Gravity.CENTER

                                val lpT =
                                    LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT)
                                lpT.setMargins(0, 8, 0, 8)

                                listView.addView(texto, lpT)

                                val lp2 =
                                    LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT)
                                lp2.setMargins(0, 10, 0, 15)

                                llContenedor.addView(listView, lp2)

                            }

                            "imagen-titulo-desc" -> {

                                val imagenW = ImageView(context)
                                val titulo = TextView(context)
                                val texto = TextView(context)

                                val listView = LinearLayout(context)
                                listView.orientation = LinearLayout.HORIZONTAL
                                listView.gravity = Gravity.CENTER_HORIZONTAL


                                val color = Color.WHITE
                                val radius = 30//radius will be 5px
                                val strokeWidth = 5
                                val gradientDrawable = GradientDrawable()
                                gradientDrawable.setColor(color)
                                gradientDrawable.cornerRadius = radius.toFloat()
                                gradientDrawable.setStroke(strokeWidth, color)

                                val listView2 = LinearLayout(context)
                                listView2.orientation = LinearLayout.VERTICAL
                                listView2.gravity = Gravity.CENTER_HORIZONTAL
                                listView2.background = gradientDrawable


                                imagenW.load(it.url) {
                                    placeholder(R.drawable.loading_animation)
                                    error(R.drawable.ic_broken_image)
                                }
                                imagenW.scaleType = ImageView.ScaleType.FIT_CENTER

                                val lpI = LinearLayout.LayoutParams(width / 5,
                                    LinearLayout.LayoutParams.MATCH_PARENT)
                                lpI.setMargins(0, 8, 0, 8)

                                listView.addView(imagenW,lpI)

                                titulo.text = it.titulo
                                titulo.textSize = 20F
                                titulo.setTextColor(Color.BLACK)
                                titulo.gravity = Gravity.LEFT

                                texto.text = it.descripcion
                                texto.textSize = 18F
                                texto.setTextColor(Color.BLACK)
                                texto.gravity = Gravity.CENTER

                                val lpT =
                                    LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT)
                                lpT.setMargins(0, 8, 0, 8)


                                val lpView =
                                    LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT)
                                lpView.setMargins(0, 0, 0, 8)

                                listView.addView(titulo, lpT)
                                listView2.addView(listView,lpView)
                                listView2.addView(texto,lpView)



                                val lp2 =
                                    LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT)
                                lp2.setMargins(0, 10, 0, 15)

                                llContenedor.addView(listView2, lp2)

                            }

                            "enlace" -> {
                                val buttonW = Button(context)

                                val color = Color.parseColor(it.backgroundColor)
                                val radius = 30//radius will be 5px
                                val strokeWidth = 2
                                val gradientDrawable = GradientDrawable()
                                gradientDrawable.setColor(color)
                                gradientDrawable.cornerRadius = radius.toFloat()
                                gradientDrawable.setStroke(strokeWidth, color)
                                buttonW.background = gradientDrawable


                                val listView = LinearLayout(context)
                                listView.orientation= LinearLayout.VERTICAL
                                listView.gravity = Gravity.CENTER_HORIZONTAL
                                val lp = LinearLayout.LayoutParams(width/3,height/10)
                                lp.setMargins(0,15,0,15)
                                buttonW.textSize = 18F
                                buttonW.text = it.titulo
                                buttonW.marginStart
                                buttonW.gravity = Gravity.CENTER
                                val url = it.url
                                buttonW.setOnClickListener {
                                    val intent = Intent(Intent.ACTION_VIEW)
                                    intent.data = Uri.parse(url)
                                    startActivity(intent)

                                }
                                listView.addView(buttonW,lp)
                                llContenedor.addView(listView)
                                val param = listView.layoutParams as ViewGroup.MarginLayoutParams
                                param.setMargins(width-(width-50),5,0,0)
                                listView.layoutParams = param
                            }
                            "acceso-cursos" -> {
                                val buttonW = Button(context)

                                // boton redondo
                                val color = Color.parseColor(it.backgroundColor)
                                val radius = 30//radius will be 5px
                                val strokeWidth = 2
                                val gradientDrawable = GradientDrawable()
                                gradientDrawable.setColor(color)
                                gradientDrawable.cornerRadius = radius.toFloat()
                                gradientDrawable.setStroke(strokeWidth, color)
                                buttonW.background = gradientDrawable
                                //fin boton redondo

                                val imagenW = ImageView(context)
                                val listView = LinearLayout(context)
                                listView.orientation= LinearLayout.HORIZONTAL
                                listView.gravity = Gravity.CENTER_VERTICAL


                                val listView2 = LinearLayout(context)
                                listView2.orientation= LinearLayout.HORIZONTAL
                                listView2.gravity = Gravity.CENTER_HORIZONTAL

                                val lp = LinearLayout.LayoutParams(width/4,LinearLayout.LayoutParams.WRAP_CONTENT)
                                lp.setMargins(0,15,0,15)
                                buttonW.textSize = 18F
                                buttonW.text = it.titulo

//                                   buttonW.setBackgroundColor(Color.parseColor(it.backgroundColor))

                                buttonW.marginStart
                                buttonW.gravity = Gravity.CENTER
                                val url = it.urlDos
                                buttonW.setOnClickListener {
                                    val intent = Intent(Intent.ACTION_VIEW)
                                    intent.data = Uri.parse(url)
                                    startActivity(intent)

                                }
                                imagenW.load(it.url) {
                                    placeholder(R.drawable.loading_animation)
                                    error(R.drawable.ic_broken_image)
                                }
                                imagenW.scaleType= ImageView.ScaleType.CENTER
                                listView.addView(imagenW)
                                listView.addView(buttonW,lp)
                                listView2.addView(listView)
                                llContenedor.addView(listView2)
//
                            }
                            "imagen" -> {


                                //fondo redondo blanco
                                val color = Color.WHITE
                                val radius = 30
                                val strokeWidth = 5
                                val gradientDrawable = GradientDrawable()
                                gradientDrawable.setColor(color)
                                gradientDrawable.cornerRadius = radius.toFloat()
                                gradientDrawable.setStroke(strokeWidth, color)
                                //fondo redondo blanco

                                val imagenW = ImageView(context)
                                val pieImagenW = TextView(context)
                                val listView = LinearLayout(context)
                                listView.background = gradientDrawable
                                listView.orientation= LinearLayout.VERTICAL
                                listView.gravity = Gravity.CENTER_HORIZONTAL
                                val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                                lp.setMargins(0,15,0,10)
                                imagenW.load(it.url) {
                                    placeholder(R.drawable.loading_animation)
                                    error(R.drawable.ic_broken_image)
                                }
                                imagenW.scaleType= ImageView.ScaleType.CENTER
                                listView.addView(imagenW,lp)
                                pieImagenW.text = it.descripcion
                                pieImagenW.gravity = Gravity.CENTER
                                pieImagenW.textSize = 20F
                                pieImagenW.textAlignment = View.TEXT_ALIGNMENT_CENTER
                                pieImagenW.setTextColor(Color.BLACK)

                                val lpPieImagen = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
                                lpPieImagen.setMargins(0,15,0,10)
                                listView.addView(pieImagenW,lpPieImagen)

                                val lpcontenedor = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                                lpcontenedor.setMargins(0,20,0,10)
                                llContenedor.addView(listView,lpcontenedor)

                            }
                            "video-guia"-> {

                                val textoW = TextView(context)
                                textoW.text = it.descripcion
                                textoW.textSize = 30F

                                val uri = Uri.parse(it.url)
                                val video = VideoView(context)
                                val mediaController = MediaController(context)
                                val listView = LinearLayout(context)
                                listView.orientation= LinearLayout.HORIZONTAL
                                listView.gravity = Gravity.CENTER_HORIZONTAL
                                val relativeLayout = RelativeLayout(context)
                                val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                    1000)

                                video.setVideoURI(uri)
                                video.setMediaController(mediaController)
                                video.layoutParams = FrameLayout.LayoutParams(lp)
                                mediaController.setAnchorView(video)
                                relativeLayout.addView(video)
                                listView.addView(textoW)
                                llContenedor.addView(relativeLayout)
                                llContenedor.addView(listView)

                            }
                            "video"-> {

                                val uri = Uri.parse(it.url)
                                val video = VideoView(context)
                                val mediaController = MediaController(context)
                                val listView = LinearLayout(context)
                                listView.orientation= LinearLayout.HORIZONTAL
                                listView.gravity = Gravity.CENTER_HORIZONTAL
                                val relativeLayout = RelativeLayout(context)
                                val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                    1000)
                                video.setVideoURI(uri)
                                video.setMediaController(mediaController)
                                video.layoutParams = FrameLayout.LayoutParams(lp)
                                mediaController.setAnchorView(video)
                                relativeLayout.addView(video)
                                listView.addView(relativeLayout)
                                llContenedor.addView(listView)


                            }
                            "pop-up" -> {

                                val descripcion = it.descripcion
                                val url = it.url
                                (activity as Home?) ?.PopUpComponente(descripcion,url)

                            }
                            "pop-up-video" -> {

                                val descripcion = it.descripcion
                                val url = it.url
                                (activity as Home?) ?.PopUpComponenteVideo(descripcion,url,mensajeInicial = true)

                            }

                            "desplegable-texto-imagen" ->{

                                val horizontalScrollView = HorizontalScrollView(context)
                                val imagenW = ImageView(context)
                                val imagenW2 = ImageView(context)
                                val imagenW3 = ImageView(context)
                                val subtituloW = TextView(context)
                                val textoW = TextView(context)

                                val listView = LinearLayout(context)
                                listView.orientation= LinearLayout.HORIZONTAL
                                listView.gravity = Gravity.CENTER_VERTICAL

                                val lpImagen = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,height/20)
                                lpImagen.setMargins(10,10,0,0)

                                imagenW.load(it.url) {
                                    placeholder(R.drawable.loading_animation)
                                }
                                imagenW.scaleType= ImageView.ScaleType.FIT_CENTER

                                listView.addView(imagenW,lpImagen)

                                subtituloW.text = it.subtitulo
                                subtituloW.gravity = Gravity.CENTER
                                subtituloW.setTextColor(Color.BLACK)
                                subtituloW.textSize = 30F
                                listView.addView(subtituloW)

                                textoW.text = it.descripcion
                                textoW.gravity = Gravity.START
                                textoW.setTextColor(Color.BLACK)
                                textoW.textSize = 20F

                                val listView2 = LinearLayout(context)
                                listView2.gravity = Gravity.CENTER_VERTICAL
                                listView2.orientation=LinearLayout.HORIZONTAL

                                imagenW2.load(it.urlDos) {
                                    placeholder(R.drawable.loading_animation)
                                    error(R.drawable.ic_broken_image)
                                }
                                imagenW2.scaleType= ImageView.ScaleType.FIT_CENTER

                                imagenW3.load(it.urlTres) {
                                    placeholder(R.drawable.loading_animation)
                                    error(R.drawable.ic_broken_image)
                                }
                                imagenW3.scaleType= ImageView.ScaleType.FIT_CENTER

                                val lpImagenes = LinearLayout.LayoutParams(width/2-width/15, LinearLayout.LayoutParams.WRAP_CONTENT)
                                lpImagenes.setMargins(10,0,10,5)

                                listView2.addView(imagenW2, lpImagenes)
                                listView2.addView(imagenW3, lpImagenes)
                                horizontalScrollView.addView(listView2)

                                val lpTexto = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
                                lpTexto.setMargins(10,10,0,0)

                                val color = Color.WHITE
                                val radius = 20
                                val strokeWidth = 5
                                val gradientDrawable = GradientDrawable()
                                gradientDrawable.setColor(color)
                                gradientDrawable.cornerRadius = radius.toFloat()
                                gradientDrawable.setStroke(strokeWidth, color)

                                val Contenedor = LinearLayout(context)
                                Contenedor.background = gradientDrawable
                                Contenedor.orientation = LinearLayout.VERTICAL
                                Contenedor.addView(listView)
                                Contenedor.addView(textoW, lpTexto)
                                Contenedor.addView(horizontalScrollView)

                                val lpContenedor = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT)
                                lpContenedor.setMargins(0,15,0,0)

                                llContenedor.addView(Contenedor,lpContenedor)

                            }
                            "desplegable-texto-imagen-imagen" ->{
                                val horizontalScrollView = HorizontalScrollView(context)
                                val imagenW = ImageView(context)
                                val imagenW2 = ImageView(context)
                                val imagenW3 = ImageView(context)
                                val subtituloW = TextView(context)
                                val textoW = TextView(context)

                                val listView = LinearLayout(context)
                                listView.orientation= LinearLayout.HORIZONTAL
                                listView.gravity = Gravity.CENTER_VERTICAL

                                val lpImagen = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,height/20)
                                lpImagen.setMargins(10,10,0,0)

                                imagenW.load(it.url) {
                                    placeholder(R.drawable.loading_animation)
                                }
                                imagenW.scaleType= ImageView.ScaleType.FIT_CENTER

                                listView.addView(imagenW,lpImagen)

                                subtituloW.text = it.subtitulo
                                subtituloW.gravity = Gravity.CENTER
                                subtituloW.setTextColor(Color.BLACK)
                                subtituloW.textSize = 30F
                                listView.addView(subtituloW)

                                textoW.text = it.descripcion
                                textoW.gravity = Gravity.START
                                textoW.setTextColor(Color.BLACK)
                                textoW.textSize = 20F

                                val listView2 = LinearLayout(context)
                                listView2.gravity = Gravity.CENTER_VERTICAL
                                listView2.orientation=LinearLayout.HORIZONTAL

                                imagenW2.load(it.urlDos) {
                                    placeholder(R.drawable.loading_animation)
                                    error(R.drawable.ic_broken_image)
                                }
                                imagenW2.scaleType= ImageView.ScaleType.FIT_CENTER

                                imagenW3.load(it.urlTres) {
                                    placeholder(R.drawable.loading_animation)
                                    error(R.drawable.ic_broken_image)
                                }
                                imagenW3.scaleType= ImageView.ScaleType.FIT_CENTER

                                val lpImagenes = LinearLayout.LayoutParams(width/2-width/15, LinearLayout.LayoutParams.WRAP_CONTENT)
                                lpImagenes.setMargins(10,0,10,5)

                                listView2.addView(imagenW2, lpImagenes)
                                listView2.addView(imagenW3, lpImagenes)
                                horizontalScrollView.addView(listView2)

                                val lpTexto = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
                                lpTexto.setMargins(10,10,0,0)

                                val color = Color.WHITE
                                val radius = 20
                                val strokeWidth = 5
                                val gradientDrawable = GradientDrawable()
                                gradientDrawable.setColor(color)
                                gradientDrawable.cornerRadius = radius.toFloat()
                                gradientDrawable.setStroke(strokeWidth, color)

                                val Contenedor = LinearLayout(context)
                                Contenedor.background = gradientDrawable
                                Contenedor.orientation = LinearLayout.VERTICAL
                                Contenedor.addView(listView)
                                Contenedor.addView(textoW, lpTexto)
                                Contenedor.addView(horizontalScrollView)

                                val lpContenedor = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT)
                                lpContenedor.setMargins(0,15,0,0)

                                llContenedor.addView(Contenedor,lpContenedor)
                            }
                        }
                    }


                    val repository = Repository()
                    val viewModelFactory = ComponentsViewModelFactory(repository)
                    try {

                        viewModel2 = ViewModelProvider(this, viewModelFactory)[ComponentesViewModel::class.java]
                        val sharedPref = this.requireActivity()
                            .getSharedPreferences("UsD", Context.MODE_PRIVATE)

                        val id = Id(sharedPref.getString("id","defaultName")!!.toInt())
                        viewModel2.getComponentes(id)
                        val lp =
                            LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT)

                        try {
                            viewModel2.datos.observe(viewLifecycleOwner) { response ->

                                if (response.isSuccessful) {


                                    val respo = response.body()!!.customModulos

                                        viewModel.padre.observe(viewLifecycleOwner) { a ->

                                            val listView = LinearLayout(context)

                                            val padre = a.toString()
                                            val buttonNext = Button(context)
//
                                           if(viewModelLocal.hijos.value == viewModelLocal.index.value) {
                                               val buttonExit = Button(context)

                                               val c =
                                                   Color.parseColor("#000000")
                                               val r = 20//radius will be 5px
                                               val s = 5
                                               val g = GradientDrawable()
                                               g.setColor(c)
                                               g.cornerRadius = r.toFloat()
                                               g.setStroke(s, c)


                                               listView.orientation =
                                                   LinearLayout.HORIZONTAL
                                               listView.gravity = Gravity.END

                                               val titlo = " Pagina principal "
                                               buttonExit.text = titlo
                                               buttonExit.textSize = 18F
                                               buttonExit.gravity =
                                                   Gravity.CENTER
                                               buttonExit.setCompoundDrawablesWithIntrinsicBounds(
                                                   0,
                                                   0,
                                                   R.drawable.chevron,
                                                   0)
                                               buttonExit.setTextColor(Color.WHITE)
                                               buttonExit.background = g

                                               buttonExit.setOnClickListener {
                                                   val intento =
                                                       Intent(context,
                                                           Home::class.java)
                                                   context?.startActivity(
                                                       intento)
                                               }

                                               val LayoutBotonNext =
                                                   LinearLayout.LayoutParams(
                                                       LinearLayout.LayoutParams.WRAP_CONTENT,
                                                       LinearLayout.LayoutParams.WRAP_CONTENT)
                                               val LayoutBotonNextC =
                                                   LinearLayout.LayoutParams(
                                                       LinearLayout.LayoutParams.WRAP_CONTENT,
                                                       LinearLayout.LayoutParams.WRAP_CONTENT)
                                               LayoutBotonNextC.setMargins(0,
                                                   20,
                                                   0,
                                                   10)
                                               listView.addView(buttonExit,
                                                   LayoutBotonNext)
                                               llContenedor.addView(listView)


                                           }else{
                                               val coBotonNext = Color.parseColor("#263238")
                                               val raBotonNext = 20//radius will be 5px
                                               val sWBotonNext = 5
                                               val gDBotonNext = GradientDrawable()
                                               gDBotonNext.setColor(coBotonNext)
                                               gDBotonNext.cornerRadius = raBotonNext.toFloat()
                                               gDBotonNext.setStroke(sWBotonNext,coBotonNext)

                                               listView.orientation = LinearLayout.HORIZONTAL
                                               listView.gravity = Gravity.END

                                               buttonNext.textSize = 18F

                                               val titlo ="  ${submodulo!![index!!]!!.titulo} "
                                               buttonNext.text = titlo
                                               buttonNext.gravity = Gravity.CENTER
                                               buttonNext.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.chevron,0)
                                               buttonNext.setTextColor(Color.WHITE)
                                               buttonNext.background =gDBotonNext
                                               buttonNext.setOnClickListener {
                                                   viewModelLocal.index(index!!+1)
                                                   viewModel3.componentes(submodulo!![index]!!.componentes)
                                                        findNavController().navigate(R.id.action_contenido2_to_contenido)
                                                   binding.llContenedor.removeAllViews()

                                               }


                                               val LayoutBotonNext = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                                               val LayoutBotonNextC = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                                               LayoutBotonNextC.setMargins(0, 20, 0, 10)
                                               listView.addView(buttonNext, LayoutBotonNext)
                                               llContenedor.addView(listView)





                                           }


                                        }
                                }
                            }
                        }catch (e: TimeoutException) {
                            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()

                        }
                    }catch (e: CertPathValidatorException) {
                        Toast.makeText(context, "Falta certificado SSL banda $e", Toast.LENGTH_SHORT)
                            .show()
                        Log.d("Falta certificado SSL", e.toString())
                    }


                    contentRowAdapter.setData(it!!)
                } else {
                    Log.d("Error#", "ERROR ####")
                }
            }
        }catch (e: Error){
            Log.d("ERROR#$e", "ERROR ##$e##")
        }



    }

    private fun setupRecyclerview(){
//        binding.recyclerViewContenido.adapter = contentRowAdapter
//        binding.recyclerViewContenido.layoutManager = LinearLayoutManager(context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}