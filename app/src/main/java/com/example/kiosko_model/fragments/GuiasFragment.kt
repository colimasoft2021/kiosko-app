package com.example.kiosko_model.fragments

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Binder
import android.os.Bundle
import android.text.Layout
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.kiosko_model.GuiasAdapter
import com.example.kiosko_model.ItemDataGuias
import com.example.kiosko_model.R
import com.example.kiosko_model.databinding.FragmentGuiasBinding
import com.example.kiosko_model.models.*
import com.example.kiosko_model.repository.Repository
import com.google.android.exoplayer2.util.Log
import retrofit2.Response
import java.util.concurrent.TimeoutException
import kotlin.math.absoluteValue


class GuiasFragment : Fragment(), AdapterView.OnItemClickListener {
    private lateinit var viewModel: GuiasViewModel
    private val viewModel2:GuiasContenidoViewModel by viewModels({requireParentFragment()})

    private var gridViewListControlInterno:GridView? = null
    private var gridViewListEjecucion:GridView? = null
    private var gridViewListAbastecimiento:GridView? = null
    private var gridViewListSeguridad:GridView? = null
    private var gridViewListServicio:GridView? = null
    private var gridView:GridView? = null

    private var ListControlInterno: List<ItemDataGuias> ? = null
    private var ListEjecucion: List<ItemDataGuias> ? = null
    private var ListAbastecimiento: List<ItemDataGuias> ? = null
    private var ListSeguridad: List<ItemDataGuias> ? = null
    private var ListServicio: List<ItemDataGuias> ? = null

    private var List: List<ItemDataGuias> ? = null


    private var ListControlInternoAdapter:  GuiasAdapter ? = null
    private var ListEjecucionAdapter:  GuiasAdapter ? = null
    private var ListAbastecimientoAdapter:  GuiasAdapter ? = null
    private var ListSeguridadAdapter:  GuiasAdapter ? = null
    private var ListServicioAdapter:  GuiasAdapter ? = null

    private var responseControlInterno: MutableList<Guias>? = mutableListOf()
    private var responseEjecucion: MutableList<Guias>? = mutableListOf()
    private var responseAbastecimiento: MutableList<Guias>? = mutableListOf()
    private var responseSeguridad: MutableList<Guias>? = mutableListOf()
    private var responseServicio: MutableList<Guias>? = mutableListOf()

    private var FondoresponseControlInterno: MutableList<String>? = mutableListOf()
    private var FondoresponseEjecucion: MutableList<String>? = mutableListOf()
    private var FondoresponseAbastecimiento: MutableList<String>? = mutableListOf()
    private var FondoresponseSeguridad: MutableList<String>? = mutableListOf()
    private var FondoresponseServicio: MutableList<String>? = mutableListOf()

    private var ColorFondoresponseControlInterno: MutableList<String>? = mutableListOf()
    private var ColorFondoresponseEjecucion: MutableList<String>? = mutableListOf()
    private var ColorFondoresponseAbastecimiento: MutableList<String>? = mutableListOf()
    private var ColorFondoresponseSeguridad: MutableList<String>? = mutableListOf()
    private var ColorFondoresponseServicio: MutableList<String>? = mutableListOf()




    private var guiasAdapter: GuiasAdapter ? = null


    private var _binding: FragmentGuiasBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentGuiasBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val color = Color.parseColor("#FC4C02")
        val radius = 15//radius will be 5px
        val graDient = GradientDrawable()
        graDient.setColor(color)
        graDient.cornerRadius = radius.toFloat()

        val vistaTitle = binding.GuiastitleContainer

        val titulo = Button(context)
        titulo.text = getString(R.string.gu_as_r_pidas)
        titulo.textSize = 25f
        titulo.background = graDient
        titulo.gravity = Gravity.CENTER_HORIZONTAL
        titulo.setTextColor(Color.WHITE)
        val layoutTitulo = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        layoutTitulo.setMargins(20,20,20,20)

        vistaTitle.addView(titulo,layoutTitulo)

        val texto = TextView(context)
        texto.text = getString(R.string.dentro_de_t)
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

        val butonSeguridad = binding.seguridad
        val llSeguridad = binding.llSeguridad

        val butonServicio = binding.servicio
        val llServicio = binding.llServicio



        llControlInterno.visibility = View.GONE
        llEjecucion.visibility = View.GONE
        llAbastecimiento.visibility = View.GONE
        llSeguridad.visibility = View.GONE
        llServicio.visibility = View.GONE

        val col = Color.parseColor("#000000")
        val rad = 20//radius will be 5px
        val strk = 5
        val gD = GradientDrawable()
        gD.setColor(col)
        gD.cornerRadius = rad.toFloat()
        gD.setStroke(strk, col)

        val buton = Button(context)
        buton.background = gD
        buton.setTextColor(Color.WHITE)
        buton.text =" Acceso directo "
        buton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_back_button,0,0,0)
        buton.setOnClickListener {

            findNavController().navigate(R.id.action_guiasFragment_to_accesoDirectoFragment)

        }
        binding.bButon.addView(buton)

        val repository = Repository()
        val viewModelFactory = GuiasViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory)[GuiasViewModel::class.java]

        try {
            viewModel.getGuias()
            viewModel.guiasResponse.observe(viewLifecycleOwner) { response ->



                response.body()!!.forEach {
                    when(it.tipoGuia){
                        "control-interno" -> {

//                            Log.d("responseTest", it.toString())
                            responseControlInterno?.add(it)
                            FondoresponseControlInterno?.add(it.urlFondo)
                            ColorFondoresponseControlInterno?.add(it.backgroundColor.toString())



                        }
                        "ejecucion" -> {

                            responseEjecucion?.add(it)
                            FondoresponseEjecucion?.add(it.urlFondo)
                            ColorFondoresponseEjecucion?.add(it.backgroundColor.toString())

                        }
                        "abastecimiento-e-inventario" -> {

                            responseAbastecimiento?.add(it)
                            FondoresponseAbastecimiento?.add(it.urlFondo)
                            ColorFondoresponseAbastecimiento?.add(it.backgroundColor.toString())

                        }
                        "seguridad" -> {

                            responseSeguridad?.add(it)
                            FondoresponseSeguridad?.add(it.urlFondo)
                            ColorFondoresponseSeguridad?.add(it.backgroundColor.toString())

                        }
                        "servicio" -> {

                            responseServicio?.add(it)
                            FondoresponseServicio?.add(it.urlFondo)
                            ColorFondoresponseServicio?.add(it.backgroundColor.toString())

                        }
                    }
                }

//                Log.d("responseTest", responseControlInterno?.toList().toString())

//                responseControlInterno = ControlInterno.toList()

                gridViewListControlInterno = binding.GuiasRapidasListaControlInterno
                ListControlInterno = ArrayList()
                ListControlInterno =setDataList(responseControlInterno?.toList())
                ListControlInternoAdapter = GuiasAdapter(requireContext(), ListControlInterno as List<ItemDataGuias>)
                gridViewListControlInterno?.adapter = ListControlInternoAdapter
                Log.d("responseTest", ListControlInterno.toString())

                gridViewListControlInterno?.onItemClickListener = this


                gridViewListEjecucion = binding.GuiasRapidasListaEjecuciN
                ListEjecucion = ArrayList()
                ListEjecucion =setDataList(responseEjecucion?.toList())
                ListEjecucionAdapter = GuiasAdapter(requireContext(), ListEjecucion as List<ItemDataGuias>)
                gridViewListEjecucion?.adapter = ListEjecucionAdapter
                gridViewListEjecucion?.onItemClickListener = this



                gridViewListAbastecimiento = binding.GuiasRapidasListaAbastecimientoeInventario
                ListAbastecimiento = ArrayList()
                ListAbastecimiento =setDataList(responseAbastecimiento?.toList())
                ListAbastecimientoAdapter = GuiasAdapter(requireContext(), ListAbastecimiento as List<ItemDataGuias>)
                gridViewListAbastecimiento?.adapter = ListAbastecimientoAdapter
                gridViewListAbastecimiento?.onItemClickListener = this



                gridViewListSeguridad = binding.GuiasRapidasListaSeguridad
                ListSeguridad = ArrayList()
                ListSeguridad =setDataList(responseSeguridad?.toList())
                ListSeguridadAdapter = GuiasAdapter(requireContext(), ListSeguridad as List<ItemDataGuias>)
                gridViewListSeguridad?.adapter = ListSeguridadAdapter
                gridViewListSeguridad?.onItemClickListener = this


                gridViewListServicio = binding.GuiasRapidasListaServicio
                ListServicio = ArrayList()
                ListServicio =setDataList(responseServicio?.toList())
                ListServicioAdapter = GuiasAdapter(requireContext(), ListServicio as List<ItemDataGuias>)
                gridViewListServicio?.adapter = ListServicioAdapter
                gridViewListServicio?.onItemClickListener = this

            }
        } catch (e: TimeoutException) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()

        }


        butonControlInterno.setOnClickListener {
            llControlInterno.visibility = View.VISIBLE
            llEjecucion.visibility = View.GONE
            llAbastecimiento.visibility = View.GONE
            llSeguridad.visibility = View.GONE
            llServicio.visibility = View.GONE
        }

        butonEjecucion.setOnClickListener {
            llControlInterno.visibility = View.GONE
            llEjecucion.visibility = View.VISIBLE
            llAbastecimiento.visibility = View.GONE
            llSeguridad.visibility = View.GONE
            llServicio.visibility = View.GONE         }

        butonAbastecimiento.setOnClickListener {
            llControlInterno.visibility = View.GONE
            llEjecucion.visibility = View.GONE
            llAbastecimiento.visibility = View.VISIBLE
            llSeguridad.visibility = View.GONE
            llServicio.visibility = View.GONE         }

        butonSeguridad.setOnClickListener {
            llControlInterno.visibility = View.GONE
            llEjecucion.visibility = View.GONE
            llAbastecimiento.visibility = View.GONE
            llSeguridad.visibility = View.VISIBLE
            llServicio.visibility = View.GONE         }

        butonServicio.setOnClickListener {
            llControlInterno.visibility = View.GONE
            llEjecucion.visibility = View.GONE
            llAbastecimiento.visibility = View.GONE
            llSeguridad.visibility = View.GONE
            llServicio.visibility = View.VISIBLE         }



        
    }

private fun setDataList(list: List<Guias>?) : ArrayList<ItemDataGuias>{

    val array:ArrayList<ItemDataGuias> = ArrayList()


    list?.forEach{
        var color = String()
            if(it.backgroundColor.isNullOrEmpty()){
                color =  "#008BCE"
            } else {
                color = it.backgroundColor
            }

        array.add(ItemDataGuias(color,it.titulo,it.componentes))
    }
    return array
}

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        Log.d("p0", p0?.id.toString())
        Log.d("p1", p1?.id.toString())
        Log.d("p2", p2.toString())
        Log.d("p3", p3.toString())

        val controlInterno = 2131361802
        val ejecucion = 2131361803
        val abastecimiento = 2131361801
        val seguridad = 2131361804
        val servicio = 2131361805


        when(p0?.id){

            controlInterno ->{
                val item:ItemDataGuias = ListControlInterno?.get(p2)!!
                viewModel2.urlFondo(FondoresponseControlInterno?.get(p2))
                viewModel2.colorModuloGuias(ColorFondoresponseControlInterno?.get(p2))
                viewModel2.componentes(item.componentes!!)
                findNavController().navigate(R.id.action_guiasFragment_to_guiasContenido)

            }
            ejecucion->{

                val item:ItemDataGuias = ListEjecucion?.get(p2)!!
                viewModel2.urlFondo(FondoresponseEjecucion?.get(p2))
                viewModel2.colorModuloGuias(ColorFondoresponseControlInterno?.get(p2))
                viewModel2.componentes(item.componentes!!)
                findNavController().navigate(R.id.action_guiasFragment_to_guiasContenido)

            }
            abastecimiento->{

                val item:ItemDataGuias = ListAbastecimiento?.get(p2)!!
                viewModel2.urlFondo(FondoresponseAbastecimiento?.get(p2))
                viewModel2.colorModuloGuias(ColorFondoresponseControlInterno?.get(p2))
                viewModel2.componentes(item.componentes!!)
                findNavController().navigate(R.id.action_guiasFragment_to_guiasContenido)

            }
            seguridad->{

                val item:ItemDataGuias = ListSeguridad?.get(p2)!!
                viewModel2.urlFondo(FondoresponseSeguridad?.get(p2))
                viewModel2.colorModuloGuias(ColorFondoresponseControlInterno?.get(p2))
                viewModel2.componentes(item.componentes!!)
                findNavController().navigate(R.id.action_guiasFragment_to_guiasContenido)

            }
            servicio->{

                val item:ItemDataGuias = ListServicio?.get(p2)!!
                viewModel2.urlFondo(FondoresponseServicio?.get(p2))
                viewModel2.colorModuloGuias(ColorFondoresponseControlInterno?.get(p2))
                viewModel2.componentes(item.componentes!!)
                findNavController().navigate(R.id.action_guiasFragment_to_guiasContenido)

            }

        }



    }


}