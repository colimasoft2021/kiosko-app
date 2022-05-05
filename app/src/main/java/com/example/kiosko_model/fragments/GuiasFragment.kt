package com.example.kiosko_model.fragments

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.GridView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.kiosko_model.GuiasAdapter
import com.example.kiosko_model.ItemDataGuias
import com.example.kiosko_model.R
import com.example.kiosko_model.databinding.FragmentGuiasBinding
import com.example.kiosko_model.models.*
import com.example.kiosko_model.repository.Repository
import retrofit2.Response
import java.util.concurrent.TimeoutException




class GuiasFragment : Fragment(), AdapterView.OnItemClickListener {
    private lateinit var viewModel: GuiasViewModel
    private val viewModel2:GuiasContenidoViewModel by viewModels({requireParentFragment()})

    private var gridView:GridView? = null
    private var List: List<ItemDataGuias> ? = null
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

        binding.llTitulo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.liston_4,0, R.drawable.liston_4,0)

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

                gridView = binding.GuiasRapidasLista
                List = ArrayList()
                List = setDataList(response)
                guiasAdapter = GuiasAdapter(requireContext(), List as List<ItemDataGuias>)
                gridView?.adapter = guiasAdapter
                gridView?.onItemClickListener = this



            }
        } catch (e: TimeoutException) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()

        }
    }

private fun setDataList(list: Response<List<Guias>>) : ArrayList<ItemDataGuias>{

    var array:ArrayList<ItemDataGuias> = ArrayList()
    var index = 0

    var arrayColors:ArrayList<String> = ArrayList()
    arrayColors.add("#FC4C02")
    arrayColors.add("#008BCE")
    arrayColors.add("#DA291C")
    arrayColors.add("#43B02A")
    arrayColors.add("#D9027D")
    arrayColors.add("#003DA5")
    arrayColors.add("#FFFFFF")



    list.body()!!.forEach{

        val color : String

        if(index <= arrayColors.size ){
            color = arrayColors.get(index)
            index++
        }else{
            index = 0
            color = arrayColors.get(index)
        }
        array.add(ItemDataGuias(color,it.titulo,it.componentes))
    }
    return array
}

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

    var item:ItemDataGuias = List?.get(p2)!!

        viewModel2.componentes(item.componentes!!)
        findNavController().navigate(R.id.action_guiasFragment_to_guiasContenido)
//        Toast.makeText(requireContext(), "workkk+${item.name}+${item.icons}",Toast.LENGTH_LONG).show()

    }


}