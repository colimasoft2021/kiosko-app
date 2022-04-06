package com.example.kiosko_model.fragments

import android.content.Intent
import android.graphics.Color
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
import com.example.kiosko_model.R
import com.example.kiosko_model.adapter.ContentRowAdapter
import com.example.kiosko_model.databinding.FragmentContenidoBinding
import com.example.kiosko_model.models.Componentes
import com.example.kiosko_model.models.ComponentesViewModel
import com.example.kiosko_model.models.ComponentsViewModelFactory
import com.example.kiosko_model.models.CompuestosViewModel
import com.example.kiosko_model.repository.Repository
import java.security.cert.CertPathValidatorException
import java.util.concurrent.TimeoutException


class Contenido2 : Fragment() {
    private val viewModel:CompuestosViewModel by viewModels({requireParentFragment()})
    private val contentRowAdapter by lazy { ContentRowAdapter(context) }

    private lateinit var viewModel2: ComponentesViewModel

    private var _binding: FragmentContenidoBinding? = null
    private val binding get() = _binding!!


//    private val viewBinding by lazy(LazyThreadSafetyMode.NONE) {
//        FragmentContenidoBinding.inflate(layoutInflater)
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) : View? {
        _binding = FragmentContenidoBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val llContenedor = binding.llContenedor
//        val llContenedor2 = binding.llContenedor2

        val displaymetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displaymetrics)
        val height = displaymetrics.heightPixels
        val width = displaymetrics.widthPixels

        binding.llContenedor.removeAllViews()

            setupRecyclerview()
            try{
                viewModel.componentes.observe(viewLifecycleOwner) { it ->
//                    Toast.makeText(context, "that is $it", Toast.LENGTH_SHORT).show()
                    if (it.isNotEmpty()) {
                       binding.llContenedor.removeAllViews()
                       it.forEach { it ->
//                           val id = i.id
//                          val padre= i.padre
//                          val tipoComponente= i.tipoComponente
//                          val url= i.url
//                          val descripcion= i.descripcion
//                          val backgroundColor= i.backgroundColor
//                          val agregarFondo=       i.agregarFondo
//                          val titulo= i.titulo
//                          val subtitulo= i.subtitulo
//                          val orden = i.orden
//                          val idModulo = i.idModulo
//                          val idModuloNavigation = i.idModuloNavigation
//                          val desplazantes = i.desplazantes



                           val buttonW = TextView(context)



                           when(it.tipoComponente){


                               "subtitulo" -> {
                                   val subtituloW = TextView(context)
                                   subtituloW.text = it.subtitulo
                                   llContenedor.addView(subtituloW)
                               }

                               "banner-informativo" -> {
                                   val BannerInfoW = TextView(context)
                                   BannerInfoW.text = it.descripcion
                                   llContenedor.addView(BannerInfoW)

//                BannerInfo.setBackgroundColor(Color.parseColor(dataSet[position].backgroundColor)   )
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
                                           placeholder(com.example.kiosko_model.R.drawable.loading_animation)
                                           error(com.example.kiosko_model.R.drawable.ic_broken_image)
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
                                   val textoW = TextView(context)
                                   textoW.text = it.descripcion
                                   llContenedor.addView(textoW)
                               }

                               "desplazante-texto-imagen" -> {

                                   val horizontalScrollView = HorizontalScrollView(context)
                                   val listView = LinearLayout(context)

                                   listView.orientation= LinearLayout.HORIZONTAL
                                   listView.gravity = Gravity.CENTER_VERTICAL
                                   it.desplazantes.forEach {

                                       val layoutParams = LinearLayout.LayoutParams(
                                           LinearLayout.LayoutParams.WRAP_CONTENT,
                                           LinearLayout.LayoutParams.WRAP_CONTENT)
                                       layoutParams.setMargins(10, 0, 20, 0)
                                       val contenedor = LinearLayout(context)
                                       contenedor.orientation= LinearLayout.VERTICAL
                                       contenedor.gravity = Gravity.CENTER

                                       val tituloImagenDesplazanteW = TextView(context)
                                       val textoImagenDesplazanteW = TextView(context)
                                       val imagenDesplazanteW = ImageView(context)

                                       imagenDesplazanteW.load(it.url) {
                                           placeholder(com.example.kiosko_model.R.drawable.loading_animation)
                                           error(com.example.kiosko_model.R.drawable.ic_broken_image)
                                       }

                                       imagenDesplazanteW.scaleType= ImageView.ScaleType.CENTER_CROP
                                       contenedor.addView(imagenDesplazanteW, layoutParams)

                                       tituloImagenDesplazanteW.text = it.titulo
                                       tituloImagenDesplazanteW.gravity = Gravity.CENTER
                                       contenedor.addView(tituloImagenDesplazanteW, layoutParams)

                                       textoImagenDesplazanteW.text = it.texto
                                       textoImagenDesplazanteW.gravity = Gravity.CENTER
                                       contenedor.addView(textoImagenDesplazanteW, layoutParams)

                                       listView.addView(contenedor)
                                   }
                                   horizontalScrollView.addView(listView)
                                   llContenedor.addView(horizontalScrollView)

                                   val param = listView.layoutParams as ViewGroup.MarginLayoutParams
                                   param.setMargins(0,5,0, 0)
                                   listView.layoutParams = param

                               }

                               "texto-imagen" ->  {
                                   val subtituloW = TextView(context)

                                   subtituloW.text = it.subtitulo
                                   llContenedor.addView(subtituloW)

                               }

                               "enlace" -> {
                                   val listView = LinearLayout(context)
                                   listView.orientation= LinearLayout.VERTICAL
                                   listView.gravity = Gravity.CENTER_HORIZONTAL
                                   val lp = LinearLayout.LayoutParams(width/3,height/10)
                                   buttonW.textSize = 18F
                                   buttonW.text = it.titulo
                                   buttonW.setBackgroundColor(Color.parseColor(it.backgroundColor))
                                   buttonW.marginStart
                                   buttonW.gravity = Gravity.CENTER
                                   val url = it.url
                                   buttonW.setOnClickListener {
                                       val intent = Intent(Intent.ACTION_VIEW)
                                       intent.data = Uri.parse(url)
                                       context?.startActivity(intent)

                                   }
                                   listView.addView(buttonW,lp)
                                   llContenedor.addView(listView)
                                   val param = listView.layoutParams as ViewGroup.MarginLayoutParams
                                   param.setMargins(width-(width-50),5,0,0)
                                   listView.layoutParams = param
                               }
                               "imagen" -> {
                                   val imagenW = ImageView(context)
                                   val pieImagenW = TextView(context)
                                   val listView = LinearLayout(context)
                                   val listView2 = LinearLayout(context)
                                   listView.orientation= LinearLayout.VERTICAL
                                   listView.gravity = Gravity.CENTER_HORIZONTAL
                                   val lp = LinearLayout.LayoutParams(width-50, 300)
                                   imagenW.load(it.url) {
                                        placeholder(com.example.kiosko_model.R.drawable.loading_animation)
                                        error(com.example.kiosko_model.R.drawable.ic_broken_image)
                                   }
                                   imagenW.scaleType= ImageView.ScaleType.CENTER
                                   listView.addView(imagenW)
                                   pieImagenW.text = it.descripcion
                                   pieImagenW.gravity = Gravity.CENTER
                                   listView.addView(pieImagenW)
                                   listView2.addView(listView)
                                   val param = listView.layoutParams as ViewGroup.MarginLayoutParams
                                   param.setMargins(0,0,0,10)
                                   listView.layoutParams = param
                                   llContenedor.addView(listView2)
                                   val param2 = listView2.layoutParams as ViewGroup.MarginLayoutParams
                                   param2.setMargins(width/2-width/8,5,0,0)
                                   listView2.layoutParams = param2
                               }

                               "video"-> {

                                   val uri = Uri.parse(it.url)
                                   val video = VideoView(context)
                                   val mediaController = MediaController(context)
                                   val relativeLayout = RelativeLayout(context)
                                   val lp = LinearLayout.LayoutParams(width-50, 300)
                                   video.setVideoURI(uri)
                                   video.setMediaController(mediaController)
                                   video.layoutParams = FrameLayout.LayoutParams(lp)
                                   mediaController.setAnchorView(video)
                                   relativeLayout.addView(video)
                                   llContenedor.addView(relativeLayout)


                               }
                           }
                       }

                        val repository = Repository()
                        val viewModelFactory = ComponentsViewModelFactory(repository)
                        try {
                            viewModel2 = ViewModelProvider(this, viewModelFactory)[ComponentesViewModel::class.java]
                            viewModel2.getComponentes()
                            val lp =
                                LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT)

                            try {
                                viewModel2.datos.observe(viewLifecycleOwner) { response ->

                                    if (response.isSuccessful) {

//                    response.body()?.let { buttonRowAdapter.setData(it) }


                                        var padre = ""
                                        var respo = response.body()
                                        viewModel.id.observe(viewLifecycleOwner) { i ->
                                            var index = i + 1

                                            Log.d("FatasasSSL",
                                                "Wmesajjjejejejeje funcionaaaa")
                                            Log.d("FatasasSSL", index.toString())
                                            Log.d("FatasasSSL", i.toString())
                                            run lit@ {
                                                response.body()?.forEach {
                                                    Log.d("controllllll", it.id.toString())

                                                    if (it.id == index) {
                                                        Log.d("WORKKKK", it.id.toString())
                                                        padre = it.padre.toString()

                                                        return@lit

                                                    } else {

                                                        if (it.id > index) {
                                                            index++
                                                            Log.d("Sumaaaaaaaa", it.id.toString())
                                                            Log.d("Sumaaaaaaaa", index.toString())

                                                        } else {
                                                            Log.d("MAyorrrr", it.id.toString())
                                                            Log.d("MAyorrrr", index.toString())

                                                        }
                                                    }

                                                }
                                            }
                                            viewModel.padre.observe(viewLifecycleOwner) { a ->
                                                Log.d("WORKKKK",a.toString())
                                                Log.d("WORKKKK",padre)

                                                if (padre == a.toString() ) {

                                                    respo?.forEach{ its ->
                                                        if (its.id == index){

                                                            val buttonNext = Button(context)
                                                            val listView = LinearLayout(context)
                                                            listView.orientation =
                                                                LinearLayout.VERTICAL
                                                            listView.gravity =
                                                                Gravity.CENTER_HORIZONTAL
                                                            val lp =
                                                                LinearLayout.LayoutParams(width / 5,
                                                                    height / 15)
                                                            buttonNext.textSize = 18F
                                                            buttonNext.text = index.toString()
                                                            buttonNext.marginStart
                                                            buttonNext.gravity = Gravity.CENTER
                                                            buttonNext.setOnClickListener {
                                                                viewModel.componentes(its.componentes)
                                                                viewModel.id(its.id)
                                                                viewModel.padre(its.padre)
                                                                findNavController().navigate(R.id.action_inicioFragment_to_contenido)

                                                            }
                                                            listView.addView(buttonNext, lp)
                                                            llContenedor.addView(listView)
                                                            val param =
                                                                listView.layoutParams as ViewGroup.MarginLayoutParams
                                                            param.setMargins(width - (width / 3),
                                                                5,
                                                                0,
                                                                20)
                                                            listView.layoutParams = param



                                                            Log.d("WORKKKK", this.toString())
                                                        }
                                                    }
                                                }else{
                                                    val buttonNext = Button(context)
                                                    val listView = LinearLayout(context)
                                                    listView.orientation = LinearLayout.VERTICAL
                                                    listView.gravity = Gravity.CENTER_HORIZONTAL
                                                    val lp = LinearLayout.LayoutParams(width / 5,
                                                        height / 15)
                                                    buttonNext.textSize = 18F
                                                    buttonNext.text = "HOME"
                                                    buttonNext.marginStart
                                                    buttonNext.gravity = Gravity.CENTER
                                                    buttonNext.setOnClickListener {

                                                        val intento = Intent(context, Home::class.java)
                                                       context?.startActivity(intento)
                                                    }
                                                    listView.addView(buttonNext, lp)
                                                    llContenedor.addView(listView)
                                                    val param =
                                                        listView.layoutParams as ViewGroup.MarginLayoutParams
                                                    param.setMargins(width - (width / 3), 5, 0, 20)
                                                    listView.layoutParams = param

                                                }
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


//                        contentRowAdapter.setData(it)
                    } else {
                        Log.d("Error#", "ERROR ####")
                    }
                }
            }catch (e: Error){
                Log.d("ERROR#$e", "ERROR ##$e##")
            }


        /*  val posicion = viewModel.posicion.value
          val a = viewModel.componentes.value


          Log.d("working?", "that is a  ${a}")
          Log.d("working?", "that is posicion ${posicion}")*/


//        ContentRowAdapter.setData()
//        val repository = Repository()
//        val viewModelFactory = ComponentsViewModelFactory(repository)
//        viewModel = ViewModelProvider(this,viewModelFactory)[ComponentesViewModel::class.java]
//        viewModel.getComponentes()
//        viewModel.datos.observe(this,  { response ->
//
//            if (response.isSuccessful){
//                if (index< response.body()!!.size){
//
////                response.body()?.let { ContentRowAdapter.setData(it)}
////                var index = 0
////                while (index < response.body()!!.size)
////                ContentRowAdapter.setData(dataSet[position].componentes)
//                }
//
//            }else{
//                Toast.makeText(context, response.code(), Toast.LENGTH_SHORT).show()
//            }
//
//        })
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