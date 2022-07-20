package com.example.kiosko_model.fragments

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.Html
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.kiosko_model.*
import com.example.kiosko_model.databinding.FragmentVideosBinding
import com.example.kiosko_model.models.*
import com.example.kiosko_model.repository.Repository
import com.google.android.exoplayer2.util.Log
import retrofit2.Response
import java.util.concurrent.TimeoutException

private lateinit var viewModel: VideosViewModel

class VideosFragment : Fragment(), AdapterView.OnItemClickListener {


    private var gridViewListControlInterno:GridView? = null
    private var gridViewListEjecucion:GridView? = null
    private var gridViewListAbastecimiento:GridView? = null
    private var gridViewListServicio:GridView? = null


    private var responseControlInterno: MutableList<Videos>? = mutableListOf()
    private var responseEjecucion: MutableList<Videos>? = mutableListOf()
    private var responseAbastecimiento: MutableList<Videos>? = mutableListOf()
    private var responseServicio: MutableList<Videos>? = mutableListOf()

    private var ListControlInterno: List<VideoItem> ? = null
    private var ListEjecucion: List<VideoItem> ? = null
    private var ListAbastecimiento: List<VideoItem> ? = null
    private var ListServicio: List<VideoItem> ? = null


    private var ListControlInternoAdapter:  VideosAdapter ? = null
    private var ListEjecucionAdapter:  VideosAdapter ? = null
    private var ListAbastecimientoAdapter:  VideosAdapter ? = null
    private var ListServicioAdapter:  VideosAdapter ? = null

//    private var gridView:GridView? = null
//    private var List: List<VideoItem> ? = null
//    private var videosAdapter: VideosAdapter? = null


    private var _binding: FragmentVideosBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentVideosBinding.inflate(inflater, container, false)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val color = Color.parseColor("#D9027D")
        val radius = 15//radius will be 5px
        val graDient = GradientDrawable()
        graDient.setColor(color)
        graDient.cornerRadius = radius.toFloat()

        val vistaTitle = binding.videoTitleContainer

        val titulo = Button(context)
        titulo.text = getString(R.string.videos)
        titulo.textSize = 25f
        titulo.background = graDient
        titulo.gravity = Gravity.CENTER_HORIZONTAL
        titulo.setTextColor(Color.WHITE)
        val layoutTitulo = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        layoutTitulo.setMargins(20,20,20,20)

        vistaTitle.addView(titulo,layoutTitulo)

        val texto = TextView(context)
        texto.text = getString(R.string.procedimientos_a_ejecutar)
        texto.textSize = 18f
        texto.textAlignment = TextView.TEXT_ALIGNMENT_TEXT_START
        texto.setTextColor(Color.BLACK)
        val layoutTexto = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        layoutTexto.setMargins(20,5,20,20)

        vistaTitle.addView(texto,layoutTexto)


        val butonControlInterno = binding.controlInterno
        val llControlInterno = binding.llControlInterno

        val butonEjecucion = binding.ejecucion
        val llEjecucion = binding.llEjecucion

        val butonAbastecimiento = binding.abastecimiento
        val llAbastecimiento = binding.llAbastecimiento

        val butonServicio = binding.servicio
        val llServicio = binding.llServicio


        llControlInterno.visibility = View.GONE
        llEjecucion.visibility = View.GONE
        llAbastecimiento.visibility = View.GONE
        llServicio.visibility = View.GONE

        val col = Color.parseColor("#000000")
        Log.d("VideosFragment",col.toString())
        val rad = 20//radius will be 5px
        val strk = 5
        val gD = GradientDrawable()
        gD.setColor(col)
        gD.cornerRadius = rad.toFloat()
        gD.setStroke(strk, col)

        val buton = Button(context)

        buton.background = gD
        Log.d("VideosFragment",buton.background.toString())
        buton.setTextColor(Color.WHITE)
        buton.text =" Acceso directo "
        buton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_back_button,0,0,0)
        buton.setOnClickListener {

            findNavController().navigate(R.id.action_videosFragment_to_accesoDirectoFragment)

        }
        binding.bButonVideo.addView(buton)


        val repository = Repository()
        val viewModelFactory = VideosViewModelFactory(repository)


        viewModel = ViewModelProvider(this, viewModelFactory)[VideosViewModel::class.java]


        try {
            viewModel.getVideos()
            viewModel.videosResponse.observe(viewLifecycleOwner) { response ->



                response.body()!!.forEach {
                    when(it.tipoCategoria){
                        "control-interno" -> {

//                            Log.d("responseTest", it.toString())
                            responseControlInterno?.add(it)



                        }
                        "ejecucion" -> {

                            responseEjecucion?.add(it)

                        }
                        "abastecimiento-e-inventario" -> {

                            responseAbastecimiento?.add(it)

                        }

                        "servicio" -> {

                            responseServicio?.add(it)

                        }
                    }
                }



                gridViewListControlInterno = binding.VideoListaControlInterno
                ListControlInterno = ArrayList()
                ListControlInterno =setDataList(responseControlInterno?.toList())
                ListControlInternoAdapter = VideosAdapter(requireContext(), ListControlInterno as List<VideoItem>)
                gridViewListControlInterno?.adapter = ListControlInternoAdapter
                Log.d("responseTest", ListControlInterno.toString())

                gridViewListControlInterno?.onItemClickListener = this


                gridViewListEjecucion = binding.VideoListaEjecuciN
                ListEjecucion = ArrayList()
                ListEjecucion =setDataList(responseEjecucion?.toList())
                ListEjecucionAdapter = VideosAdapter(requireContext(), ListEjecucion as List<VideoItem>)
                gridViewListEjecucion?.adapter = ListEjecucionAdapter
                gridViewListEjecucion?.onItemClickListener = this



                gridViewListAbastecimiento = binding.VideoListaAbastecimientoeInventario
                ListAbastecimiento = ArrayList()
                ListAbastecimiento =setDataList(responseAbastecimiento?.toList())
                ListAbastecimientoAdapter = VideosAdapter(requireContext(), ListAbastecimiento as List<VideoItem>)
                gridViewListAbastecimiento?.adapter = ListAbastecimientoAdapter
                gridViewListAbastecimiento?.onItemClickListener = this


                gridViewListServicio = binding.VideoListaServicio
                ListServicio = ArrayList()
                ListServicio =setDataList(responseServicio?.toList())
                ListServicioAdapter = VideosAdapter(requireContext(), ListServicio as List<VideoItem>)
                gridViewListServicio?.adapter = ListServicioAdapter
                gridViewListServicio?.onItemClickListener = this


            }
        } catch (e: TimeoutException) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()

        }finally {

        }




        butonControlInterno.setOnClickListener {
            llControlInterno.visibility = View.VISIBLE
            llEjecucion.visibility = View.GONE
            llAbastecimiento.visibility = View.GONE
            llServicio.visibility = View.GONE
        }

        butonEjecucion.setOnClickListener {
            llControlInterno.visibility = View.GONE
            llEjecucion.visibility = View.VISIBLE
            llAbastecimiento.visibility = View.GONE
            llServicio.visibility = View.GONE         }

        butonAbastecimiento.setOnClickListener {
            llControlInterno.visibility = View.GONE
            llEjecucion.visibility = View.GONE
            llAbastecimiento.visibility = View.VISIBLE
            llServicio.visibility = View.GONE         }


        butonServicio.setOnClickListener {
            llControlInterno.visibility = View.GONE
            llEjecucion.visibility = View.GONE
            llAbastecimiento.visibility = View.GONE
            llServicio.visibility = View.VISIBLE         }


    }

    @Suppress("DEPRECATION")
    private fun setDataList(list: List<Videos>?) : ArrayList<VideoItem>{

        var array:ArrayList<VideoItem> = ArrayList()
        var index = 0

        var arrayColors:ArrayList<String> = ArrayList()
        arrayColors.add("#FC4C02")//naranja, servicio
        arrayColors.add("#008BCE")//azul, control interno
        arrayColors.add("#DA291C")//rojo
        arrayColors.add("#43B02A")//verde, abastecimiento
        arrayColors.add("#D9027D")//lila, ejecucion
        arrayColors.add("#003DA5")
        arrayColors.add("#FFFFFF")



        list?.forEach{
            Log.d("VideosFragment",it.tipoCategoria.toString())
            val color : String


                Log.d("VideosFragmentewe",arrayColors.size.toString())
                Log.d("VideosFragment",index.toString())
                    if(it.tipoCategoria.equals("control-interno")){
                        color = arrayColors[1]
                        array.add(VideoItem(color, Html.fromHtml(it.descripcion).toString(),it.url))
                    }else if(it.tipoCategoria.equals("ejecucion")){
                        color = arrayColors[4]
                        array.add(VideoItem(color,Html.fromHtml(it.descripcion).toString(),it.url))

                    }else if(it.tipoCategoria.equals("abastecimiento-e-inventario")){
                        color = arrayColors[3]
                        array.add(VideoItem(color,Html.fromHtml(it.descripcion).toString(),it.url))

                    }else if(it.tipoCategoria.equals("servicio")){
                        color = arrayColors[0]
                        array.add(VideoItem(color,Html.fromHtml(it.descripcion).toString(),it.url))

                    }else{
                        color = arrayColors[2]
                        array.add(VideoItem(color,Html.fromHtml(it.descripcion).toString(),it.url))
                    }

            Log.d("VideosFragment",it.descripcion.toString())
        }
        return array
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

        Log.d("p0000", p0?.id.toString())
        Log.d("p1", p1?.id.toString())
        Log.d("p2", p2.toString())
        Log.d("p3", p3.toString())

        val controlInterno = 2131361818
        val ejecucion = 2131361819
        val abastecimiento = 2131361817
        val servicio = 2131361820

        when(p0?.id){

            controlInterno ->{
                val item:VideoItem = ListControlInterno?.get(p2)!!
                (activity as Home?) ?.PopUpComponenteVideo(item.name,item.url,mensajeInicial = false)
                Log.d("work??","work")

            }
            ejecucion->{

                val item:VideoItem = ListEjecucion?.get(p2)!!
                (activity as Home?) ?.PopUpComponenteVideo(item.name,item.url,mensajeInicial = false)


            }
            abastecimiento->{

                val item:VideoItem = ListAbastecimiento?.get(p2)!!
                (activity as Home?) ?.PopUpComponenteVideo(item.name,item.url,mensajeInicial = false)

            }

            servicio->{

                val item:VideoItem = ListServicio?.get(p2)!!
                (activity as Home?) ?.PopUpComponenteVideo(item.name,item.url,mensajeInicial = false)


            }

        }
    }


}